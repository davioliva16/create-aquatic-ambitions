package net.davio.aquaticambitions.foundation.data;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.tterrag.registrate.providers.ProviderType;
import net.davio.aquaticambitions.foundation.data.recipe.CAARecipeProvider;
import net.davio.aquaticambitions.foundation.data.recipe.CAAStandardRecipeGen;
import net.davio.aquaticambitions.foundation.data.tags.CAARegistrateTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CAADatagen {

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        addExtraRegistrateData();

        generator.addProvider(event.includeServer(), new CAAStandardRecipeGen(output, lookupProvider));

        if (event.includeServer()) {
            CAARecipeProvider.registerAllProcessing(generator, output, lookupProvider);
        }
    }

    private static void addExtraRegistrateData() {
        CAARegistrateTags.addGenerators();
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            //BAdvancements.provideLang(langConsumer);
            //provideDefault(langConsumer);
        });
    }

}

