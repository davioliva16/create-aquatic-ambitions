package net.davio.aquaticambitions.data.tags;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.davio.aquaticambitions.registry.CAATags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

//Currently not used
public class TagGen {
    public static void addGenerators() {
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::genBlockTags);
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        com.simibubi.create.foundation.data.TagGen.CreateTagsProvider<Block> prov = new com.simibubi.create.foundation.data.TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
            prov.tag(CAATags.CAABlockTags.FAN_PROCESSING_CATALYSTS_CHANNELING.tag).add(
                Blocks.CONDUIT
        );

        for (CAATags.CAABlockTags tag : CAATags.CAABlockTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }
}
