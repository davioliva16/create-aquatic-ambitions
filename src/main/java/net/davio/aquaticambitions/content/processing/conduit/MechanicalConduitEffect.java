package net.davio.aquaticambitions.content.processing.conduit;

import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitEffectDefinition.Behavior;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.material.Fluid;

/**
 * Per-conduit mutable state for a single effect. The immutable definition (lang key, effect, color, fluid tag,
 * behavior) now comes from the {@code conduit_effect} datapack registry
 * ({@link MechanicalConduitEffectDefinition}); this class just tracks the accumulated ticks and amplifier for one
 * block entity and delegates the read-only fields to its definition.
 */
public class MechanicalConduitEffect {

    private final MechanicalConduitEffectDefinition definition;
    private float ticks;
    private int amplifier;

    public MechanicalConduitEffect(MechanicalConduitEffectDefinition definition) {
        this.definition = definition;
        this.ticks = 0;
        this.amplifier = 0;
    }

    public int getTicks(){
        return Mth.floor(ticks);
    }

    public void setTicks(int newTicks) { //Used to update client side
        this.ticks = newTicks;
    }

    public boolean isActive(){
        return ticks > 0f;
    }

    public void addTicks(float amount){
        ticks += amount;
    }

    public void subtractTicks(){
        ticks --;
        if (ticks < 0f) ticks = 0f;
    }

    public int getAmplifier() {return amplifier;}

    public void setAmplifier(int newAmplifier) {
        if (newAmplifier > this.amplifier) {
            amplifier = newAmplifier;
        }
    }

    public void resetAmplifier() {
        amplifier = 0;
    }

    public String getLangKey(){
        return definition.langKey();
    }

    public int getColor(){
        return definition.color();
    }

    /** The mob effect to apply, or {@code null} for non-effect behaviors (clear/burn). */
    public Holder<MobEffect> getEffect(){
        return definition.mobEffect().orElse(null);
    }

    public TagKey<Fluid> getFluidTag(){
        return definition.fluidTag();
    }

    public Behavior getBehavior(){
        return definition.behavior();
    }
}
