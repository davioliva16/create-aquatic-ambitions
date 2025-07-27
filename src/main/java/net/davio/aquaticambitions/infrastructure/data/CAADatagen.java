package net.davio.aquaticambitions.infrastructure.data;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.infrastructure.data.loot.CAAGlobalLootModifierProvider;
import net.davio.aquaticambitions.infrastructure.data.recipe.CAARecipeProvider;
import net.davio.aquaticambitions.infrastructure.data.recipe.CAAStandardRecipeGen;
import net.davio.aquaticambitions.infrastructure.data.tags.CAARegistrateTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class CAADatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(CreateAquaticAmbitions.MODID))
            addExtraRegistrateData();
    }

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new CAAStandardRecipeGen(output, lookupProvider));
        generator.addProvider(event.includeServer(), new CAAGlobalLootModifierProvider(output, lookupProvider));

        if (event.includeServer()) {
            CAARecipeProvider.registerAllProcessing(generator, output, lookupProvider);
        }
    }

    private static void addExtraRegistrateData() {
        CAARegistrateTags.addGenerators();
    }
}

