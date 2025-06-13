package net.davio.aquaticambitions.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class CAAMixingRecipeGen extends MixingRecipeGen {
    GeneratedRecipe

    PRISMARINE_ALLOY = create(CreateAquaticAmbitions.asResource("prismarine_alloy"), b -> b
            .require(Blocks.PRISMARINE)
            .require(AllItems.COPPER_NUGGET)
            .output(CAAItems.PRISMARINE_ALLOY, 1));

    public CAAMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }
}