package net.davio.aquaticambitions.ponder.scenes;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;

import net.createmod.catnip.math.Pointing;
import net.createmod.catnip.math.VecHelper;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.ParrotElement;
import net.createmod.ponder.api.element.ParrotPose;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock;
import net.davio.aquaticambitions.registry.CAAIcons;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class MechanicalConduitScenes {

    public static void awaken(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("mechanical_conduit_awaken", "awaken");

        BlockPos conduitPos = util.grid().at(2, 2, 2);
        BlockPos pumpPos = util.grid().at(1, 1, 2);
        BlockPos tankPos = util.grid().at(1, 1, 3);
        BlockPos cog1Pos = util.grid().at(5, 1, 1);
        BlockPos cog2Pos = util.grid().at(1, 1, 1);
        BlockPos cogLargePos =  util.grid().at(5, 0, 2);


        Selection pump = util.select().position(pumpPos);

        Selection fluidStuff = util.select().fromTo(1, 1, 2, 0, 3, 4);
        Selection bottomPipe = util.select().position(2,1,2); //Save selection before pipe is replaced - idk if this is how that works

        Selection kinetics1 = util.select().fromTo(cog1Pos, cog2Pos);
        Selection kinetics2 = util.select().position(cogLargePos); //Save selection before pipe is replaced - idk if this is how that works

        ElementLink<WorldSectionElement> conduitSelection =
                scene.world().showIndependentSection(util.select().position(conduitPos), Direction.DOWN);

        scene.world().moveSection(conduitSelection, util.vector().of(0, -1, 0), 0);

        scene.configureBasePlate(0, 0, 5);

        scene.showBasePlate();

        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .pointAt(util.vector().centerOf(util.grid().at(2, 1, 2)))
                .placeNearTarget()
                .text("Conduit Cages can be used as more compact version of the conduit's prismarine ring");

        scene.idle(70);

        FluidStack water = new FluidStack(Fluids.WATER,16000);

        scene.world().modifyBlockEntity(tankPos, FluidTankBlockEntity.class, be -> be.getTankInventory()
                .fill(water, IFluidHandler.FluidAction.EXECUTE));

        scene.world().moveSection(conduitSelection, util.vector().of(0, 1, 0), 10);
        scene.idle(10);

        scene.world().showSection(fluidStuff, Direction.NORTH);
        scene.world().showSection(bottomPipe, Direction.NORTH);

        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .pointAt(util.vector().centerOf(util.grid().at(1, 1, 2)))
                .placeNearTarget()
                .text("Fluids can be pumped into the conduit cage through its bottom face");

        scene.idle(70);

        scene.world().setKineticSpeed(kinetics1, -32);
        scene.world().setKineticSpeed(kinetics2, 32);
        scene.world().showSection(kinetics1, Direction.WEST);
        scene.world().showSection(kinetics2, Direction.WEST);

        scene.idle(10);

        scene.world().setKineticSpeed(pump, 32);
        scene.effects().rotationSpeedIndicator(pumpPos);

        scene.world().modifyBlock(conduitPos, s -> s.setValue(
                MechanicalConduitBlock.CONDUIT_POWER_LEVEL,
                MechanicalConduitBlock.ConduitPowerLevel.AWAKENED),
                false);

        scene.effects().indicateSuccess(conduitPos);

        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .pointAt(util.vector().centerOf(util.grid().at(2, 2, 2)))
                .placeNearTarget()
                .text("Pumping a fluid into a conduit cage will awaken it");

        scene.idle(70);

        scene.markAsFinished();
    }
    public static void processing(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("mechanical_conduit_processing", "processing");

        BlockPos conduitPos = util.grid().at(2, 2, 2);
        BlockPos pumpPos = util.grid().at(2, 1, 3);
        BlockPos tankPos = util.grid().at(2, 1, 4);
        BlockPos cog1Pos = util.grid().at(3, 1, 3);
        BlockPos cog2Pos = util.grid().at(3, 1, 5);
        BlockPos cogLargePos =  util.grid().at(4, 0, 5);
        BlockPos belt1Pos =  util.grid().at(5, 2, 2);
        BlockPos belt2Pos =  util.grid().at(5, 0, 2);
        BlockPos fanPos =  util.grid().at(4, 2, 2);
        BlockPos fanBasePos =  util.grid().at(4, 1, 2);
        BlockPos depotPos =  util.grid().at(0, 1, 2);

        Selection pump = util.select().position(pumpPos);
        Selection fluidStuff = util.select().fromTo(2, 1, 2, 2, 3, 4);
        Selection kinetics1 = util.select().fromTo(cog1Pos,cog2Pos);
        Selection kinetics2 = util.select().position(cogLargePos);
        Selection belt = util.select().fromTo(belt1Pos,belt2Pos);
        Selection fan = util.select().position(fanPos);
        Selection fanBase = util.select().position(fanBasePos);
        Selection depot = util.select().position(depotPos);

        scene.configureBasePlate(0, 0, 5);

        scene.showBasePlate();
        scene.idle(10);

        FluidStack water = new FluidStack(Fluids.WATER,16000);

        scene.world().modifyBlockEntity(tankPos, FluidTankBlockEntity.class, be -> be.getTankInventory()
                .fill(water, IFluidHandler.FluidAction.EXECUTE));


        scene.world().showSection(fluidStuff, Direction.NORTH);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .pointAt(util.vector().centerOf(util.grid().at(2, 2, 2)))
                .placeNearTarget()
                .text("Conduits Cages can also be used for fan processing");

        scene.idle(80);

        scene.world().setKineticSpeed(kinetics1, -32);
        scene.world().setKineticSpeed(kinetics2, 32);
        scene.world().showSection(kinetics1, Direction.NORTH);
        scene.world().showSection(kinetics2, Direction.NORTH);

        scene.idle(10);

        scene.world().setKineticSpeed(pump, 32);
        scene.effects().rotationSpeedIndicator(pumpPos);

        scene.world().modifyBlock(conduitPos, s -> s.setValue(
                MechanicalConduitBlock.CONDUIT_POWER_LEVEL,
                MechanicalConduitBlock.ConduitPowerLevel.AWAKENED),
                false);

        scene.effects().indicateSuccess(conduitPos);

        scene.idle(10);

        scene.world().setKineticSpeed(belt, -16);
        scene.world().setKineticSpeed(fan, -16);
        scene.world().showSection(belt, Direction.WEST);
        scene.world().showSection(fan, Direction.WEST);
        scene.world().showSection(fanBase, Direction.UP);

        scene.idle(30);

        scene.overlay().showOutlineWithText(util.select().fromTo(1, 2, 2, 0, 2, 2), 70)
                .attachKeyFrame()
                .colored(PonderPalette.MEDIUM)
                .pointAt(util.vector().centerOf(0, 2, 2))
                .placeNearTarget()
                .text("Air Passing through an Conduit Cage will create a Channeling Setup");

        scene.idle(80);

        scene.world().showSection(depot, Direction.EAST);
        scene.idle(10);

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

        scene.idle(30);

        scene.markAsFinished();
    }

    public static void effects(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        RandomSource random = RandomSource.create();
        scene.title("mechanical_conduit_effects", "effects");

        //Fluid Stuff
        BlockPos conduitPos = util.grid().at(3, 2, 1);
        BlockPos pump1Pos = util.grid().at(3, 1, 2);
        BlockPos tankStart = util.grid().at(3, 1, 3);
        BlockPos tankEnd = util.grid().at(3, 3, 3);

        //For Pumping into Conduit
        BlockPos cog1Pos = util.grid().at(2, 1, 2);
        BlockPos cog2Pos = util.grid().at(2, 1, 5);
        BlockPos cog3Pos =  util.grid().at(1, 0, 5);

        //birb
        BlockPos birbPos =  util.grid().at(1, 0, 1);

        Selection conduit = util.select().fromTo(conduitPos,conduitPos.offset(0,-1,0));
        Selection pump1 = util.select().position(pump1Pos);

        Selection tank = util.select().fromTo(tankStart, tankEnd);
        Selection kinetics1 = util.select().fromTo(cog1Pos,cog2Pos);
        Selection kinetics2 = util.select().position(cog3Pos);

        scene.configureBasePlate(0, 0, 5);

        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(conduit, Direction.NORTH);
        scene.world().showSection(pump1, Direction.NORTH);
        ElementLink<WorldSectionElement> tankLink = scene.world().showIndependentSection(tank, Direction.NORTH);

        FluidStack water = new FluidStack(Fluids.WATER,16000);

        scene.world().modifyBlockEntity(tankStart, FluidTankBlockEntity.class, be -> be.getTankInventory()
                .fill(water, IFluidHandler.FluidAction.EXECUTE));

        scene.idle(10);

        ElementLink<ParrotElement> flappyBirb = scene.special().createBirb(util.vector().topOf(birbPos), ParrotPose.FlappyPose::new);

        scene.overlay().showText(70)
                .text("Conduit Cages can affect entities nearby")
                .placeNearTarget()
                .pointAt(util.vector().topOf(birbPos).add(0,0.3f,0));

        scene.idle(80);

        scene.world().setKineticSpeed(kinetics1, 32);
        scene.world().setKineticSpeed(kinetics2, -32);

        scene.world().showSection(kinetics1, Direction.NORTH);
        scene.world().showSection(kinetics2, Direction.NORTH);
        scene.idle(10);

        scene.world().setKineticSpeed(pump1, -32);

        scene.world().modifyBlock(conduitPos, s -> s.setValue(MechanicalConduitBlock.CONDUIT_POWER_LEVEL,
                MechanicalConduitBlock.ConduitPowerLevel.AWAKENED), false);

        scene.effects().indicateSuccess(conduitPos);

        scene.idle(10);

        Vec3 itemVec = util.vector().topOf(birbPos).add(0, 0.3, 0);

        scene.overlay().showText(70)
                .text("Pumping water into a Conduit Cage will provide the Conduit Power Effect to nearby entities")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector().topOf(tankStart).add(0,0,0));

        scene.idle(80);

        scene.overlay().showControls(itemVec, Pointing.LEFT, 60).showing(CAAIcons.I_CONDUIT_POWER);

        scene.effects().emitParticles(itemVec, scene.effects().simpleParticleEmitter(ParticleTypes.ENTITY_EFFECT, VecHelper.offsetRandomly(
                Vec3.ZERO, random, 1/16f)), 0.2f, 60);

        scene.idle(80);

        for (int i = 0; i < 16; i++) {
            scene.world().modifyBlockEntity(tankStart, FluidTankBlockEntity.class, be -> be.getTankInventory()
                    .drain(1000, IFluidHandler.FluidAction.EXECUTE));
            scene.idle(1);
        }

        scene.addKeyframe();

        scene.idle(10);

        ItemStack stack = new ItemStack(Items.POTION);
        PotionUtils.setPotion(stack, Potions.FIRE_RESISTANCE);
        FluidStack fireResPotion = PotionFluidHandler.getFluidFromPotionItem(stack);
        fireResPotion.setAmount(1000);

        for (int i = 0; i < 16; i++) {
            scene.world().modifyBlockEntity(tankStart, FluidTankBlockEntity.class, be -> be.getTankInventory()
                    .fill(fireResPotion, IFluidHandler.FluidAction.EXECUTE));
            scene.idle(1);
        }

        scene.overlay().showText(70)
                .text("Different fluids will provide different effects")
                .placeNearTarget()
                .pointAt(util.vector().topOf(tankStart).add(0,0,0));

        scene.idle(80);

        scene.overlay().showControls(itemVec, Pointing.LEFT, 60).showing(CAAIcons.I_FIRE_RES);

        scene.effects().emitParticles(itemVec, scene.effects().simpleParticleEmitter(ParticleTypes.ENTITY_EFFECT, VecHelper.offsetRandomly(
                Vec3.ZERO, random, 1/16f)), 0.2f, 60);

        scene.idle(80);

        Vec3 filterPos = util.vector().blockSurface(conduitPos,Direction.WEST).add(0.05f,-0.3f,0);

        scene.overlay().showFilterSlotInput(filterPos, Direction.WEST, 70);
        scene.overlay().showText(100)
                .pointAt(filterPos)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The mode slot allows you to select which entities will be affected");

        scene.idle(80);

        scene.markAsFinished();
    }
}
