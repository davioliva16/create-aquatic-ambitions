package net.davio.aquaticambitions.content.processing.conduit;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitEffectDefinition.Behavior;
import net.davio.aquaticambitions.registry.CAARegistries;
import net.davio.aquaticambitions.registry.CAATags;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

/**
 * Built-in default entries for the {@code conduit_effect} datapack registry. These reproduce the effect set that
 * used to be hardcoded in {@link MechanicalConduitBlockEntity}'s constructor, so out-of-the-box behavior is
 * unchanged. Emitted as JSON by {@code CAAConduitEffectsProvider}; datapacks/mods add or override entries on top.
 */
public class CAAConduitEffects {

    public static void bootstrap(BootstrapContext<MechanicalConduitEffectDefinition> ctx) {
        // Awakening fuel (water) — also grants Conduit Power.
        apply(ctx, "conduit_power", "effect.minecraft.conduit_power", MobEffects.CONDUIT_POWER, 0x1DC2D1, CAATags.CAAFluidTags.CONDUIT_FUEL.tag);

        // Non-effect behaviors.
        behavior(ctx, "clear", "tooltip.create_aquatic_ambitions.effect.cleansing", 0xFFFFFF, CAATags.CAAFluidTags.CLEARS_EFFECTS.tag, Behavior.CLEAR_EFFECTS); // milk
        behavior(ctx, "burning", "tooltip.create_aquatic_ambitions.effect.burning", 0xE2AA22, CAATags.CAAFluidTags.SETS_ON_FIRE.tag, Behavior.SET_ON_FIRE); // lava

        // Potions and other mob effects.
        apply(ctx, "saturation", "effect.minecraft.saturation", MobEffects.SATURATION, 0xF82421, CAATags.CAAFluidTags.GIVES_SATURATION.tag);
        apply(ctx, "fire_resistance", "effect.minecraft.fire_resistance", MobEffects.FIRE_RESISTANCE, 0xE49A3A, CAATags.CAAFluidTags.GIVES_FIRE_RES.tag);
        apply(ctx, "haste", "effect.minecraft.haste", MobEffects.DIG_SPEED, 0xD9C043, CAATags.CAAFluidTags.GIVES_HASTE.tag);
        apply(ctx, "infested", "effect.minecraft.infested", MobEffects.INFESTED, 0x8C9B8C, CAATags.CAAFluidTags.GIVES_INFESTED.tag);
        apply(ctx, "invisibility", "effect.minecraft.invisibility", MobEffects.INVISIBILITY, 0x7F8392, CAATags.CAAFluidTags.GIVES_INVIS.tag);
        apply(ctx, "jump_boost", "effect.minecraft.jump_boost", MobEffects.JUMP, 0x23FC4D, CAATags.CAAFluidTags.GIVES_JUMP.tag);
        apply(ctx, "luck", "effect.minecraft.luck", MobEffects.LUCK, 0x339900, CAATags.CAAFluidTags.GIVES_LUCK.tag);
        apply(ctx, "night_vision", "effect.minecraft.night_vision", MobEffects.NIGHT_VISION, 0x1F1FA1, CAATags.CAAFluidTags.GIVES_NIGHT_VISION.tag);
        apply(ctx, "oozing", "effect.minecraft.oozing", MobEffects.OOZING, 0x99FFA3, CAATags.CAAFluidTags.GIVES_OOZING.tag);
        apply(ctx, "poison", "effect.minecraft.poison", MobEffects.POISON, 0x4E9331, CAATags.CAAFluidTags.GIVES_POISON.tag);
        apply(ctx, "regeneration", "effect.minecraft.regeneration", MobEffects.REGENERATION, 0xCD5CAB, CAATags.CAAFluidTags.GIVES_REGEN.tag);
        apply(ctx, "resistance", "effect.minecraft.resistance", MobEffects.DAMAGE_RESISTANCE, 0x8F45ED, CAATags.CAAFluidTags.GIVES_RESISTANCE.tag);
        apply(ctx, "slow_falling", "effect.minecraft.slow_falling", MobEffects.SLOW_FALLING, 0xFFEFD1, CAATags.CAAFluidTags.GIVES_SLOW_FALL.tag);
        apply(ctx, "slowness", "effect.minecraft.slowness", MobEffects.MOVEMENT_SLOWDOWN, 0x5A6C81, CAATags.CAAFluidTags.GIVES_SLOWNESS.tag);
        apply(ctx, "speed", "effect.minecraft.speed", MobEffects.MOVEMENT_SPEED, 0x7CAFC6, CAATags.CAAFluidTags.GIVES_SPEED.tag);
        apply(ctx, "strength", "effect.minecraft.strength", MobEffects.DAMAGE_BOOST, 0xFCC500, CAATags.CAAFluidTags.GIVES_STRENGTH.tag);
        apply(ctx, "water_breathing", "effect.minecraft.water_breathing", MobEffects.WATER_BREATHING, 0x96D7BE, CAATags.CAAFluidTags.GIVES_WATER_BREATHING.tag);
        apply(ctx, "weakness", "effect.minecraft.weakness", MobEffects.WEAKNESS, 0x484D48, CAATags.CAAFluidTags.GIVES_WEAKNESS.tag);
        apply(ctx, "weaving", "effect.minecraft.weaving", MobEffects.WEAVING, 0x78695A, CAATags.CAAFluidTags.GIVES_WEAVING.tag);
        apply(ctx, "wind_charged", "effect.minecraft.wind_charged", MobEffects.WIND_CHARGED, 0xBDC9FF, CAATags.CAAFluidTags.GIVES_WIND.tag);
        apply(ctx, "wither", "effect.minecraft.wither", MobEffects.WITHER, 0x352A27, CAATags.CAAFluidTags.GIVES_WITHER.tag);
    }

    private static void apply(BootstrapContext<MechanicalConduitEffectDefinition> ctx, String name, String langKey,
                              Holder<MobEffect> effect, int color, TagKey<Fluid> fluidTag) {
        register(ctx, name, new MechanicalConduitEffectDefinition(Optional.of(effect), color, langKey, fluidTag, Behavior.APPLY, true));
    }

    private static void behavior(BootstrapContext<MechanicalConduitEffectDefinition> ctx, String name, String langKey,
                                 int color, TagKey<Fluid> fluidTag, Behavior behavior) {
        register(ctx, name, new MechanicalConduitEffectDefinition(Optional.empty(), color, langKey, fluidTag, behavior, true));
    }

    private static void register(BootstrapContext<MechanicalConduitEffectDefinition> ctx, String name,
                                 MechanicalConduitEffectDefinition definition) {
        ctx.register(ResourceKey.create(CAARegistries.CONDUIT_EFFECT, CreateAquaticAmbitions.asResource(name)), definition);
    }

    private CAAConduitEffects() {
    }
}
