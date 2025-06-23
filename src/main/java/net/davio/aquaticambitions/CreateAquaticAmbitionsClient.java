package net.davio.aquaticambitions;

import net.createmod.ponder.foundation.PonderIndex;
import net.davio.aquaticambitions.ponder.CAAPonderPlugin;
import net.davio.aquaticambitions.registry.CAAPartials;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = CreateAquaticAmbitions.MODID, dist = Dist.CLIENT)
public class CreateAquaticAmbitionsClient {

    public CreateAquaticAmbitionsClient() {
        CAAPartials.init();
    }

    public static void onCtorClient(IEventBus modEventBus) {

        IEventBus neoEventBus = NeoForge.EVENT_BUS;
        modEventBus.addListener(CreateAquaticAmbitionsClient::clientInit);

    }

    public static void clientInit(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CAAPonderPlugin());
    }
}
