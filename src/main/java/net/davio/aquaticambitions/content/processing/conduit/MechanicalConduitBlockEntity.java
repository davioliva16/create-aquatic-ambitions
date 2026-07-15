package net.davio.aquaticambitions.content.processing.conduit;

import com.simibubi.create.AllFluids;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.AngleHelper;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock.ConduitPowerLevel;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitEffectDefinition.Behavior;
import net.davio.aquaticambitions.infrastructure.config.CAAConfig;
import net.davio.aquaticambitions.registry.CAABlockEntityTypes;
import net.davio.aquaticambitions.registry.CAAIcons;
import net.davio.aquaticambitions.registry.CAARegistries;
import net.davio.aquaticambitions.registry.CAATags;
import net.davio.aquaticambitions.util.CAALang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
import org.jetbrains.annotations.Contract;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MechanicalConduitBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    private SmartFluidTankBehaviour tank;

    private int awakenedTicks = 0;

    /** Max fluid (mB) accepted per insertion. Config-driven so large fluid packages/transfers can be accepted. */
    private static int tankCapacity() {
        return CAAConfig.server().conduitCage.conduitFluidCapacity.get();
    }

    /** Max awakening the conduit can accumulate, in ticks. Config is in seconds; 20 ticks/second. */
    private static int awakenedTicksLimit() {
        return CAAConfig.server().conduitCage.awakenedTimeLimit.get() * 20;
    }

    // Built lazily from the conduit_effect datapack registry once a level (and its registry access) is available.
    private Map<ResourceLocation, MechanicalConduitEffect> conduitEffectsMap = null;

    protected LerpedFloat eyeAnimation;
    protected LerpedFloat eyeAngle;
    protected LerpedFloat cageAngle;

    protected ScrollOptionBehaviour<EntitySelectionMode> entityTypeSelector;

    public MechanicalConduitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        eyeAnimation = LerpedFloat.linear();
        eyeAngle = LerpedFloat.angular();
        cageAngle = LerpedFloat.angular();

        eyeAngle.startWithValue((AngleHelper.horizontalAngle(Direction.NORTH) + 180) % 360);
        cageAngle.startWithValue((AngleHelper.horizontalAngle(Direction.NORTH) + 180) % 360);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

        // Consumption runs from tick(), not whenFluidUpdates: draining mid-fill() makes fill() return 0, so
        // external fillers think nothing was accepted and duplicate the fluid (issue #59).
        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, tankCapacity(),true)
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

    /**
     * Builds the per-conduit effect state map from the {@code conduit_effect} datapack registry the first time it's
     * needed. Disabled definitions ({@code enabled: false}) are skipped entirely, so all downstream loops ignore
     * them. Returns an empty map if no registry access is available yet.
     */
    private Map<ResourceLocation, MechanicalConduitEffect> getConduitEffects() {
        if (conduitEffectsMap == null && level != null) {
            ensureEffects(level.registryAccess());
        }
        return conduitEffectsMap != null ? conduitEffectsMap : Map.of();
    }

    private void ensureEffects(HolderLookup.Provider registries) {
        if (conduitEffectsMap != null) return;
        Map<ResourceLocation, MechanicalConduitEffect> map = new LinkedHashMap<>();
        registries.lookupOrThrow(CAARegistries.CONDUIT_EFFECT)
                .listElements()
                .filter(ref -> ref.value().enabled())
                .forEach(ref -> map.put(ref.key().location(), new MechanicalConduitEffect(ref.value())));
        conduitEffectsMap = map;
    }

    private void consumeFluid() {

        FluidStack fluidStack = tank.getPrimaryHandler().getFluid();
        if (fluidStack.isEmpty()) // called every server tick
            return;

        int limit = awakenedTicksLimit();
        for(MechanicalConduitEffect conduitEffect : getConduitEffects().values()) {
            if (fluidStack.is(conduitEffect.getFluidTag()) || potionHasEffect(fluidStack, conduitEffect)) // or NBT matches create:potion
                {
                conduitEffect.addTicks(getConversionRate(fluidStack)*fluidStack.getAmount());
                if (conduitEffect.getTicks() > limit) // clamp: one large insertion (e.g. a big fluid package) can't exceed the configured cap
                    conduitEffect.setTicks(limit);
            }
            if (conduitEffect.getTicks() >= limit) {
                tank.forbidInsertion();
            }
        }

        tank.getPrimaryHandler().drain(tankCapacity(), IFluidHandler.FluidAction.EXECUTE);

        notifyUpdate();
        updateBlockState();
        sendData();
    }

    private boolean potionHasEffect(FluidStack fluidStack, MechanicalConduitEffect conduitEffect) {
        if (conduitEffect.getEffect() == null) { // clear/burn behaviors have no mob effect to match against
            return false;
        }
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
        if (this.level == null) return;

        if (level.isClientSide) {
            if (shouldTickAnimation()) {
                tickAnimation();
            }
            if (!isVirtual()) {
                spawnParticles(getConduitLevelFromBlock());
            }
            return;
        }

        consumeFluid();

        int maxAwakanedTicks = 0;

        for(MechanicalConduitEffect conduitEffect : getConduitEffects().values()) {
            if (conduitEffect.isActive()) {
                conduitEffect.subtractTicks();

                //Handle effects, dispatching on the definition's behavior
                switch (conduitEffect.getBehavior()) {
                    case CLEAR_EFFECTS -> this.clearEffects();
                    case SET_ON_FIRE -> this.setOnFire();
                    case APPLY -> {
                        if (conduitEffect.getEffect() != null) {
                            this.applyEffects(conduitEffect.getEffect(), conduitEffect.getAmplifier());
                        }
                    }
                }

                if (conduitEffect.getTicks() < awakenedTicksLimit()) {
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

        ensureEffects(registries);
        CompoundTag effectsTag = new CompoundTag();
        for (Map.Entry<ResourceLocation, MechanicalConduitEffect> entry : conduitEffectsMap.entrySet()) {
            effectsTag.putInt(entry.getKey().toString(), entry.getValue().getTicks());
        }
        compound.put("ConduitEffects", effectsTag);

        super.write(compound, registries, clientPacket);
    }

    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        awakenedTicks = compound.getInt("awakenedTimeRemaining");
        super.read(compound, registries, clientPacket);

        ensureEffects(registries);
        CompoundTag effectsTag = compound.getCompound("ConduitEffects");
        for (String key : effectsTag.getAllKeys()) {
            // Keys are now effect ResourceLocations; pre-2.0.3 saves used enum names, which won't parse/match here
            // (in-progress conduit timers reset once on update — this is intentional, no migration).
            ResourceLocation id = ResourceLocation.tryParse(key);
            MechanicalConduitEffect effect = id == null ? null : conduitEffectsMap.get(id);
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
        if (this.level == null) return;
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

    // Night vision (and similar) visually oscillate while their remaining duration is <= 200 ticks, so effects must
    // be kept comfortably above that: apply for longer than 200 ticks and refresh before dropping back under it,
    // otherwise short-duration reapplication leaves entities permanently in the "flashing" window (issue #55).
    private static final int APPLIED_EFFECT_DURATION = 260;
    private static final int EFFECT_REFRESH_THRESHOLD = 210;

    private void applyEffects(Holder<MobEffect> effect, int amplifier  ) {
        int range = CAAConfig.server().conduitCage.conduitCageRange.get();
        List<LivingEntity> list = getLivingEntities(range);

        if (!list.isEmpty()) {
            for(LivingEntity entity : list) {
                if (entityMatchesSelector(entity, entityTypeSelector.get())){
                    if (this.getBlockPos().closerThan(entity.blockPosition(), (double)range)) {
                        MobEffectInstance existing = entity.getEffect(effect);
                        if (existing == null || existing.getDuration() < EFFECT_REFRESH_THRESHOLD) {
                            entity.addEffect(new MobEffectInstance(effect, APPLIED_EFFECT_DURATION, amplifier, true, true));
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
        if (this.level == null) return List.of();
        int k = this.getBlockPos().getX();
        int l = this.getBlockPos().getY();
        int i1 = this.getBlockPos().getZ();
        AABB aabb = (new AABB(k, l, i1, k + 1, l + 1, i1 + 1)).inflate(range).expandTowards(0.0F, this.level.getHeight(), 0.0F);
        return this.level.getEntitiesOfClass(LivingEntity.class, aabb);
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


    @Contract(pure = true)
    public static boolean entityMatchesSelector(LivingEntity entity, EntitySelectionMode mode) {
        return switch (mode) {
            case EVERYONE -> true;
            case PLAYERS -> entity instanceof Player;
            case MONSTERS -> entity instanceof Enemy;
            case FRIENDLY_MOBS -> entity instanceof PathfinderMob && !(entity instanceof Monster);
            case PLAYERS_FRIENDLY_MOBS -> entity instanceof PathfinderMob && !(entity instanceof Monster) || (entity instanceof Player);
        };
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

        for(MechanicalConduitEffect conduitEffect : getConduitEffects().values()) {

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
        // Within ~0.5s of the configured cap the conduit is effectively "always on" while fed, so show ∞ instead
        // of a jittery near-max countdown. Relative to the config so a lowered awakenedTimeLimit still reads right.
        if (tickAmount >= awakenedTicksLimit() - 10) return "∞";
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
