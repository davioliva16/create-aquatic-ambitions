package net.davio.aquaticambitions.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.WashingRecipeGen;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CAAWashingDataGen extends WashingRecipeGen {

    GeneratedRecipe

    WASHING_SUS_ROCK =  create(CreateAquaticAmbitions.asResource("suspicious_rock"), b -> b
        .require(CAAItems.SUSPICIOUS_ROCK)
        .output(0.5f, Items.BONE_MEAL, 2)
        .output(.33f, Items.BONE, 1)
        .output(.05f, Items.NAUTILUS_SHELL, 1));


    public CAAWashingDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }
}
