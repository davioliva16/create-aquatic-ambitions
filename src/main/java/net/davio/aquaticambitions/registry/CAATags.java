package net.davio.aquaticambitions.registry;

import net.createmod.catnip.lang.Lang;
import net.davio.aquaticambitions.CreateAquaticAmbitions;

import com.simibubi.create.Create;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.material.FluidState;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.MODID;

public class CAATags {
    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }
    public static <T> TagKey<T> commonTag(Registry<T> registry, String path) {
        return optionalTag(registry, ResourceLocation.fromNamespaceAndPath("c", path));
    }
    public static <T> TagKey<T> modTag(Registry<T> registry, String path) {
        return optionalTag(registry, CreateAquaticAmbitions.asResource(path));
    }
    public static TagKey<Block> commonBlockTag(String path) {
        return commonTag(BuiltInRegistries.BLOCK, path);
    }
    public static TagKey<Item> commonItemTag(String path) {
        return commonTag(BuiltInRegistries.ITEM, path);
    }
    public static TagKey<Fluid> commonFluidTag(String path) {
        return commonTag(BuiltInRegistries.FLUID, path);
    }
    public static TagKey<Block> modBlockTag(String path) {
        return modTag(BuiltInRegistries.BLOCK, path);
    }
    public static TagKey<Item> modItemTag(String path) {
        return modTag(BuiltInRegistries.ITEM, path);
    }
    public static TagKey<Fluid> modFluidTag(String path) {
        return modTag(BuiltInRegistries.FLUID, path);
    }

    public enum NameSpace {
        MOD(MODID, false, true),
        COMMON("c"),
        CREATE(Create.ID);

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;
        NameSpace(String id) {
            this(id, true, false);
        }
        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum CAABlockTags {

        FAN_PROCESSING_CATALYSTS_CHANNELING(NameSpace.MOD, "fan_processing_catalysts/channeling");

        public final TagKey<Block> tag;
        public final boolean alwaysDatagen;

        CAABlockTags() {
            this(NameSpace.MOD);
        }

        CAABlockTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        CAABlockTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        CAABlockTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        CAABlockTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
        if (optional) {
            tag = optionalTag(BuiltInRegistries.BLOCK, id);
        } else {
            tag = BlockTags.create(id);
        }
        this.alwaysDatagen = alwaysDatagen;
        }

            @SuppressWarnings("deprecation")
            public boolean matches(Block block) {
            return block.builtInRegistryHolder()
                    .is(tag);
        }

            public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
        }

            public boolean matches(BlockState state) {
            return state.is(tag);
        }

            private static void init() {
        }
    }

    public enum CAAItemTags {

        UA_CORAL(NameSpace.MOD, "upgrade_aquatic/coral");

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        CAAItemTags() {
            this(NameSpace.MOD);
        }

        CAAItemTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        CAAItemTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        CAAItemTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        CAAItemTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(BuiltInRegistries.ITEM, id);
            } else {
                tag = ItemTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }

        private static void init() {
        }
    }

    public enum CAAFluidTags {

        CONDUIT_FUEL(NameSpace.MOD,"mechanical_conduit_fuel"),
        GIVES_FIRE_RES(NameSpace.MOD,"conduit_effects/gives_fire_resistance"),
        GIVES_HASTE(NameSpace.MOD,"conduit_effects/gives_haste"),
        GIVES_INFESTED(NameSpace.MOD,"conduit_effects/gives_infested"),
        GIVES_INVIS(NameSpace.MOD,"conduit_effects/gives_invisibility"),
        GIVES_JUMP(NameSpace.MOD,"conduit_effects/gives_jump"),
        GIVES_LUCK(NameSpace.MOD,"conduit_effects/gives_luck"),
        GIVES_NIGHT_VISION(NameSpace.MOD,"conduit_effects/gives_night_vision"),
        GIVES_OOZING(NameSpace.MOD,"conduit_effects/gives_oozing"),
        GIVES_POISON(NameSpace.MOD,"conduit_effects/gives_poison"),
        GIVES_REGEN(NameSpace.MOD,"conduit_effects/gives_regen"),
        GIVES_RESISTANCE(NameSpace.MOD,"conduit_effects/gives_resistance"),
        GIVES_SLOW_FALL(NameSpace.MOD,"conduit_effects/gives_slow_fall"),
        GIVES_SLOWNESS(NameSpace.MOD, "conduit_effects/gives_slowness"),
        GIVES_SPEED(NameSpace.MOD, "conduit_effects/gives_speed"),
        GIVES_STRENGTH(NameSpace.MOD, "conduit_effects/gives_strength"),
        GIVES_WATER_BREATHING(NameSpace.MOD, "conduit_effects/gives_water_breathing"),
        GIVES_WEAKNESS(NameSpace.MOD, "conduit_effects/gives_weakness"),
        GIVES_WEAVING(NameSpace.MOD, "conduit_effects/gives_weaving"),
        GIVES_WIND(NameSpace.MOD, "conduit_effects/gives_wind"),
        GIVES_WITHER(NameSpace.MOD, "conduit_effects/gives_wither"),
        FAN_PROCESSING_CATALYSTS_CHANNELING(NameSpace.MOD, "fan_processing_catalysts/channeling");
        

        public final TagKey<Fluid> tag;
        public final boolean alwaysDatagen;

        CAAFluidTags() {
            this(NameSpace.MOD);
        }

        CAAFluidTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        CAAFluidTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        CAAFluidTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        CAAFluidTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(BuiltInRegistries.FLUID, id);
            } else {
                tag = FluidTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Fluid fluid) {
            return fluid.is(tag);
        }

        public boolean matches(FluidState state) {
            return state.is(tag);
        }

        private static void init() {
        }
    }

    public static void init() {
        CAABlockTags.init();
        CAAItemTags.init();
        CAAFluidTags.init();
    }
}
