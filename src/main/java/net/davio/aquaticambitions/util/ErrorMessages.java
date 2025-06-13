package net.davio.aquaticambitions.util;

import com.google.common.base.MoreObjects;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class ErrorMessages {
    public static String notNull(String name) {
        return name + " must not be null";
    }

    public static String level(@Nullable Level level) {
        return level == null ? "unknown" : level.dimension().toString();
    }

    public static String pos(BlockPos blockPos) {
        return "(" + blockPos.toShortString() + ")";
    }

    public static String blockEntity(BlockEntity blockEntity) {
        return MoreObjects.toStringHelper(blockEntity)
                .add("type", Util.getRegisteredName(BuiltInRegistries.BLOCK_ENTITY_TYPE, blockEntity.getType()))
                .add("level", level(blockEntity.getLevel()))
                .add("pos", pos(blockEntity.getBlockPos()))
                .omitNullValues()
                .toString();
    }
}