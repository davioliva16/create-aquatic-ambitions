package net.davio.aquaticambitions.ponder.util.fakeConduitEntity;

import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.davio.aquaticambitions.registry.CAABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;

public class FakeActiveConduitBlock extends Block implements IBE<FakeActiveConduitBlockEntity>, ProperWaterloggedBlock {

    //The ENTIRE point of this block is being able to render a conduit in ponder scenes.
    //It will not be available anywhere but with commands

    public FakeActiveConduitBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public Class<FakeActiveConduitBlockEntity> getBlockEntityClass() {
        return FakeActiveConduitBlockEntity.class;
    }


    public BlockEntityType<? extends FakeActiveConduitBlockEntity> getBlockEntityType() {
        return CAABlockEntityTypes.FAKE_CONDUIT.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState defaultState = defaultBlockState();
        return withWater(defaultState, context);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return pState;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }
}