package net.davio.aquaticambitions.infrastructure.data.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import net.davio.aquaticambitions.registry.CAABlocks;
import net.davio.aquaticambitions.registry.CAAItems;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Supplier;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;

import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAABlocks;
import net.davio.aquaticambitions.registry.CAAItems;

public class CAAStandardRecipeGen extends BaseRecipeProvider {
    final List<GeneratedRecipe> all = new ArrayList<>();

    private CAAStandardRecipeGen.Marker MATERIALS = enterFolder("materials");

    GeneratedRecipe

    PRISMARINE_ALLOY_FROM_BLOCK = create(CAAItems.PRISMARINE_ALLOY).withSuffix("_from_block")
        .returns(9)
        .unlockedBy(CAARecipeProvider.I::prismarineAlloy)
        .viaShapeless(b -> b.requires(CAABlocks.PRISMARINE_ALLOY_BLOCK.get())),

    PRISMARINE_ALLOY_BLOCK = create(CAABlocks.PRISMARINE_ALLOY_BLOCK).unlockedBy(CAARecipeProvider.I::prismarineAlloy)
        .viaShaped(b -> b.define('C', CAARecipeProvider.I.prismarineAlloy())
            .pattern("CCC")
            .pattern("CCC")
            .pattern("CCC")),

    PRISMARINE_ROD = create(CAAItems.PRISMARINE_ALLOY_ROD).unlockedBy(CAARecipeProvider.I::prismarineAlloy)
            .viaShaped(b -> b.define('I', CAARecipeProvider.I.prismarineAlloy())
                    .pattern("I")
                    .pattern("I")),

    PRISMARINE_ALLOY = create(CAAItems.PRISMARINE_ALLOY).unlockedBy(CAARecipeProvider.I::copperNugget)
        .viaShaped(b -> b.define('A', Items.PRISMARINE)
                .define('B', AllItems.COPPER_NUGGET)
                .pattern("BA")
                .pattern("AB")),

    BRAIN_CORAL = create(() -> Items.BRAIN_CORAL_BLOCK).unlockedBy(CAARecipeProvider.I::brainCoral)
        .viaShaped(b -> b.define('#', Items.BRAIN_CORAL)
                .pattern("##")
                .pattern("##")),

    FIRE_CORAL = create(() -> Items.FIRE_CORAL_BLOCK).unlockedBy(CAARecipeProvider.I::fireCoral)
        .viaShaped(b -> b.define('#', Items.FIRE_CORAL)
                .pattern("##")
                .pattern("##")),

    BUBBLE_CORAL = create(() -> Items.BUBBLE_CORAL_BLOCK).unlockedBy(CAARecipeProvider.I::bubbleCoral)
        .viaShaped(b -> b.define('#', Items.BUBBLE_CORAL)
                .pattern("##")
                .pattern("##")),

    HORN_CORAL = create(() -> Items.HORN_CORAL_BLOCK).unlockedBy(CAARecipeProvider.I::hornCoral)
        .viaShaped(b -> b.define('#', Items.HORN_CORAL)
                .pattern("##")
                .pattern("##")),

    TUBE_CORAL = create(() -> Items.TUBE_CORAL_BLOCK).unlockedBy(CAARecipeProvider.I::tubeCoral)
        .viaShaped(b -> b.define('#', Items.TUBE_CORAL)
                .pattern("##")
                .pattern("##")),

    MECHANICAL_CONDUIT = create(CAABlocks.MECHANICAL_CONDUIT).unlockedBy(CAARecipeProvider.I::conduit)
            .viaShaped(b -> b.define('A', Items.CONDUIT.asItem())
                    .define('I', CAAItems.PRISMARINE_ALLOY_ROD)
                    .define('P', CAARecipeProvider.I.prismarineAlloy())
                    .define('U', AllBlocks.FLUID_PIPE)
                    .pattern("III")
                    .pattern("IAI")
                    .pattern("PUP")),


    TRIDENT = create(() -> Items.TRIDENT).unlockedBy(CAARecipeProvider.I::prismarineAlloy)
            .viaShaped(b -> b.define('A', CAAItems.SPIKY_SHELL.get())
                    .define('I', CAAItems.PRISMARINE_ALLOY_ROD.get())
                    .pattern(" AA")
                    .pattern(" IA")
                    .pattern("I  "));

    private CAAStandardRecipeGen.Marker COOKING = enterFolder("/");

    GeneratedRecipe

    VERIDIUM = create(() -> AllPaletteStoneTypes.VERIDIUM.baseBlock.get()).viaCooking(() -> Items.PRISMARINE)
    .inFurnace();

    static class Marker {
    }
    String currentFolder = "";

    CAAStandardRecipeGen.Marker enterFolder(String folder) {
        currentFolder = folder;
        return new CAAStandardRecipeGen.Marker();
    }

    CAAStandardRecipeGen.GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new CAAStandardRecipeGen.GeneratedRecipeBuilder(currentFolder, result);
    }

    CAAStandardRecipeGen.GeneratedRecipeBuilder create(ResourceLocation result) {
        return new CAAStandardRecipeGen.GeneratedRecipeBuilder(currentFolder, result);
    }

    CAAStandardRecipeGen.GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return create(result::get);
    }

    GeneratedRecipe createSpecial(Supplier<? extends SimpleCraftingRecipeSerializer<?>> serializer, String recipeType,
                                  String path) {
        ResourceLocation location = Create.asResource(recipeType + "/" + currentFolder + "/" + path);
        return register(consumer -> {
            SpecialRecipeBuilder b = SpecialRecipeBuilder.special(serializer.get());
            b.save(consumer, location.toString());
        });
    }

    GeneratedRecipe blastCrushedMetal(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
        return create(result::get).withSuffix("_from_crushed")
                .viaCooking(ingredient::get)
                .rewardXP(.1f)
                .inBlastFurnace();
    }

    GeneratedRecipe blastModdedCrushedMetal(ItemEntry<? extends Item> ingredient, CommonMetal metal) {
        for (Mods mod : metal.mods) {
            String metalName = metal.getName(mod);
            ResourceLocation ingot = mod.ingotOf(metalName);
            String modId = mod.getId();
            create(ingot).withSuffix("_compat_" + modId)
                    .whenModLoaded(modId)
                    .viaCooking(ingredient::get)
                    .rewardXP(.1f)
                    .inBlastFurnace();
        }
        return null;
    }

    GeneratedRecipe recycleGlass(BlockEntry<? extends Block> ingredient) {
        return create(() -> Blocks.GLASS).withSuffix("_from_" + ingredient.getId()
                        .getPath())
                .viaCooking(ingredient::get)
                .forDuration(50)
                .inFurnace();
    }

    GeneratedRecipe recycleGlassPane(BlockEntry<? extends Block> ingredient) {
        return create(() -> Blocks.GLASS_PANE).withSuffix("_from_" + ingredient.getId()
                        .getPath())
                .viaCooking(ingredient::get)
                .forDuration(50)
                .inFurnace();
    }

    GeneratedRecipe metalCompacting(List<ItemProviderEntry<? extends ItemLike>> variants,
                                    List<Supplier<TagKey<Item>>> ingredients) {
        GeneratedRecipe result = null;
        for (int i = 0; i + 1 < variants.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = variants.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = variants.get(i + 1);
            Supplier<TagKey<Item>> currentIngredient = ingredients.get(i);
            Supplier<TagKey<Item>> nextIngredient = ingredients.get(i + 1);

            result = create(nextEntry).withSuffix("_from_compacting")
                    .unlockedBy(currentEntry::get)
                    .viaShaped(b -> b.pattern("###")
                            .pattern("###")
                            .pattern("###")
                            .define('#', currentIngredient.get()));

            result = create(currentEntry).returns(9)
                    .withSuffix("_from_decompacting")
                    .unlockedBy(nextEntry::get)
                    .viaShapeless(b -> b.requires(nextIngredient.get()));
        }
        return result;
    }

    GeneratedRecipe conversionCycle(List<ItemProviderEntry<? extends ItemLike>> cycle) {
        GeneratedRecipe result = null;
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            result = create(nextEntry).withSuffix("_from_conversion")
                    .unlockedBy(currentEntry::get)
                    .viaShapeless(b -> b.requires(currentEntry.get()));
        }
        return result;
    }

    GeneratedRecipe clearData(ItemProviderEntry<? extends ItemLike> item) {
        return create(item).withSuffix("_clear")
                .unlockedBy(item::get)
                .viaShapeless(b -> b.requires(item.get()));
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> p_200404_1_) {
        all.forEach(c -> c.register(p_200404_1_));
        Create.LOGGER.info(getName() + " registered " + all.size() + " recipe" + (all.size() == 1 ? "" : "s"));
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    class GeneratedRecipeBuilder {

        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ICondition> recipeConditions;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag.get())
                    .build();
            return this;
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        // FIXME 5.1 refactor - recipe categories as markers instead of sections?
        GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b =
                        builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createLocation("crafting"));
            });
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b =
                        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                b.save(result -> {
                    consumer.accept(!recipeConditions.isEmpty()
                            ? new CAAStandardRecipeGen.ConditionSupportingShapelessRecipeResult(result, recipeConditions)
                            : result);
                }, createLocation("crafting"));
            });
        }

        GeneratedRecipe viaNetheriteSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
            return register(consumer -> {
                SmithingTransformRecipeBuilder b =
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.of(base.get()), upgradeMaterial.get(), RecipeCategory.COMBAT, result.get()
                                        .asItem());
                b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(base.get())
                        .build()));
                b.save(consumer, createLocation("crafting"));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return Create.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createLocation(String recipeType) {
            return Create.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? CatnipServices.REGISTRIES.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
        }

        CAAStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new CAAStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder(ingredient);
        }

        class GeneratedCookingRecipeBuilder {

            private Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            private final RecipeSerializer<? extends AbstractCookingRecipe> FURNACE = RecipeSerializer.SMELTING_RECIPE,
                    SMOKER = RecipeSerializer.SMOKING_RECIPE, BLAST = RecipeSerializer.BLASTING_RECIPE,
                    CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            CAAStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            CAAStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(FURNACE, builder, 1);
            }

            GeneratedRecipe inSmoker() {
                return inSmoker(b -> b);
            }

            GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                create(CAMPFIRE, builder, 3);
                return create(SMOKER, builder, .5f);
            }

            GeneratedRecipe inBlastFurnace() {
                return inBlastFurnace(b -> b);
            }

            GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                return create(BLAST, builder, .5f);
            }

            private GeneratedRecipe create(RecipeSerializer<? extends AbstractCookingRecipe> serializer,
                                           UnaryOperator<SimpleCookingRecipeBuilder> builder, float cookingTimeModifier) {
                return register(consumer -> {
                    boolean isOtherMod = compatDatagenOutput != null;

                    SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
                            RecipeCategory.MISC, isOtherMod ? Items.DIRT : result.get(), exp,
                            (int) (cookingTime * cookingTimeModifier), serializer));

                    if (unlockedBy != null)
                        b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                    b.save(result -> {
                        consumer.accept(
                                isOtherMod ? new CAAStandardRecipeGen.ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
                                        : result);
                    }, createSimpleLocation(CatnipServices.REGISTRIES.getKeyOrThrow(serializer)
                            .getPath()));
                });
            }
        }
    }

    public CAAStandardRecipeGen(PackOutput p_i48262_1_) {
        super(p_i48262_1_, Create.ID);
    }

    private record ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride,
                                             List<ICondition> conditions) implements FinishedRecipe {
        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());

            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            object.add("conditions", conds);
        }
    }

    private record ConditionSupportingShapelessRecipeResult(FinishedRecipe wrapped, List<ICondition> conditions)
            implements FinishedRecipe {
        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            wrapped.serializeRecipeData(pJson);

            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            pJson.add("conditions", conds);
        }
    }
}