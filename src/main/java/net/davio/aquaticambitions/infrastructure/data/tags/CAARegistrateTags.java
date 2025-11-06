package net.davio.aquaticambitions.infrastructure.data.tags;


import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.davio.aquaticambitions.registry.CAATags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CAARegistrateTags {
    public static void addGenerators() {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CAARegistrateTags::genItemTags);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, CAARegistrateTags::genBlockTags);
        REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, CAARegistrateTags::genFluidTags);

    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);
        //We don't really have item tags yet
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

        prov.tag(CAATags.CAABlockTags.FAN_PROCESSING_CATALYSTS_CHANNELING.tag);

        prov.tag(BlockTags.LEAVES)
                .addOptional(ResourceLocation.withDefaultNamespace("brain_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("bubble_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("fire_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("horn_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("tube_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("brain_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("bubble_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("fire_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("horn_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("tube_coral_fan"))

                .addOptional(ResourceLocation.withDefaultNamespace("dead_brain_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_bubble_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_fire_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_horn_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_tube_coral"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_brain_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_bubble_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_fire_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_horn_coral_fan"))
                .addOptional(ResourceLocation.withDefaultNamespace("dead_tube_coral_fan"))
        //Upgrade Aquatic
                // Live corals
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "acan_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "branch_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "chrome_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "finger_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "moss_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "petal_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "pillow_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "rock_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "silk_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "star_coral"))
                // Live coral fans
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "acan_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "branch_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "chrome_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "finger_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "moss_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "petal_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "pillow_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "rock_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "silk_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "star_coral_fan"))
                // Dead corals
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_acan_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_branch_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_chrome_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_finger_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_moss_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_petal_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_pillow_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_rock_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_silk_coral"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_star_coral"))
                // Dead coral fans
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_acan_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_branch_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_chrome_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_finger_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_moss_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_petal_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_pillow_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_rock_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_silk_coral_fan"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(Mods.UA.getId(), "dead_star_coral_fan"));
    }

    private static void genFluidTags(RegistrateTagsProvider<Fluid> provIn) {
        TagGen.CreateTagsProvider<Fluid> prov = new TagGen.CreateTagsProvider<>(provIn, Fluid::builtInRegistryHolder);

        prov.tag(CAATags.CAAFluidTags.CONDUIT_FUEL.tag)
                .add(Fluids.WATER);
        prov.tag(CAATags.CAAFluidTags.CLEARS_EFFECTS.tag)
                .add(NeoForgeMod.MILK.get());
        prov.tag(CAATags.CAAFluidTags.GIVES_HASTE.tag)
                .add(AllFluids.TEA.get());
        prov.tag(CAATags.CAAFluidTags.GIVES_SATURATION.tag)
                .add(AllFluids.HONEY.getSource())
                .add(AllFluids.CHOCOLATE.getSource());
        prov.tag(CAATags.CAAFluidTags.SETS_ON_FIRE.tag)
                .add(Fluids.LAVA);

        prov.tag(CAATags.CAAFluidTags.GIVES_FIRE_RES.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_INFESTED.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_INVIS.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_JUMP.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_LUCK.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_NIGHT_VISION.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_OOZING.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_POISON.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_REGEN.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_RESISTANCE.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_SLOW_FALL.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_SLOWNESS.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_SPEED.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_STRENGTH.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_WATER_BREATHING.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_WEAKNESS.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_WEAVING.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_WIND.tag);
        prov.tag(CAATags.CAAFluidTags.GIVES_WITHER.tag);
        prov.tag(CAATags.CAAFluidTags.FAN_PROCESSING_CATALYSTS_CHANNELING.tag);
    }
}

