package net.davio.aquaticambitions.registry;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;
import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock;
import net.davio.aquaticambitions.ponder.util.fakeConduitEntity.FakeActiveConduitBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class CAABlocks {

    static {
        REGISTRATE.setCreativeTab(CAACreativeTab.CREATIVE_TAB);
    }

    public static final BlockEntry<Block> PRISMARINE_ALLOY_BLOCK = REGISTRATE.block("prismarine_alloy_block" ,Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.mapColor(MapColor.GLOW_LICHEN).requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .transform(tagBlockAndItem(
                    TagKey.create(Registries.BLOCK, new ResourceLocation("c", "storage_blocks/prismarine_alloy")),
                    TagKey.create(Registries.ITEM, new ResourceLocation("c", "storage_blocks/prismarine_alloy"))
            ))
            .build()
            .lang("Prismarine Alloy Block")
            .register();

    public static final BlockEntry<MechanicalConduitBlock> MECHANICAL_CONDUIT = REGISTRATE
            .block("mechanical_conduit", MechanicalConduitBlock::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .properties(p -> p
                    .mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .lightLevel(MechanicalConduitBlock::getLight)
            ).transform(pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .item().model(AssetLookup.customBlockItemModel("mechanical_conduit"))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<FakeActiveConduitBlock> FAKE_CONDUIT = REGISTRATE
            .block("fake_active_conduit", FakeActiveConduitBlock::new)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(),
                    prov.models().getExistingFile(ctx.getId())))
            .register();

    public static void register() {}
}
