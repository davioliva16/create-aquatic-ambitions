package net.davio.aquaticambitions.registry;

import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingFanProcessingType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CAAFanProcessingTypes {
    private static final DeferredRegister<FanProcessingType> TYPES = DeferredRegister
            .create(CreateRegistries.FAN_PROCESSING_TYPE, CreateAquaticAmbitions.MODID);

    public static final DeferredHolder<FanProcessingType, ChannelingFanProcessingType> CHANNELING = TYPES
            .register("channeling", ChannelingFanProcessingType::new);

    public static void register(IEventBus modEventBus) {
        TYPES.register(modEventBus);
    }
}
