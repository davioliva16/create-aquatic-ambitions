package net.davio.aquaticambitions;

import net.createmod.ponder.foundation.PonderIndex;
import net.davio.aquaticambitions.ponder.CAAPonderPlugin;
import net.davio.aquaticambitions.registry.CAAPartials;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CreateAquaticAmbitionsClient {

    public CreateAquaticAmbitionsClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CAAPartials.init();
        modEventBus.register(this);

    }

    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CAAPonderPlugin());
    }
}
