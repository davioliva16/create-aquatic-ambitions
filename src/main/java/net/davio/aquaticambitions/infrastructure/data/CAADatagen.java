package net.davio.aquaticambitions.infrastructure.data;

import net.davio.aquaticambitions.infrastructure.data.loot.CAAGlobalLootModifierProvider;
import net.davio.aquaticambitions.infrastructure.data.recipe.CAARecipeProvider;
import net.davio.aquaticambitions.infrastructure.data.recipe.CAAStandardRecipeGen;
import net.davio.aquaticambitions.infrastructure.data.tags.CAARegistrateTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class CAADatagen {

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        addExtraRegistrateData();

        if (event.includeServer()) {
            CAARecipeProvider.registerAllProcessing(generator, output);
            generator.addProvider(true, new CAAStandardRecipeGen(output));
            generator.addProvider(true, new CAAGlobalLootModifierProvider(output));
        }
    }

    private static void addExtraRegistrateData() {
        CAARegistrateTags.addGenerators();
    }
}

