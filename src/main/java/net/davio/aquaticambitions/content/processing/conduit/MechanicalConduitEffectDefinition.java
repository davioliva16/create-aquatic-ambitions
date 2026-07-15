package net.davio.aquaticambitions.content.processing.conduit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

/**
 * Data-driven definition of a single Mechanical Conduit effect, loaded from the {@code conduit_effect} datapack
 * registry ({@code data/<namespace>/conduit_effect/*.json}). One JSON file = one effect. Definitions are
 * immutable and shared; per-conduit mutable state (ticks/amplifier) lives in {@link MechanicalConduitEffect}.
 *
 * <p>Because this is a datapack registry, entries sync to clients automatically and can be overridden by higher
 * priority datapacks (drop a file at the same path in the {@code create_aquatic_ambitions} namespace), or extended
 * by other mods (a file under their own namespace). Set {@code enabled} to {@code false} to disable a built-in
 * effect without removing it.
 */
public record MechanicalConduitEffectDefinition(
        Optional<Holder<MobEffect>> mobEffect,
        int color,
        String langKey,
        TagKey<Fluid> fluidTag,
        Behavior behavior,
        boolean enabled
) {
    /** Reads/writes colors as {@code "#RRGGBB"} hex strings for human-friendly JSON. */
    public static final Codec<Integer> COLOR_CODEC = Codec.STRING.comapFlatMap(
            s -> {
                try {
                    return DataResult.success(Integer.parseInt(s.startsWith("#") ? s.substring(1) : s, 16));
                } catch (NumberFormatException e) {
                    return DataResult.error(() -> "Not a hex color string: " + s);
                }
            },
            i -> String.format("#%06X", i & 0xFFFFFF));

    /**
     * Optional {@code mob_effect} id that tolerates an unresolvable value. A datapack can reference an effect from a
     * mod that isn't installed (see the Ars Nouveau {@code mana_regen} example in the README) or simply typo the id;
     * with {@code holderByNameCodec()} that threw and failed the whole {@code conduit_effect} registry load, which
     * prevents the world from loading. Here an absent, malformed, or unregistered id all decode to
     * {@link Optional#empty()} — the entry still loads, just with no effect (an {@code apply} behavior with no effect
     * is a no-op) — so one bad datapack line can't brick a world.
     */
    public static final MapCodec<Optional<Holder<MobEffect>>> MOB_EFFECT_FIELD = Codec.STRING.optionalFieldOf("mob_effect").xmap(
            optString -> optString
                    .map(ResourceLocation::tryParse) // empty if the string isn't a valid id
                    .flatMap(id -> BuiltInRegistries.MOB_EFFECT.getHolder(ResourceKey.create(Registries.MOB_EFFECT, id)))
                    .map(ref -> (Holder<MobEffect>) ref), // empty if the effect isn't registered (e.g. mod not installed)
            optHolder -> optHolder.flatMap(Holder::unwrapKey).map(key -> key.location().toString()));

    public static final Codec<MechanicalConduitEffectDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MOB_EFFECT_FIELD.forGetter(MechanicalConduitEffectDefinition::mobEffect),
            COLOR_CODEC.fieldOf("color").forGetter(MechanicalConduitEffectDefinition::color),
            Codec.STRING.fieldOf("lang_key").forGetter(MechanicalConduitEffectDefinition::langKey),
            TagKey.codec(Registries.FLUID).fieldOf("fluid_tag").forGetter(MechanicalConduitEffectDefinition::fluidTag),
            Behavior.CODEC.optionalFieldOf("behavior", Behavior.APPLY).forGetter(MechanicalConduitEffectDefinition::behavior),
            Codec.BOOL.optionalFieldOf("enabled", true).forGetter(MechanicalConduitEffectDefinition::enabled)
    ).apply(instance, MechanicalConduitEffectDefinition::new));

    /** What a conduit does when this effect is active, generalizing the old milk/lava special cases. */
    public enum Behavior implements StringRepresentable {
        /** Apply {@link #mobEffect} to nearby entities (the common case). */
        APPLY("apply"),
        /** Remove all effects from nearby entities (milk). */
        CLEAR_EFFECTS("clear_effects"),
        /** Set nearby entities on fire (lava). */
        SET_ON_FIRE("set_on_fire");

        public static final Codec<Behavior> CODEC = StringRepresentable.fromEnum(Behavior::values);

        private final String name;

        Behavior(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
