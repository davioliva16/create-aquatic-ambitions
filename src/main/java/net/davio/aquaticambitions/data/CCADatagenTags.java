package net.davio.aquaticambitions.data;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.davio.aquaticambitions.registry.CCATags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

//Currently not used
public class CCADatagenTags {
    public static void addGenerators() {
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, CCADatagenTags::genBlockTags);
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
            prov.tag(CCATags.CCABlockTags.FAN_PROCESSING_CATALYSTS_CHANELLING.tag).add(
                Blocks.CONDUIT
        );

        for (CCATags.CCABlockTags tag : CCATags.CCABlockTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }
}
