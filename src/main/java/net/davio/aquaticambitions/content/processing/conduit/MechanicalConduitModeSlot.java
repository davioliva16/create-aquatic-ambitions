package net.davio.aquaticambitions.content.processing.conduit;

import com.simibubi.create.foundation.blockEntity.behaviour.*;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MechanicalConduitModeSlot extends ValueBoxTransform.Sided {

    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        Direction side = getSide();
        float horizontalAngle = AngleHelper.horizontalAngle(side);
        Vec3 southLocation = VecHelper.voxelSpace(8, 3, 14.5f);
        return VecHelper.rotateCentered(southLocation, horizontalAngle, Direction.Axis.Y);
    }

    @Override
    protected Vec3 getSouthLocation() {
        return Vec3.ZERO;
    }

    }

