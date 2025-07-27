package net.davio.aquaticambitions.infrastructure.data.recipe;

import com.simibubi.create.api.data.recipe.MillingRecipeGen;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class CAAMillingRecipeGen extends MillingRecipeGen {
    GeneratedRecipe

    SUSPICIOUS_ROCK =  create(() -> AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get(), b -> b.duration(50)
            .output(CAAItems.CALCIUM_RICH_POWDER,1)
            .output(0.5f,CAAItems.CALCIUM_RICH_POWDER,1)
            .output(.05f, CAAItems.SUSPICIOUS_ROCK));

    public CAAMillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }
}
