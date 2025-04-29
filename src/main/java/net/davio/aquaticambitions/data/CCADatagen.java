package net.davio.aquaticambitions.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import net.davio.aquaticambitions.data.recipe.ChannelingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.MODID;
import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CCADatagen {
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new ChannelingRecipeGen(output, lookupProvider));
        //generator.addProvider(server, new CCAAdvancements(output, lookupProvider));

        event.getGenerator().addProvider(true, REGISTRATE.setDataProvider(new RegistrateDataProvider(REGISTRATE, MODID, event)));
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/create_aquatic_ambitions/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
    /*
    private static void providePonderLang(BiConsumer<String, String> consumer) {
        // Register this since FMLClientSetupEvent does not run during datagen
        CCAPonderIndex.addPlugin(new CreatePonderPlugin());
        CCAPonderIndex.getLangAccess().provideLang(CreateAquaticAmbitions.MODID, consumer);
     */
}

