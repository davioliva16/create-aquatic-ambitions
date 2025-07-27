package net.davio.aquaticambitions.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CAAServerConfig extends ConfigBase {
    public final CAAConduitCageConfig conduitCage = nested(0, CAAConduitCageConfig::new, Comments.conduitCage);
    @Override
    public String getName() {
        return "server";
    }

    static class Comments {
        static final String conduitCage = "Parameters and abilities of the Conduit Cage";
    }
}