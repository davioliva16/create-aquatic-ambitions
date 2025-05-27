package net.davio.aquaticambitions.compat.JEI;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.jei.*;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.davio.aquaticambitions.util.CAALang;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.compat.JEI.category.FanChannelingCategory;
import net.davio.aquaticambitions.registry.recipe.CAARecipeTypes;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


@JeiPlugin
@SuppressWarnings({"unused", "inline", "all"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CAAJEI implements IModPlugin {
    private static final ResourceLocation MOD_ID = CreateAquaticAmbitions.asResource("jei_plugin");
    @Override
    public ResourceLocation getPluginUid() {
        return MOD_ID;
    }

    public IIngredientManager ingredientManager;
    private static final List<CreateRecipeCategory<?>> Categories = new ArrayList<>();

    public static IJeiRuntime runtime;

    private void loadCategories() {
        Categories.clear();
        CreateRecipeCategory<?> channeling = builder(ChannelingRecipe.class)
                .addTypedRecipes(CAARecipeTypes.CHANNELING::getType)
                .catalystStack(ProcessingViaFanCategory.getFan("aquatic_ambitions.fan_channeling"))
                .doubleItemIcon(AllItems.PROPELLER.get(), Items.HEART_OF_THE_SEA)
                .emptyBackground(178, 72)
                .build("aquatic_ambitions.fan_channeling", FanChannelingCategory::new);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        Categories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(Categories.toArray(IRecipeCategory[]::new));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        Categories.forEach(c -> c.registerCatalysts(registration));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new BlueprintTransferHandler(), RecipeTypes.CRAFTING);
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    private static class CategoryBuilder<T extends Recipe<?>> {
        private final Class<? extends T> recipeClass;
        private Predicate<CRecipes> predicate = cRecipes -> true;

        private IDrawable background;
        private IDrawable icon;

        private final List<Consumer<List<RecipeHolder<T>>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }

        public CategoryBuilder<T> enableIf(Predicate<CRecipes> predicate) {
            this.predicate = predicate;
            return this;
        }

        public CategoryBuilder<T> enableWhen(Function<CRecipes, Object> configValue) {
            predicate = c -> (boolean) configValue.apply(c);
            return this;
        }

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<RecipeHolder<T>>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addRecipes(Supplier<Collection<? extends RecipeHolder<T>>> collection) {
            return addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
        }

        @SuppressWarnings("unchecked")
        public CategoryBuilder<T> addAllRecipesIf(Predicate<RecipeHolder<T>> pred) {
            return addRecipeListConsumer(recipes -> consumeAllRecipesOfType(recipe -> {
                if (pred.test(recipe))
                    recipes.add(recipe);
            }));
        }

        public CategoryBuilder<T> addAllRecipesIf(Predicate<RecipeHolder<?>> pred, Function<RecipeHolder<?>, RecipeHolder<T>> converter) {
            return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add(converter.apply(recipe));
                }
            }));
        }

        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            return addTypedRecipes(recipeTypeEntry::getType);
        }

        public <I extends RecipeInput, R extends Recipe<I>> CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<R>> recipeType) {
            return addRecipeListConsumer(recipes -> CAAJEI.<T>consumeTypedRecipes(recipe -> {
                if (recipeClass.isInstance(recipe.value()))
                    //noinspection unchecked - checked by if statement above
                    recipes.add((RecipeHolder<T>) recipe);
            }, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<RecipeHolder<?>, RecipeHolder<T>> converter) {
            return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<RecipeHolder<?>> pred) {
            return addRecipeListConsumer(recipes -> consumeTypedRecipesTyped(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add(recipe);
                }
            }, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType,
                                                           Supplier<RecipeType<? extends T>> excluded) {
            return addRecipeListConsumer(recipes -> {
                List<RecipeHolder<?>> excludedRecipes = getTypedRecipes(excluded.get());
                consumeTypedRecipesTyped(recipe -> {
                    for (RecipeHolder<?> excludedRecipe : excludedRecipes) {
                        if (doInputsMatch(recipe.value(), excludedRecipe.value())) {
                            return;
                        }
                    }
                    recipes.add(recipe);
                }, recipeType.get());
            });
        }

        public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> {
                List<RecipeHolder<?>> excludedRecipes = getTypedRecipes(recipeType.get());
                recipes.removeIf(recipe -> {
                    for (RecipeHolder<?> excludedRecipe : excludedRecipes)
                        if (doInputsMatch(recipe.value(), excludedRecipe.value()) && doOutputsMatch(recipe.value(), excludedRecipe.value()))
                            return true;
                    return false;
                });
            });
        }

        public CategoryBuilder<T> removeNonAutomation() {
            return addRecipeListConsumer(recipes -> recipes.removeIf(AllRecipeTypes.CAN_BE_AUTOMATED.negate()));
        }

        public static List<RecipeHolder<?>> getTypedRecipes(RecipeType<?> type) {
            List<RecipeHolder<?>> recipes = new ArrayList<>();
            consumeTypedRecipes(recipes::add, type);
            return recipes;
        }

        public static List<RecipeHolder<?>> getTypedRecipesExcluding(RecipeType<?> type, Predicate<RecipeHolder<?>> exclusionPred) {
            List<RecipeHolder<?>> recipes = getTypedRecipes(type);
            recipes.removeIf(exclusionPred);
            return recipes;
        }

        public static boolean doInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
            if (recipe1.getIngredients()
                    .isEmpty()
                    || recipe2.getIngredients()
                    .isEmpty()) {
                return false;
            }
            ItemStack[] matchingStacks = recipe1.getIngredients()
                    .get(0)
                    .getItems();
            if (matchingStacks.length == 0) {
                return false;
            }
            return recipe2.getIngredients()
                    .get(0)
                    .test(matchingStacks[0]);
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            catalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get()
                    .asItem()));
        }

        public CategoryBuilder<T> icon(IDrawable icon) {
            this.icon = icon;
            return this;
        }

        public CategoryBuilder<T> itemIcon(ItemLike item) {
            icon(new ItemIcon(() -> new ItemStack(item)));
            return this;
        }

        public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
            icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
            return this;
        }

        public CategoryBuilder<T> background(IDrawable background) {
            this.background = background;
            return this;
        }

        public CategoryBuilder<T> emptyBackground(int width, int height) {
            background(new EmptyBackground(width, height));
            return this;
        }

        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            Supplier<List<RecipeHolder<T>>> recipesSupplier;
            if (predicate.test(AllConfigs.server().recipes)) {
                recipesSupplier = () -> {
                    List<RecipeHolder<T>> recipes = new ArrayList<>();
                    for (Consumer<List<RecipeHolder<T>>> consumer : recipeListConsumers)
                        consumer.accept(recipes);
                    return recipes;
                };
            } else {
                recipesSupplier = Collections::emptyList;
            }

            CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
                    new mezz.jei.api.recipe.RecipeType<>(CreateAquaticAmbitions.asResource(name), recipeClass),
                    CAALang.translateDirect("recipe." + name), background, icon, recipesSupplier, catalysts);
            CreateRecipeCategory<T> category = factory.create(info);
            Categories.add(category);
            return category;
        }

        private void consumeAllRecipesOfType(Consumer<RecipeHolder<T>> consumer) {
            consumeAllRecipes(recipeHolder -> {
                if (recipeClass.isInstance(recipeHolder.value())) {
                    //noinspection unchecked - this is checked by the if statement
                    consumer.accept((RecipeHolder<T>) recipeHolder);
                }
            });
        }
        private void consumeTypedRecipesTyped(Consumer<RecipeHolder<T>> consumer, RecipeType<?> type) {
            consumeTypedRecipes(recipeHolder -> {
                if (recipeClass.isInstance(recipeHolder.value())) {
                    //noinspection unchecked - this is checked by the if statement
                    consumer.accept((RecipeHolder<T>) recipeHolder);
                }
            }, type);
        }

    }

    public static void consumeAllRecipes(Consumer<? super RecipeHolder<?>> consumer) {
        Minecraft.getInstance()
                .getConnection()
                .getRecipeManager()
                .getRecipes()
                .forEach(consumer);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends Recipe<?>> void consumeTypedRecipes(Consumer<RecipeHolder<?>> consumer, RecipeType<?> type) {
        List<? extends RecipeHolder<?>> map = Minecraft.getInstance()
                .getConnection()
                .getRecipeManager().getAllRecipesFor((RecipeType) type);
        if (!map.isEmpty())
            map.forEach(consumer);
    }

    public static List<RecipeHolder<?>> getTypedRecipes(RecipeType<?> type) {
        List<RecipeHolder<?>> recipes = new ArrayList<>();
        consumeTypedRecipes(recipes::add, type);
        return recipes;
    }

    public static List<RecipeHolder<?>> getTypedRecipesExcluding(RecipeType<?> type, Predicate<RecipeHolder<?>> exclusionPred) {
        List<RecipeHolder<?>> recipes = getTypedRecipes(type);
        recipes.removeIf(exclusionPred);
        return recipes;
    }

    public static boolean doInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        if (recipe1.getIngredients()
                .isEmpty()
                || recipe2.getIngredients()
                .isEmpty()) {
            return false;
        }
        ItemStack[] matchingStacks = recipe1.getIngredients()
                .getFirst()
                .getItems();
        if (matchingStacks.length == 0) {
            return false;
        }
        return recipe2.getIngredients()
                .getFirst()
                .test(matchingStacks[0]);
    }

    public static boolean doOutputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        return ItemHelper.sameItem(recipe1.getResultItem(registryAccess), recipe2.getResultItem(registryAccess));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        CAAJEI.runtime = runtime;
    }
}