package net.davio.aquaticambitions.registry;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;
import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class CAABlocks {

    static {
        REGISTRATE.setCreativeTab(CAACreativeModeTab.CREATIVE_TAB);
    }

    public static final BlockEntry<Block> PRISMARINE_ALLOY_BLOCK = REGISTRATE.block("prismarine_alloy_block" ,Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.mapColor(MapColor.GLOW_LICHEN).requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .tag(BlockTags.NEEDS_STONE_TOOL)
            .transform(tagBlockAndItem("storage_blocks/prismarine"))
            .build()
            .lang("Prismarine Alloy Block")
            .register();

    public static final BlockEntry<MechanicalConduitBlock> MECHANICAL_CONDUIT = REGISTRATE.block("mechanical_conduit", MechanicalConduitBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p
                    .mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .lightLevel(MechanicalConduitBlock::getLight)
            ).transform(pickaxeOnly())
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {};
}
