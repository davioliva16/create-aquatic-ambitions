package net.davio.aquaticambitions.registry;

import com.simibubi.create.AllShapes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CAAShapes {
    public static final VoxelShape

            MECH_CONDUIT_SHAPE = shape(1, -2, 1, 15, 14, 15)
            .build();

    public static AllShapes.Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new AllShapes.Builder(Block.box(x1, y1, z1, x2, y2, z2));
    };
}
