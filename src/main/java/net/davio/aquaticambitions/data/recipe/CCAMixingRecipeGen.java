package net.davio.aquaticambitions.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.MixingRecipeGen;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CCAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class CCAMixingRecipeGen extends MixingRecipeGen {

    public CCAMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    GeneratedRecipe

    PRISMARINE_ALLOY = create(CreateAquaticAmbitions.asResource("prismarine_alloy"), b -> b
            .require(Blocks.PRISMARINE)
            .require(AllItems.COPPER_NUGGET)
            .output(CCAItems.PRISMARINE_ALLOY, 1));

}
