package net.davio.aquaticambitions.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.PolishingRecipeGen;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CAAPolishingRecipeGen extends PolishingRecipeGen {

    GeneratedRecipe

    NAUTILOUS = create(CAAItems.SUSPICIOUS_ROCK::get, b -> b.output(Items.NAUTILUS_SHELL))
    ;

    public CAAPolishingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }

}
