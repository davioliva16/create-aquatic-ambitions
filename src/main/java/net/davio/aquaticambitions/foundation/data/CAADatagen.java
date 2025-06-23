package net.davio.aquaticambitions.foundation.data;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.tterrag.registrate.providers.ProviderType;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.foundation.data.loot.CAAGlobalLootModifierProvider;
import net.davio.aquaticambitions.foundation.data.recipe.CAARecipeProvider;
import net.davio.aquaticambitions.foundation.data.recipe.CAAStandardRecipeGen;
import net.davio.aquaticambitions.foundation.data.tags.CAARegistrateTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

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

