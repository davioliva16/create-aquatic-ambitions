package net.davio.aquaticambitions.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public class CAAServerConfig extends ConfigBase {
    public final CAAConduitCageConfig conduitCage = nested(0, CAAConduitCageConfig::new, Comments.conduitCage);
    @Override
    public String getName() {
        return "server";
    }

    static class Comments {
        static final String conduitCage = "Parameters and abilities of the Conduit Cage";
        static final String coralFarming = "Parameters related to Coral Farming";
    }
}
