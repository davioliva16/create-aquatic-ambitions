package net.davio.aquaticambitions.infrastructure.data.recipe;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class CAACompactingRecipeGen extends CompactingRecipeGen {
    GeneratedRecipe

    CALCITE =  create(CreateAquaticAmbitions.asResource("calcite"), b -> b
            .require(Fluids.LAVA,100)
            .require(Items.GRAVEL)
            .require(CAAItems.CALCIUM_RICH_POWDER)
            .output(Items.CALCITE, 1));

    public CAACompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }
}
