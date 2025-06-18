package net.davio.aquaticambitions.foundation.data;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.tterrag.registrate.providers.ProviderType;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.foundation.data.recipe.CAARecipeProvider;
import net.davio.aquaticambitions.foundation.data.recipe.CAAStandardRecipeGen;
import net.davio.aquaticambitions.foundation.data.tags.CAARegistrateTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

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

        if (event.includeServer()) {
            CAARecipeProvider.registerAllProcessing(generator, output, lookupProvider);
        }
    }

    private static void addExtraRegistrateData() {
        CAARegistrateTags.addGenerators();
    }
}

