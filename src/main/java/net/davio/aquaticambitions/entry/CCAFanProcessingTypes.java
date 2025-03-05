package net.davio.aquaticambitions.entry;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.kinetics.fan.processing.ChannellingProcessingType;
import net.minecraft.core.Registry;

public class CCAFanProcessingTypes {
    static {
        Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, CreateAquaticAmbitions.asResource("channeling"), new ChannellingProcessingType());
    }

    public static void init() {
    }
}