package net.davio.aquaticambitions.infrastructure.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class CAARecipeProvider extends RecipeProvider {

    static final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();

    public CAARecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
    }

    public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        GENERATORS.add(new CAACrushingRecipeGen(output, registries, CreateAquaticAmbitions.MODID));
        GENERATORS.add(new CAAMillingRecipeGen(output, registries, CreateAquaticAmbitions.MODID));
        GENERATORS.add(new CAAWashingDataGen(output, registries, CreateAquaticAmbitions.MODID));
        GENERATORS.add(new CAACompactingRecipeGen(output, registries, CreateAquaticAmbitions.MODID));
        GENERATORS.add(new CAAMixingRecipeGen(output, registries, CreateAquaticAmbitions.MODID));
        GENERATORS.add(new CAAChannelingRecipeGen(output, registries, CreateAquaticAmbitions.MODID));

        gen.addProvider(true, new DataProvider() {

            @Override
            public String getName() {
                return CreateAquaticAmbitions.MODID + "' Processing Recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(dc))
                        .toArray(CompletableFuture[]::new));
            }
        });
    }

    protected static class I {

        static ItemLike prismarineAlloy() {
            return CAAItems.PRISMARINE_ALLOY.get();
        }

        static ItemLike conduit() {
            return Items.CONDUIT.asItem();
        }

        static ItemLike copperNugget() {
            return AllItems.COPPER_NUGGET.get();
        }

        static ItemLike brainCoral() {
            return Items.BRAIN_CORAL.asItem();
        }

        static ItemLike fireCoral() {
            return Items.FIRE_CORAL.asItem();
        }

        static ItemLike bubbleCoral() {
            return Items.BUBBLE_CORAL.asItem();
        }

        static ItemLike hornCoral() {
            return Items.HORN_CORAL.asItem();
        }

        static ItemLike tubeCoral() {
            return Items.TUBE_CORAL.asItem();
        }

    }
}
