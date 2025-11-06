package net.davio.aquaticambitions.content.processing.conduit;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.material.Fluid;

public class MechanicalConduitEffect {

    private float ticks;
    private int amplifier;
    private final String langKey;
    private final Holder<MobEffect> effect;
    private final int hexColor;
    private final TagKey<Fluid> fluidTag;


    public MechanicalConduitEffect(String langKey, Holder<MobEffect> effect, int hexColor, TagKey<Fluid> fluidTag) {
        this.ticks = 0;
        this.amplifier = 0;
        this.langKey = langKey;
        this.effect = effect;
        this.hexColor = hexColor;
        this.fluidTag = fluidTag;
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
        return langKey;
    }

    public int getColor(){
        return hexColor;
    }

    public Holder<MobEffect> getEffect(){
        return effect;
    }

    public TagKey<Fluid> getFluidTag(){
        return fluidTag;
    }
}
