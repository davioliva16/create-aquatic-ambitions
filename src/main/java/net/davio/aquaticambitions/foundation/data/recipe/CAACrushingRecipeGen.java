package net.davio.aquaticambitions.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CAACrushingRecipeGen extends CrushingRecipeGen {
    GeneratedRecipe

    PRISMARINE_BRICKS_TO_LAPIS_EXP_NUGGETS = create(CreateAquaticAmbitions.asResource("prismarine_bricks_to_lapis_and_copper"), b -> b
        .duration(150)
        .require(Items.PRISMARINE_BRICKS)
        .output(.9f, Items.LAPIS_LAZULI, 1)
        .output( 1,  AllItems.EXP_NUGGET, 2)
        .output(.5f, AllItems.EXP_NUGGET, 1)
        .output(.125f, AllItems.COPPER_NUGGET, 1)),

    PRISMARINE__TO_LAPIS_EXP_NUGGETS = create(CreateAquaticAmbitions.asResource("prismarine_to_lapis"), b -> b
        .duration(150)
        .require(Items.PRISMARINE)
        .output(.5f, Items.LAPIS_LAZULI, 1)
        .output( .75f,  AllItems.EXP_NUGGET, 1));

    public CAACrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }
}

