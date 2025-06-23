package net.davio.aquaticambitions;

import net.createmod.ponder.foundation.PonderIndex;
import net.davio.aquaticambitions.ponder.CAAPonderPlugin;
import net.davio.aquaticambitions.registry.CAAPartials;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateAquaticAmbitionsClient {

    public CreateAquaticAmbitionsClient() {
        CAAPartials.init();
    }

    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(CreateAquaticAmbitionsClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        CAAPartials.init();
        PonderIndex.addPlugin(new CAAPonderPlugin());
    }
}
