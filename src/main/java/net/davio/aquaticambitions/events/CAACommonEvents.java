package net.davio.aquaticambitions.events;

import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlockEntity;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitEffectDefinition;
import net.davio.aquaticambitions.registry.CAARegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;


public class CAACommonEvents {
    @EventBusSubscriber
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            MechanicalConduitBlockEntity.registerCapabilities(event);
        }

        @SubscribeEvent
        public static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
            // Same codec for data and network — definitions reference the mob effect and fluid tag by id, so
            // there's no tag-content to resolve client-side (unlike Create Diesel Generators' FuelType).
            event.dataPackRegistry(CAARegistries.CONDUIT_EFFECT, MechanicalConduitEffectDefinition.CODEC, MechanicalConduitEffectDefinition.CODEC);
        }
    }
}
