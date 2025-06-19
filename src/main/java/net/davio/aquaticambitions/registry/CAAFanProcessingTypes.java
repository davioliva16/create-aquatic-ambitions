package net.davio.aquaticambitions.registry;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingFanProcessingType;
import net.minecraft.core.Registry;

public class CAAFanProcessingTypes {
    static {
        Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, CreateAquaticAmbitions.asResource("channeling"), new ChannelingFanProcessingType());
    }

    public static void init() {
    }
}