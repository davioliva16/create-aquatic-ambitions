package net.davio.aquaticambitions.content.processing.conduit;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class MechanicalConduitEffect {

    private int ticks;
    private final String langKey;
    private final MobEffect effect;
    private final int hexColor;
    private final TagKey fluidTag;


    public MechanicalConduitEffect(String langKey, MobEffect effect, int hexColor, TagKey fluidTag) {
        this.ticks = 0;
        this.langKey = langKey;
        this.effect = effect;
        this.hexColor = hexColor;
        this.fluidTag = fluidTag;
    }

    public int getTicks(){
        return ticks;
    }

    public void setTicks(int newTicks) { //Used to update client side
        this.ticks = newTicks;
    }

    public boolean isActive(){
        return ticks > 0f;
    }

    public void addTicks(int amount){
        ticks += amount;
    }

    public void subtractTicks(){
        ticks --;
    }

    public String getLangKey(){
        return langKey;
    }

    public int getColor(){
        return hexColor;
    }

    public MobEffect getEffect(){
        return effect;
    }

    public TagKey getFluidTag(){
        return fluidTag;
    }
}
