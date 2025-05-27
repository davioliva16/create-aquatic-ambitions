package net.davio.aquaticambitions.content.processing.conduit;

import com.mojang.serialization.Codec;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import net.createmod.catnip.lang.Lang;
import net.davio.aquaticambitions.registry.CAABlockEntityTypes;
import net.davio.aquaticambitions.registry.CAAShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


public class MechanicalConduitBlock extends Block implements IBE<MechanicalConduitBlockEntity>, IWrenchable {

    public static final EnumProperty<ConduitPowerLevel> CONDUIT_POWER_LEVEL = EnumProperty.create("conduit", ConduitPowerLevel.class);

    public MechanicalConduitBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(CONDUIT_POWER_LEVEL, ConduitPowerLevel.IDLE)
        );
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CONDUIT_POWER_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState defaultState = defaultBlockState();
        return defaultState.setValue(CONDUIT_POWER_LEVEL, ConduitPowerLevel.IDLE);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        return CAAShapes.MECH_CONDUIT_SHAPE;
    };

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context == CollisionContext.empty()) return AllShapes.HEATER_BLOCK_SPECIAL_COLLISION_SHAPE;
        return getShape(blockState, level, pos, context);
    };

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) != 0)
            return;

        if (state.getValue(CONDUIT_POWER_LEVEL).equals(ConduitPowerLevel.AWAKENED)) {

        world.playLocalSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
                (double) ((float) pos.getZ() + 0.5F), SoundEvents.CONDUIT_AMBIENT, SoundSource.BLOCKS,
                0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
        }
    }

    public static ConduitPowerLevel getConduitLevelOf(BlockState blockState) {
        return blockState.getValue(CONDUIT_POWER_LEVEL);
    };

    @Override
    public Class<MechanicalConduitBlockEntity> getBlockEntityClass() {
        return MechanicalConduitBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MechanicalConduitBlockEntity> getBlockEntityType() {
        return CAABlockEntityTypes.MECHANICAL_CONDUIT.get();
    }


    public static int getLight(BlockState state) {
        ConduitPowerLevel level = state.getValue(CONDUIT_POWER_LEVEL);
        return switch (level) {
            case IDLE -> 8;
            default -> 15;
        };
    }

    public static enum ConduitPowerLevel implements StringRepresentable {
        IDLE,
        AWAKENED;

        public static final Codec<ConduitPowerLevel> CODEC = StringRepresentable.fromEnum(ConduitPowerLevel::values);

        public boolean isAwakened() {
            return this.ordinal() >= ConduitPowerLevel.AWAKENED.ordinal();
        }

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }
    };
}
