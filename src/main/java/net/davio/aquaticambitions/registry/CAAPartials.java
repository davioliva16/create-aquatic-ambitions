package net.davio.aquaticambitions.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.davio.aquaticambitions.CreateAquaticAmbitions;

public class CAAPartials {

    public static final PartialModel

        CONDUIT_CAGE = block("conduit_cage"),
        INACTIVE_CONDUIT = block("inactive_conduit"),
        CONDUIT_EYE = block("conduit_eye"),
        CONDUIT_WIND = block("conduit_wind");

    private static PartialModel block(String path) {
        return PartialModel.of(CreateAquaticAmbitions.asResource("block/" + path));
    }

    public static void init() {
        // init static fields
    }
}
