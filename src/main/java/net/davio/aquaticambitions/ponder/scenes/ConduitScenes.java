package net.davio.aquaticambitions.ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class ConduitScenes {

    public static void processing(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("conduit_processing", "processing");

        BlockPos conduitPos = util.grid().at(3, 3, 3);
        BlockPos depotPos = util.grid().at(0, 2, 3);

        Selection ringTop = util.select().fromTo(3, 5, 1, 3, 5, 5);
        Selection ringBottom = util.select().fromTo(3, 1, 1, 3, 1, 5);
        Selection ringLeft = util.select().fromTo(3, 1, 1, 5, 5, 1);
        Selection ringRight = util.select().fromTo(3, 1, 5, 5, 5, 5);

        Selection mainWater = util.select().fromTo(2, 2, 2, 4, 4, 4);
        Selection frontWater = util.select().fromTo(1, 2, 2, 1, 4, 4);

        Selection fans = util.select().fromTo(7, 0, 2, 6, 4, 4);

        Selection depot = util.select().position(0, 2, 3);
        Selection belowDepot = util.select().position(0, 1, 3);
        Selection depotSelection = util.select().fromTo(0, 2, 3, 0, 1, 3);

        scene.configureBasePlate(0, 0, 7);

        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(ringTop, Direction.SOUTH);
        scene.world().showSection(ringBottom, Direction.SOUTH);
        scene.world().showSection(ringLeft, Direction.SOUTH);
        scene.world().showSection(ringRight, Direction.SOUTH);

        scene.idle(10);

        scene.world().showSection(mainWater, Direction.WEST);

        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .pointAt(util.vector().centerOf(conduitPos))
                .placeNearTarget()
                .text("Awakened Conduits can be used for fan processing");

        scene.idle(70);

        scene.world().setKineticSpeed(fans, -48);
        scene.world().showSection(fans, Direction.WEST);

        scene.idle(50);

        scene.overlay().showOutlineWithText(util.select().fromTo(1, 2, 2, 0, 4, 4), 60)
                .attachKeyFrame()
                .colored(PonderPalette.MEDIUM)
                .pointAt(util.vector().centerOf(0, 2, 2))
                .placeNearTarget()
                .text("Air Passing through an Conduit Cage will create a Channeling Setup");

        scene.idle(70);

        scene.world().showSection(frontWater, Direction.DOWN);

        scene.idle(10);

        scene.overlay().showOutlineWithText(util.select().fromTo(1, 2, 2, 1, 4, 4), 60)
                .attachKeyFrame()
                .colored(PonderPalette.RED)
                .pointAt(util.vector().centerOf(0, 2, 2))
                .placeNearTarget()
                .text("Extra layers of water will dillute the Conduit's Power, thus creating a Washing Setup instead");

        scene.idle(70);

        scene.world().hideSection(frontWater, Direction.UP);
        
        scene.idle(10);

        scene.world().setBlocks(depot, AllBlocks.DEPOT.getDefaultState(), false);
        scene.world().setBlocks(belowDepot, Blocks.WHITE_CONCRETE.defaultBlockState(), false);

        scene.world().showSection(depotSelection, Direction.UP);

        ItemStack flint = new ItemStack(Items.FLINT);
        ItemStack prismarine_shard = new ItemStack(Items.PRISMARINE_SHARD);

        scene.world().createItemOnBeltLike(depotPos, Direction.EAST, flint);
        scene.idle(10);

        Vec3 itemVec = util.vector().blockSurface(util.grid().at(depotPos.getX(), depotPos.getY(), depotPos.getZ()), Direction.UP)
                .add(0.1, 0, 0);
        scene.overlay().showControls(itemVec, Pointing.DOWN, 20).withItem(flint);

        scene.idle(30);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .pointAt(util.vector().topOf(util.grid().at(depotPos.getX(), depotPos.getY(), depotPos.getZ())))
                .placeNearTarget()
                .text("Items caught in the Channeling stream will be processed");

        scene.idle(90);

        scene.world().removeItemsFromBelt(depotPos);
        scene.world().createItemOnBeltLike(depotPos, Direction.UP, prismarine_shard);

        scene.idle(10);

        scene.overlay().showControls(itemVec, Pointing.DOWN, 20).withItem(prismarine_shard);

        scene.idle(10);

        scene.markAsFinished();
    }
}
