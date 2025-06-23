package net.davio.aquaticambitions.foundation.data.tags;


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
                .addOptional(new ResourceLocation("minecraft","brain_coral"))
                .addOptional(new ResourceLocation("minecraft","bubble_coral"))
                .addOptional(new ResourceLocation("minecraft","fire_coral"))
                .addOptional(new ResourceLocation("minecraft","horn_coral"))
                .addOptional(new ResourceLocation("minecraft","tube_coral"))
                .addOptional(new ResourceLocation("minecraft","brain_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","bubble_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","fire_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","horn_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","tube_coral_fan"))

                .addOptional(new ResourceLocation("minecraft","dead_brain_coral"))
                .addOptional(new ResourceLocation("minecraft","dead_bubble_coral"))
                .addOptional(new ResourceLocation("minecraft","dead_fire_coral"))
                .addOptional(new ResourceLocation("minecraft","dead_horn_coral"))
                .addOptional(new ResourceLocation("minecraft","dead_tube_coral"))
                .addOptional(new ResourceLocation("minecraft","dead_brain_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","dead_bubble_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","dead_fire_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","dead_horn_coral_fan"))
                .addOptional(new ResourceLocation("minecraft","dead_tube_coral_fan"))
        //Upgrade Aquatic
                // Live corals
                .addOptional(new ResourceLocation(Mods.UA.getId(), "acan_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "branch_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "chrome_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "finger_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "moss_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "petal_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "pillow_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "rock_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "silk_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "star_coral"))
                // Live coral fans
                .addOptional(new ResourceLocation(Mods.UA.getId(), "acan_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "branch_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "chrome_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "finger_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "moss_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "petal_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "pillow_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "rock_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "silk_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "star_coral_fan"))
                // Dead corals
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_acan_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_branch_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_chrome_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_finger_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_moss_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_petal_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_pillow_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_rock_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_silk_coral"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_star_coral"))
                // Dead coral fans
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_acan_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_branch_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_chrome_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_finger_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_moss_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_petal_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_pillow_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_rock_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_silk_coral_fan"))
                .addOptional(new ResourceLocation(Mods.UA.getId(), "dead_star_coral_fan"));
    }

    private static void genFluidTags(RegistrateTagsProvider<Fluid> provIn) {
        TagGen.CreateTagsProvider<Fluid> prov = new TagGen.CreateTagsProvider<>(provIn, Fluid::builtInRegistryHolder);

        prov.tag(CAATags.CAAFluidTags.CONDUIT_FUEL.tag)
                .add(Fluids.WATER);
        prov.tag(CAATags.CAAFluidTags.GIVES_HASTE.tag)
                .add(AllFluids.TEA.get());

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

