package net.davio.aquaticambitions.events;

import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CCACommonEvents {
    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            MechanicalConduitBlockEntity.registerCapabilities(event);
        }
    }
}
