package net.davio.aquaticambitions.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.davio.aquaticambitions.data.tags.CAARegistrateTags;
import net.davio.aquaticambitions.util.CAALang;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CAADatagen {

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        addExtraRegistrateData();
        if(event.includeServer()) {
            //CAARecipeProvider.registerProcessing(generator, output);
            //generator.addProvider(true, CAARecipeProvider);
            //generator.addProvider(true, CAAAdvancements(output));
            //generator.addProvider(true, new CAAStandardRecipeGen(output));
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

    private static void provideDefault(BiConsumer<String, String> consumer) {
        //CAALang.provideLang(consumer);
    }

}

