package net.davio.aquaticambitions.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;
import net.createmod.catnip.config.ui.ConfigAnnotations;

public class CAAConduitCageConfig extends ConfigBase {
    public final ConfigInt conduitCageRange = i(32, 4,96,
            "conduitCageRange",
            Comments.conduitCageRange);
    public final ConfigInt waterAwakenConversionRate = i(20, 5,600,
            "waterConversionRate",
            Comments.waterConversionRate);
    public final ConfigInt potionAwakenConversionRate = i(300, 5,3600,
            "potionConversionRate",
            Comments.potionConversionRate);
    public final ConfigInt fluidAwakenConversionRate = i(60, 5,1200,
            "fluidConversionRate",
            Comments.fluidConversionRate);
    public final ConfigInt awakenedTimeLimit = i(7200, 1,12000,
            "awakenedTimeLimit",
            Comments.awakenedTimeLimit,
            ConfigAnnotations.RequiresRestart.SERVER.asComment());
    public final ConfigInt conduitFluidCapacity = i(1000, 1000,256000,
            "conduitFluidCapacity",
            Comments.conduitFluidCapacity);


    static class Comments {
        static final String conduitCageRange = "Maximum range of the conduit cage diameter (4-96).";
        static final String waterConversionRate = "How many seconds of awakening are added per bucket of water. Values below 20 will require high pump RPMs";
        static final String potionConversionRate = "How many seconds of awakening are added per bucket of potion";
        static final String fluidConversionRate = "How many seconds of awakening are added per bucket of fluid. Used for anything but water and potions";
        static final String awakenedTimeLimit = "Max amount of seconds a conduit can accumulate before it starts rejecting fluids. Lower this if want conduit cages to need constant fluid supply";
        static final String conduitFluidCapacity = "How much fluid (in mB) the conduit accepts in a single insertion, e.g. from a fluid pipe or a package. Defaults to 1 bucket. Raise this only if you need the conduit to accept larger single transfers, such as multi-bucket Fluid Logistics packages";
    }

    @Override
    public String getName() {
        return "conduit_cage";
    }

}
