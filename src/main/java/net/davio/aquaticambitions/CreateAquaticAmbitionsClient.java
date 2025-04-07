package net.davio.aquaticambitions;

import net.davio.aquaticambitions.registry.CCAPartials;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = CreateAquaticAmbitions.MODID, dist = Dist.CLIENT)
public class CreateAquaticAmbitionsClient {

    public static void onCtorClient(IEventBus modEventBus) {

        IEventBus neoEventBus = NeoForge.EVENT_BUS;
        modEventBus.addListener(CreateAquaticAmbitionsClient::clientInit);

    }

    public static void clientInit(final FMLClientSetupEvent event) {
        CCAPartials.init();
    }
}
