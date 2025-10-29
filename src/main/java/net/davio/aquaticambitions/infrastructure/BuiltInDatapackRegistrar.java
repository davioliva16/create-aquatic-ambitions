package net.davio.aquaticambitions.infrastructure;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.Optional;

public class BuiltInDatapackRegistrar {
    private static final String HARVERSTABLE_CORALS_ID = "harverstable_corals";
    private static final String SILKLESS_CORALS_ID = "silkless_corals";
    public static final PackLocationInfo HARVERSTABLE_CORALS_PACK = new PackLocationInfo(HARVERSTABLE_CORALS_ID, Component.literal("CAA Harverstable Corals"), PackSource.DEFAULT, Optional.empty());
    public static final PackLocationInfo SILKLESS_CORALS_PACK = new PackLocationInfo(SILKLESS_CORALS_ID, Component.literal("CAA Silkless Corals"), PackSource.DEFAULT, Optional.empty());

    public static void registerPackRepository(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            event.addRepositorySource(s -> s.accept(Pack.readMetaAndCreate(HARVERSTABLE_CORALS_PACK, new PathPackResources.PathResourcesSupplier(ModList.get().getModFileById(CreateAquaticAmbitions.MODID).getFile().findResource("datapacks/" + HARVERSTABLE_CORALS_ID)), PackType.SERVER_DATA, new PackSelectionConfig(false, Pack.Position.TOP, false))));
            event.addRepositorySource(s -> s.accept(Pack.readMetaAndCreate(SILKLESS_CORALS_PACK, new PathPackResources.PathResourcesSupplier(ModList.get().getModFileById(CreateAquaticAmbitions.MODID).getFile().findResource("datapacks/" + SILKLESS_CORALS_ID)), PackType.SERVER_DATA, new PackSelectionConfig(false, Pack.Position.TOP, false))));
        }
    }
}

