package net.davio.aquaticambitions.content.processing.conduit;

import com.simibubi.create.AllFluids;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.math.AngleHelper;
import net.davio.aquaticambitions.infrastructure.config.CAAConfig;
import net.davio.aquaticambitions.infrastructure.config.CAAServerConfig;
import net.davio.aquaticambitions.util.CAALang;
import net.davio.aquaticambitions.registry.CAABlockEntityTypes;
import net.davio.aquaticambitions.registry.CAATags;
import net.davio.aquaticambitions.registry.CAAIcons;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionContents;
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
    protected LerpedFloat cageAngle;

    protected ScrollOptionBehaviour<EntitySelectionMode> entityTypeSelector;

    public MechanicalConduitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        //Init all possible effects - I don't know if this is best place for it -
        conduitEffectsMap.put("CONDUIT_POWER", new MechanicalConduitEffect(
                "effect.minecraft.conduit_power", MobEffects.CONDUIT_POWER,0x1DC2D1, CAATags.CAAFluidTags.CONDUIT_FUEL.tag));

        //MILK
        conduitEffectsMap.put("CLEAR", new MechanicalConduitEffect(
                "tooltip.create_aquatic_ambitions.effect.cleansing", null,0xFFFFFF, CAATags.CAAFluidTags.CLEARS_EFFECTS.tag));
        //LAVA
        conduitEffectsMap.put("BURNING", new MechanicalConduitEffect(
                "tooltip.create_aquatic_ambitions.effect.burning", null,0xE2AA22, CAATags.CAAFluidTags.SETS_ON_FIRE.tag));

        //Potions and other effects
        conduitEffectsMap.put("SATURATION", new MechanicalConduitEffect(
                "effect.minecraft.saturation", MobEffects.SATURATION,0xF82421, CAATags.CAAFluidTags.GIVES_SATURATION.tag));
        conduitEffectsMap.put("FIRE_RESISTANCE", new MechanicalConduitEffect(
                "effect.minecraft.fire_resistance",MobEffects.FIRE_RESISTANCE,0xE49A3A, CAATags.CAAFluidTags.GIVES_FIRE_RES.tag));
        conduitEffectsMap.put("HASTE", new MechanicalConduitEffect(
                "effect.minecraft.haste", MobEffects.DIG_SPEED,0xD9C043, CAATags.CAAFluidTags.GIVES_HASTE.tag));
        conduitEffectsMap.put("INFESTED", new MechanicalConduitEffect(
                "effect.minecraft.infested", MobEffects.INFESTED,0x8C9B8C, CAATags.CAAFluidTags.GIVES_INFESTED.tag));
        conduitEffectsMap.put("INVISIBILITY", new MechanicalConduitEffect(
                "effect.minecraft.invisibility", MobEffects.INVISIBILITY,0x7F8392, CAATags.CAAFluidTags.GIVES_INVIS.tag));
        conduitEffectsMap.put("JUMP_BOOST", new MechanicalConduitEffect(
                "effect.minecraft.jump_boost", MobEffects.JUMP,0x23FC4D, CAATags.CAAFluidTags.GIVES_JUMP.tag));
        conduitEffectsMap.put("LUCK", new MechanicalConduitEffect(
                "effect.minecraft.luck", MobEffects.LUCK, 0x339900, CAATags.CAAFluidTags.GIVES_LUCK.tag));
        conduitEffectsMap.put("NIGHT_VISION", new MechanicalConduitEffect(
                "effect.minecraft.night_vision", MobEffects.NIGHT_VISION,0x1F1FA1, CAATags.CAAFluidTags.GIVES_NIGHT_VISION.tag));
        conduitEffectsMap.put("OOZING", new MechanicalConduitEffect(
                "effect.minecraft.oozing", MobEffects.OOZING,0x99FFA3 , CAATags.CAAFluidTags.GIVES_OOZING.tag));
        conduitEffectsMap.put("POISON", new MechanicalConduitEffect(
                "effect.minecraft.poison", MobEffects.POISON,0x4E9331, CAATags.CAAFluidTags.GIVES_POISON.tag));
        conduitEffectsMap.put("REGENERATION", new MechanicalConduitEffect(
                "effect.minecraft.regeneration", MobEffects.REGENERATION,0xCD5CAB, CAATags.CAAFluidTags.GIVES_REGEN.tag));
        conduitEffectsMap.put("RESISTANCE", new MechanicalConduitEffect(
                "effect.minecraft.resistance", MobEffects.DAMAGE_RESISTANCE,0x8F45ED, CAATags.CAAFluidTags.GIVES_RESISTANCE.tag));
        conduitEffectsMap.put("SLOW_FALLING", new MechanicalConduitEffect(
                "effect.minecraft.slow_falling", MobEffects.SLOW_FALLING,0xFFEFD1 , CAATags.CAAFluidTags.GIVES_SLOW_FALL.tag));
        conduitEffectsMap.put("SLOWNESS", new MechanicalConduitEffect(
                "effect.minecraft.slowness", MobEffects.MOVEMENT_SLOWDOWN,0x5A6C81, CAATags.CAAFluidTags.GIVES_SLOWNESS.tag));
        conduitEffectsMap.put("SPEED", new MechanicalConduitEffect(
                "effect.minecraft.speed", MobEffects.MOVEMENT_SPEED,0x7CAFC6, CAATags.CAAFluidTags.GIVES_SPEED.tag));
        conduitEffectsMap.put("STRENGTH", new MechanicalConduitEffect(
                "effect.minecraft.strength", MobEffects.DAMAGE_BOOST,0xFCC500, CAATags.CAAFluidTags.GIVES_STRENGTH.tag));
        conduitEffectsMap.put("WATER_BREATHING", new MechanicalConduitEffect(
                "effect.minecraft.water_breathing", MobEffects.WATER_BREATHING,0x96D7BE, CAATags.CAAFluidTags.GIVES_WATER_BREATHING.tag)); //make this an achievement "Redundancies Expert"
        conduitEffectsMap.put("WEAKNESS", new MechanicalConduitEffect(
                "effect.minecraft.weakness", MobEffects.WEAKNESS,0x484D48, CAATags.CAAFluidTags.GIVES_WEAKNESS.tag));
        conduitEffectsMap.put("WEAVING", new MechanicalConduitEffect(
                "effect.minecraft.weaving", MobEffects.WEAVING,0x78695A, CAATags.CAAFluidTags.GIVES_WEAVING.tag));
        conduitEffectsMap.put("WIND_CHARGED", new MechanicalConduitEffect(
                "effect.minecraft.wind_charged", MobEffects.WIND_CHARGED,0xBDC9FF, CAATags.CAAFluidTags.GIVES_WIND.tag));
        conduitEffectsMap.put("WITHER", new MechanicalConduitEffect(
                "effect.minecraft.wither", MobEffects.WITHER,0x352A27, CAATags.CAAFluidTags.GIVES_WITHER.tag));

        eyeAnimation = LerpedFloat.linear();
        eyeAngle = LerpedFloat.angular();
        cageAngle = LerpedFloat.angular();

        eyeAngle.startWithValue((AngleHelper.horizontalAngle(Direction.NORTH) + 180) % 360);
        cageAngle.startWithValue((AngleHelper.horizontalAngle(Direction.NORTH) + 180) % 360);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, TANK_CAPACITY,true)
                .whenFluidUpdates(this::consumeFluid)
                .forbidExtraction();
        behaviours.add(tank);

        behaviours.add(entityTypeSelector = new ScrollOptionBehaviour<>(EntitySelectionMode.class,
                CAALang.translateDirect("mechanical_conduit.entity_filter.title"), this,
                new MechanicalConduitModeSlot()));

    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CAABlockEntityTypes.MECHANICAL_CONDUIT.get(),
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
            if (fluidStack.is(conduitEffect.getFluidTag()) || potionHasEffect(fluidStack, conduitEffect)) // or NBT matches create:potion
                {
                conduitEffect.addTicks(getConversionRate(fluidStack)*fluidStack.getAmount());
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

    private boolean potionHasEffect(FluidStack fluidStack, MechanicalConduitEffect conduitEffect) {
        PotionContents potionContents = fluidStack.get(DataComponents.POTION_CONTENTS);
        if (potionContents == null) {
            return false;
        }
        for(MobEffectInstance effect :potionContents.getAllEffects()) {
            if (effect.is(conduitEffect.getEffect())) {
                conduitEffect.setAmplifier(effect.getAmplifier());
                return true;
            }
        }
        return false;
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

                //Handle effects
                if (conduitEffect.getFluidTag() == CAATags.CAAFluidTags.CLEARS_EFFECTS.tag) {
                    this.clearEffects();
                } else if (conduitEffect.getFluidTag() == CAATags.CAAFluidTags.SETS_ON_FIRE.tag) {
                    this.setOnFire();
                }
                else {
                    this.applyEffects(conduitEffect.getEffect(), conduitEffect.getAmplifier());
                }

                if (conduitEffect.getTicks() < awakenedTicksLimit) {
                    tank.allowInsertion();
                }

                if (conduitEffect.getTicks() > maxAwakanedTicks) {
                    maxAwakanedTicks = conduitEffect.getTicks();
                }

            } else {
                if (conduitEffect.getAmplifier() > 0) {
                    conduitEffect.resetAmplifier();
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
        float eyeTarget = 0;
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
            eyeTarget = AngleHelper.deg(-Mth.atan2(dz, dx)) - 90;
        };
        eyeTarget = eyeAngle.getValue() + AngleHelper.getShortestAngleDiff(eyeAngle.getValue(), eyeTarget);
        eyeAngle.chase(eyeTarget, .25f, LerpedFloat.Chaser.exp(5));
        eyeAngle.tickChaser();

        eyeAnimation.chase(0, 0.25f, LerpedFloat.Chaser.exp(0.25f));
        eyeAnimation.tickChaser();
    }

    protected void spawnParticles(ConduitPowerLevel conduitPowerLevel) {
        if (level == null)
            return;
        if (conduitPowerLevel == ConduitPowerLevel.IDLE)
            return;

        RandomSource r = level.getRandom();

        if (conduitPowerLevel.isAwakened() && r.nextInt(5) == 0) {
            Vec3 vec31 = new Vec3(getBlockPos().getX()+0.5f, getBlockPos().getY()+2f, getBlockPos().getZ()+0.5f);
            float f3 = (-0.5F + r.nextFloat())*2f;
            float f4 = (-1.25f + r.nextFloat());
            float f5 = (-0.5F + r.nextFloat())*2f;
            Vec3 vec32 = new Vec3((double)f3, (double)f4, (double)f5);
            level.addParticle(ParticleTypes.NAUTILUS, vec31.x, vec31.y, vec31.z, vec32.x, vec32.y, vec32.z);
        }
    }

    private void applyEffects(Holder<MobEffect> effect, int amplifier  ) {
        int range = CAAConfig.server().conduitCage.conduitCageRange.get();
        List<LivingEntity> list = getLivingEntities(range);

        if (!list.isEmpty()) {
            for(LivingEntity entity : list) {
                if (entityMatchesSelector(entity, entityTypeSelector.get())){
                    if (this.getBlockPos().closerThan(entity.blockPosition(), (double)range)) {
                        MobEffectInstance existing = entity.getEffect(effect);
                        if (existing == null || existing.getDuration() < 25) {
                            entity.addEffect(new MobEffectInstance(effect, 119, amplifier, true, true));
                        }
                    }
                }
            }
        }
    }

    private void clearEffects() {
        int range = CAAConfig.server().conduitCage.conduitCageRange.get();

        List<LivingEntity> list = getLivingEntities(range);
        if (!list.isEmpty()) {
            for(LivingEntity entity : list) {
                if (entityMatchesSelector(entity, entityTypeSelector.get())){
                    if (this.getBlockPos().closerThan(entity.blockPosition(), (double)range)) {
                        entity.removeAllEffects();
                    }
                }
            }
        }
    }

    private void setOnFire() {
        int range = CAAConfig.server().conduitCage.conduitCageRange.get();

        List<LivingEntity> list = getLivingEntities(range);
        if (!list.isEmpty()) {
            for(LivingEntity entity : list) {
                if (entityMatchesSelector(entity, entityTypeSelector.get())){
                    if (this.getBlockPos().closerThan(entity.blockPosition(), (double)range)) {
                        entity.setRemainingFireTicks(5*20);
                    }
                }
            }
        }
    }

    public List<LivingEntity> getLivingEntities(int range) {
        int k = this.getBlockPos().getX();
        int l = this.getBlockPos().getY();
        int i1 = this.getBlockPos().getZ();
        AABB aabb = (new AABB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))).inflate((double)range).expandTowards((double)0.0F, (double)this.level.getHeight(), (double)0.0F);
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
        return list;
    }

    public float getConversionRate(FluidStack fluidStack) {

        float conversionRate;

        if (CAATags.CAAFluidTags.CONDUIT_FUEL.matches(fluidStack.getFluid())) {
            conversionRate  = CAAConfig.server().conduitCage.waterAwakenConversionRate.get()*20/1000f;
        } else if (fluidStack.getFluid().isSame(AllFluids.POTION.get())) {
            conversionRate = CAAConfig.server().conduitCage.potionAwakenConversionRate.get()*20/1000f;
        } else {
            conversionRate = CAAConfig.server().conduitCage.fluidAwakenConversionRate.get()*20/1000f;
        }

        return conversionRate;
    }


    public static boolean entityMatchesSelector(LivingEntity entity, EntitySelectionMode mode) {
        if (mode == EntitySelectionMode.PLAYERS) return (entity instanceof Player);
        else if (mode == EntitySelectionMode.MONSTERS) return (entity instanceof Enemy);
        else if (mode == EntitySelectionMode.FRIENDLY_MOBS) return (entity instanceof PathfinderMob && !(entity instanceof Monster));
        else if (mode == EntitySelectionMode.PLAYERS_FRIENDLY_MOBS) return (
                (entity instanceof PathfinderMob && !(entity instanceof Monster) || (entity instanceof Player)));
        return true;
    }

    public enum EntitySelectionMode implements INamedIconOptions {
        EVERYONE(CAAIcons.I_EVERYONE),
        PLAYERS(CAAIcons.I_PLAYERS),
        FRIENDLY_MOBS(CAAIcons.I_FRIENDLY_MOBS),
        PLAYERS_FRIENDLY_MOBS(CAAIcons.I_PLAYERS_FRIENDLY_MOBS),
        MONSTERS(CAAIcons.I_MONSTERS);

        private final String translationKey;
        private final CAAIcons icon;

        EntitySelectionMode(CAAIcons icon) {
            this.icon = icon;
            this.translationKey = "mechanical_conduit.selection_mode." + Lang.asId(name()); // Will end up at the create namespace
        }

        @Override
        public CAAIcons getIcon() {
            return icon;
        }

        @Override
        public String getTranslationKey() {
            return translationKey;
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        if (!getConduitLevel().isAwakened()) {
            return false;
        }

        CAALang.translate("tooltip.conduitcage.header").forGoggles(tooltip);

        for(MechanicalConduitEffect conduitEffect : conduitEffectsMap.values()) {

            Component effectName = Component.translatable(conduitEffect.getLangKey())
                    .withStyle(Style.EMPTY.withColor(conduitEffect.getColor()));

            if (conduitEffect.isActive()){
                CAALang.text("")
                    .add(effectName)
                    .add(CAALang.text(" "))
                    .add(CAALang.text(tickToDuration(conduitEffect.getTicks()))
                    .style(ChatFormatting.WHITE))
                    .forGoggles(tooltip, 1);
            }
        }
        return true;
    }

    public String tickToDuration(int tickAmount) {
        if (tickAmount>143990) return "∞";
        int totalSeconds = tickAmount / 20;
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds / 60)%60;
        int seconds = totalSeconds % 60;
        if (hours > 0) {
            return String.format("%02d:%02d:%02d",hours, minutes, seconds);}
        else{
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}
