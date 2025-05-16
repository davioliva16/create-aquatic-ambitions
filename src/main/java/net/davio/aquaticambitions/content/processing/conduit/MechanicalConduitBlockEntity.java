package net.davio.aquaticambitions.content.processing.conduit;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.math.AngleHelper;
import net.davio.aquaticambitions.util.CCALang;
import net.davio.aquaticambitions.registry.CCABlockEntityTypes;
import net.davio.aquaticambitions.registry.CCATags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock.ConduitPowerLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MechanicalConduitBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    private static final int TANK_CAPACITY = 1000;

    private SmartFluidTankBehaviour tank;

    private int awakenedTicks = 0;

    private final int awakenedTicksLimit = 144000;

    Map<String, MechanicalConduitEffect> conduitEffectsMap = new HashMap<>();

    protected LerpedFloat eyeAnimation;
    protected LerpedFloat eyeAngle;

    public MechanicalConduitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        //Init all possible effects - I don't know if this is best place for it -
        // TODO fix hex values

        conduitEffectsMap.put("CONDUIT_POWER", new MechanicalConduitEffect(
                "effect.minecraft.conduit_power", MobEffects.CONDUIT_POWER,0x1DC2D1,CCATags.CCAFluidTags.CONDUIT_FUEL.tag));
        conduitEffectsMap.put("FIRE_RESISTANCE", new MechanicalConduitEffect(
                "effect.minecraft.fire_resistance",MobEffects.FIRE_RESISTANCE,0xE49A3A, CCATags.CCAFluidTags.GIVES_FIRE_RES.tag));
        conduitEffectsMap.put("HASTE", new MechanicalConduitEffect(
                "effect.minecraft.haste", MobEffects.DIG_SPEED,0xD9C043, CCATags.CCAFluidTags.GIVES_HASTE.tag));
        conduitEffectsMap.put("INFESTED", new MechanicalConduitEffect(
                "effect.minecraft.infested", MobEffects.INFESTED,0x8C9B8C, CCATags.CCAFluidTags.GIVES_INFESTED.tag));
        conduitEffectsMap.put("INVISIBILITY", new MechanicalConduitEffect(
                "effect.minecraft.invisibility", MobEffects.INVISIBILITY,0x7F8392, CCATags.CCAFluidTags.GIVES_INVIS.tag));
        conduitEffectsMap.put("JUMP_BOOST", new MechanicalConduitEffect(
                "effect.minecraft.jump_boost", MobEffects.JUMP,0x23FC4D, CCATags.CCAFluidTags.GIVES_JUMP.tag));
        conduitEffectsMap.put("LUCK", new MechanicalConduitEffect(
                "effect.minecraft.luck", MobEffects.LUCK, 0x339900, CCATags.CCAFluidTags.GIVES_LUCK.tag));
        conduitEffectsMap.put("NIGHT_VISION", new MechanicalConduitEffect(
                "effect.minecraft.night_vision", MobEffects.NIGHT_VISION,0x1F1FA1, CCATags.CCAFluidTags.GIVES_NIGHT_VISION.tag));
        conduitEffectsMap.put("OOZING", new MechanicalConduitEffect(
                "effect.minecraft.oozing", MobEffects.OOZING,0x99FFA3 , CCATags.CCAFluidTags.GIVES_OOZING.tag));
        conduitEffectsMap.put("POISON", new MechanicalConduitEffect(
                "effect.minecraft.poison", MobEffects.POISON,0x4E9331, CCATags.CCAFluidTags.GIVES_POISON.tag));
        conduitEffectsMap.put("REGENERATION", new MechanicalConduitEffect(
                "effect.minecraft.regeneration", MobEffects.REGENERATION,0xCD5CAB, CCATags.CCAFluidTags.GIVES_REGEN.tag));
        conduitEffectsMap.put("RESISTANCE", new MechanicalConduitEffect(
                "effect.minecraft.resistance", MobEffects.DAMAGE_RESISTANCE,0x8F45ED, CCATags.CCAFluidTags.GIVES_RESISTANCE.tag));
        conduitEffectsMap.put("SLOW_FALLING", new MechanicalConduitEffect(
                "effect.minecraft.slow_falling", MobEffects.SLOW_FALLING,0xFFEFD1 , CCATags.CCAFluidTags.GIVES_SLOW_FALL.tag));
        conduitEffectsMap.put("SLOWNESS", new MechanicalConduitEffect(
                "effect.minecraft.slowness", MobEffects.MOVEMENT_SLOWDOWN,0x5A6C81, CCATags.CCAFluidTags.GIVES_SLOWNESS.tag));
        conduitEffectsMap.put("SPEED", new MechanicalConduitEffect(
                "effect.minecraft.speed", MobEffects.MOVEMENT_SPEED,0x7CAFC6, CCATags.CCAFluidTags.GIVES_SPEED.tag));
        conduitEffectsMap.put("STRENGTH", new MechanicalConduitEffect(
                "effect.minecraft.strength", MobEffects.DAMAGE_BOOST,0xFCC500, CCATags.CCAFluidTags.GIVES_STRENGTH.tag));
        conduitEffectsMap.put("WATER_BREATHING", new MechanicalConduitEffect(
                "effect.minecraft.water_breathing", MobEffects.WATER_BREATHING,0x96D7BE, CCATags.CCAFluidTags.GIVES_WATER_BREATHING.tag)); //make this an achievement "Redundancies Expert"
        conduitEffectsMap.put("WEAKNESS", new MechanicalConduitEffect(
                "effect.minecraft.weakness", MobEffects.WEAKNESS,0x484D48, CCATags.CCAFluidTags.GIVES_WEAKNESS.tag));
        conduitEffectsMap.put("WEAVING", new MechanicalConduitEffect(
                "effect.minecraft.weaving", MobEffects.WEAVING,0x78695A, CCATags.CCAFluidTags.GIVES_WEAVING.tag));
        conduitEffectsMap.put("WIND_CHARGED", new MechanicalConduitEffect(
                "effect.minecraft.wind_charged", MobEffects.WIND_CHARGED,0xBDC9FF, CCATags.CCAFluidTags.GIVES_WIND.tag));
        conduitEffectsMap.put("WITHER", new MechanicalConduitEffect(
                "effect.minecraft.wither", MobEffects.WITHER,0x352A27, CCATags.CCAFluidTags.GIVES_WITHER.tag));

        eyeAnimation = LerpedFloat.linear();
        eyeAngle = LerpedFloat.angular();

        eyeAngle.startWithValue((AngleHelper.horizontalAngle(Direction.NORTH) + 180) % 360);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, TANK_CAPACITY,true)
                .whenFluidUpdates(this::consumeFluid)
                .forbidExtraction();
        behaviours.add(tank);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CCABlockEntityTypes.MECHANICAL_CONDUIT.get(),
                (be, context) -> {
                    if (context == null || context == Direction.DOWN)
                        return be.tank.getCapability();
                    return null;
                }
        );
    }

    private void consumeFluid() {

        FluidStack fluidStack = tank.getPrimaryHandler().getFluid();

        for(MechanicalConduitEffect conduitEffect : conduitEffectsMap.values()) {
            if (fluidStack.is(conduitEffect.getFluidTag())) {
                conduitEffect.addTicks(fluidStack.getAmount());
            }

            if (conduitEffect.getTicks() > awakenedTicksLimit) {
                tank.forbidInsertion();
            }
        }

        tank.getPrimaryHandler().drain(TANK_CAPACITY, IFluidHandler.FluidAction.EXECUTE);

        notifyUpdate();
        updateBlockState();
        sendData();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            if (shouldTickAnimation()) {
                tickAnimation();
            }
            if (!isVirtual()) {
                spawnParticles(getConduitLevelFromBlock());
            }
            return;
        }

        int maxAwakanedTicks = 0;

        for(MechanicalConduitEffect conduitEffect : conduitEffectsMap.values()) {
            if (conduitEffect.isActive()) {
                conduitEffect.subtractTicks();
                applyEffects(level, getBlockPos(), conduitEffect.getEffect());

                if (conduitEffect.getTicks() < awakenedTicksLimit) {
                    tank.allowInsertion();
                }

                if (conduitEffect.getTicks() > maxAwakanedTicks) {
                    maxAwakanedTicks = conduitEffect.getTicks();
                }

            }

            awakenedTicks = maxAwakanedTicks;
        }
        updateBlockState();
        sendData();
    }

    public void lazyTick() {
        super.lazyTick();
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putInt("awakenedTimeRemaining", awakenedTicks);

        CompoundTag effectsTag = new CompoundTag();
        for (Map.Entry<String, MechanicalConduitEffect> entry : conduitEffectsMap.entrySet()) {
            effectsTag.putInt(entry.getKey(), entry.getValue().getTicks());
        }
        compound.put("ConduitEffects", effectsTag);

        super.write(compound, registries, clientPacket);
    }

    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        awakenedTicks = compound.getInt("awakenedTimeRemaining");
        super.read(compound, registries, clientPacket);

        CompoundTag effectsTag = compound.getCompound("ConduitEffects");
        for (String key : effectsTag.getAllKeys()) {
            MechanicalConduitEffect effect = conduitEffectsMap.get(key);
            if (effect != null) {
                effect.setTicks(effectsTag.getInt(key));
            }
        }
    }

    public ConduitPowerLevel getConduitLevelFromBlock() {
        return MechanicalConduitBlock.getConduitLevelOf(getBlockState());
    };

    public void updateBlockState(){
        setConduitLevelOfBlock(getConduitLevel());
    }

    public void setConduitLevelOfBlock(ConduitPowerLevel conduitPowerLevel) {
        ConduitPowerLevel inBlockState = getConduitLevelFromBlock();
        if (inBlockState == conduitPowerLevel)
            return;
        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(MechanicalConduitBlock.CONDUIT_POWER_LEVEL, conduitPowerLevel));
        notifyUpdate();
    }

    protected ConduitPowerLevel getConduitLevel() {
        if (awakenedTicks > 0) {
            return ConduitPowerLevel.AWAKENED;
        } else {
            return ConduitPowerLevel.IDLE;
        }
    }
    @OnlyIn(Dist.CLIENT)
    private boolean shouldTickAnimation() {
        return !VisualizationManager.supportsVisualization(level);
    }

    @OnlyIn(Dist.CLIENT)
    public void tickAnimation() { //This is just for player tracking, everything else is done in visual class
        float target = 0;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && !player.isInvisible()) {
            double x;
            double z;
            if (isVirtual()) {
                x = -4;
                z = -10;
            } else {
                x = player.getX();
                z = player.getZ();
            };
            double dx = x - (getBlockPos().getX() + 0.5);
            double dz = z - (getBlockPos().getZ() + 0.5);
            target = AngleHelper.deg(-Mth.atan2(dz, dx)) - 90;
        };
        target = eyeAngle.getValue() + AngleHelper.getShortestAngleDiff(eyeAngle.getValue(), target);
        eyeAngle.chase(target, .25f, LerpedFloat.Chaser.exp(5));
        eyeAngle.tickChaser();

        eyeAnimation.chase(0, 0.25f, LerpedFloat.Chaser.exp(0.25f));
        eyeAnimation.tickChaser();
    };

    protected void spawnParticles(ConduitPowerLevel conduitPowerLevel) {
        if (level == null)
            return;
        if (conduitPowerLevel == ConduitPowerLevel.IDLE)
            return;

        RandomSource r = level.getRandom();

        if (conduitPowerLevel.isAwakened() && r.nextInt(5) == 0) {
            Vec3 vec31 = new Vec3(getBlockPos().getX()+0.5f, getBlockPos().getY()+1.25f, getBlockPos().getZ()+0.5f);
            float f3 = (-0.5F + r.nextFloat())*2f;
            float f4 = (-0.75f + r.nextFloat());
            float f5 = (-0.5F + r.nextFloat())*2f;
            Vec3 vec32 = new Vec3((double)f3, (double)f4, (double)f5);
            level.addParticle(ParticleTypes.NAUTILUS, vec31.x, vec31.y, vec31.z, vec32.x, vec32.y, vec32.z);
        }
    }

    private static void applyEffects(Level level, BlockPos pos, Holder<MobEffect> effect) {
        int range = 32;
        int k = pos.getX();
        int l = pos.getY();
        int i1 = pos.getZ();
        AABB aabb = (new AABB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))).inflate((double)range).expandTowards((double)0.0F, (double)level.getHeight(), (double)0.0F);
        List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
        if (!list.isEmpty()) {
            for(Player player : list) {
                if (pos.closerThan(player.blockPosition(), (double)range)) {
                    player.addEffect(new MobEffectInstance(effect, 619, 0, true, true));
                }
            }
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        if (!getConduitLevel().isAwakened()) {
            return false;
        }

        CCALang.translate("tooltip.conduitcage.header").forGoggles(tooltip);

        LangBuilder sec = CreateLang.translate("generic.unit.seconds");

        for(MechanicalConduitEffect conduitEffect : conduitEffectsMap.values()) {

            Component effectName = Component.translatable(conduitEffect.getLangKey())
                    .withStyle(Style.EMPTY.withColor(conduitEffect.getColor()));

            if (conduitEffect.isActive()){
                CCALang.text("")
                    .add(effectName)
                    .add(CCALang.text(" "))
                    .add(CCALang.number(Mth.floor(conduitEffect.getTicks()/20f))
                    .add(CCALang.text(" "))
                    .add(sec)
                    .style(ChatFormatting.WHITE))
                    .forGoggles(tooltip, 1);
            }
        }
        return true;
    }
}
