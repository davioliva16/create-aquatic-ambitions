package net.davio.aquaticambitions.foundation.data.recipe;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.api.data.recipe.ChannelingDataGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class CAAChannelingDataGen extends ChannelingDataGen {

    GeneratedRecipe

    PRISMARINE =  create(CreateAquaticAmbitions.asResource("prismarine"), b -> b
        .require(Items.FLINT)
        .output(.33f, Items.PRISMARINE_SHARD, 1)),

    PRISMARINE_CRYSTALS = convert(Items.GLOWSTONE, Items.PRISMARINE_CRYSTALS),
    HEART_OF_THE_SEA = convert(Items.ENDER_EYE, Items.HEART_OF_THE_SEA),

    EXPOSED_COPPER = convert(Items.COPPER_BLOCK, Items.EXPOSED_COPPER),
    WEATHERED_COPPER = convert(Items.EXPOSED_COPPER, Items.WEATHERED_COPPER),
    OXIDIZED_COPPER = convert(Items.WEATHERED_COPPER, Items.OXIDIZED_COPPER),

    TUBE_BLOCK =  convert(Items.DEAD_TUBE_CORAL, Items.TUBE_CORAL_BLOCK),
    BRAIN_BLOCK =  convert(Items.DEAD_BRAIN_CORAL, Items.BRAIN_CORAL_BLOCK),
    BUBBLE_BLOCK =  convert(Items.DEAD_BUBBLE_CORAL, Items.BUBBLE_CORAL_BLOCK),
    FIRE_BLOCK =  convert(Items.DEAD_FIRE_CORAL, Items.FIRE_CORAL_BLOCK),
    HORN_BLOCK =  convert(Items.DEAD_HORN_CORAL, Items.HORN_CORAL_BLOCK),

    TUBE = coralRevival(Items.DEAD_TUBE_CORAL, () -> Items.TUBE_CORAL),
    BRAIN = coralRevival(Items.DEAD_BRAIN_CORAL, () -> Items.BRAIN_CORAL),
    BUBBLE = coralRevival(Items.DEAD_BUBBLE_CORAL, () -> Items.BUBBLE_CORAL),
    FIRE = coralRevival(Items.DEAD_FIRE_CORAL, () -> Items.FIRE_CORAL),
    HORN = coralRevival(Items.DEAD_HORN_CORAL, () -> Items.HORN_CORAL),

    SPONGE = coralRevival(Blocks.SPONGE.asItem(), Blocks.WET_SPONGE::asItem); //TODO DOES THIS WORK?

    public CAAChannelingDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }
}
