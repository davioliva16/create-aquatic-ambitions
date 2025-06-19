package net.davio.aquaticambitions.foundation.data.tags;


import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.data.TagGen;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.davio.aquaticambitions.registry.CAATags;
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
            //If config enables it, add conduit block and conduit cage here to let them channel while inactive
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

