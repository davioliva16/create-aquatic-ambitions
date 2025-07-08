package net.davio.aquaticambitions.infrastructure;


import com.simibubi.create.foundation.pack.ModFilePackResources;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.infrastructure.config.CAAConfigs;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

@Mod.EventBusSubscriber(modid = CreateAquaticAmbitions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BuiltinDatapackRegistrar {

    @SubscribeEvent
    public static final void addPackFinders(AddPackFindersEvent event) {
        IModFileInfo modFileInfo = ModList.get().getModFileById(CreateAquaticAmbitions.MODID);
        if (modFileInfo == null) {
            return;
        };
        IModFile modFile = modFileInfo.getFile();

        // Datapacks
        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(CreateAquaticAmbitions.asResource("harverstable_corals").toString(),
                    Component.literal("CAA Harverstable Corals"),
                    false,
                    id -> new ModFilePackResources(id, modFile, "datapacks/harverstable_corals"),
                    PackType.SERVER_DATA, Pack.Position.TOP, PackSource.DEFAULT);
            if (pack != null) consumer.accept(pack);
        });

        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(CreateAquaticAmbitions.asResource("silkless_corals").toString(),
                    Component.literal("CAA Silkless Corals"),
                    false,
                    id -> new ModFilePackResources(id, modFile, "datapacks/silkless_corals"),
                    PackType.SERVER_DATA, Pack.Position.TOP, PackSource.DEFAULT);
            if (pack != null ) consumer.accept(pack);
        });
    }
}
