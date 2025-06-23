package net.davio.aquaticambitions.foundation.data.recipe;

import com.simibubi.create.foundation.data.recipe.Mods;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.api.data.recipe.ChannelingRecipeGen;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class CAAChannelingRecipeGen extends ChannelingRecipeGen {

    GeneratedRecipe

    PRISMARINE =  create(CreateAquaticAmbitions.asResource("prismarine"), b -> b
        .require(Items.FLINT)
        .output(.33f, Items.PRISMARINE_SHARD, 1)),

    CHANNEL_SUS_ROCK =  create(CreateAquaticAmbitions.asResource("suspicious_rock"), b -> b
            .require(CAAItems.SUSPICIOUS_ROCK)
            .output(.1f, CAAItems.SPIKY_SHELL, 1)
            .output(.5f, Items.NAUTILUS_SHELL, 1)),

    PRISMARINE_CRYSTALS = convert(Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS),
    HEART_OF_THE_SEA = convert(Items.ENDER_EYE, Items.HEART_OF_THE_SEA),

    EXPOSED_COPPER = convert(Items.COPPER_BLOCK, Items.EXPOSED_COPPER),
    WEATHERED_COPPER = convert(Items.EXPOSED_COPPER, Items.WEATHERED_COPPER),
    OXIDIZED_COPPER = convert(Items.WEATHERED_COPPER, Items.OXIDIZED_COPPER),

    TUBE_BLOCK =  convert(Items.DEAD_TUBE_CORAL_BLOCK, Items.TUBE_CORAL_BLOCK),
    BRAIN_BLOCK =  convert(Items.DEAD_BRAIN_CORAL_BLOCK, Items.BRAIN_CORAL_BLOCK),
    BUBBLE_BLOCK =  convert(Items.DEAD_BUBBLE_CORAL_BLOCK, Items.BUBBLE_CORAL_BLOCK),
    FIRE_BLOCK =  convert(Items.DEAD_FIRE_CORAL_BLOCK, Items.FIRE_CORAL_BLOCK),
    HORN_BLOCK =  convert(Items.DEAD_HORN_CORAL_BLOCK, Items.HORN_CORAL_BLOCK),

    TUBE = coralRevival(Items.DEAD_TUBE_CORAL, () -> Items.TUBE_CORAL),
    BRAIN = coralRevival(Items.DEAD_BRAIN_CORAL, () -> Items.BRAIN_CORAL),
    BUBBLE = coralRevival(Items.DEAD_BUBBLE_CORAL, () -> Items.BUBBLE_CORAL),
    FIRE = coralRevival(Items.DEAD_FIRE_CORAL, () -> Items.FIRE_CORAL),
    HORN = coralRevival(Items.DEAD_HORN_CORAL, () -> Items.HORN_CORAL),

    TUBE_FAN = coralRevival(Items.DEAD_TUBE_CORAL_FAN, () -> Items.TUBE_CORAL_FAN),
    BRAIN_FAN = coralRevival(Items.DEAD_BRAIN_CORAL_FAN, () -> Items.BRAIN_CORAL_FAN),
    BUBBLE_FAN = coralRevival(Items.DEAD_BUBBLE_CORAL_FAN, () -> Items.BUBBLE_CORAL_FAN),
    FIRE_FAN = coralRevival(Items.DEAD_FIRE_CORAL_FAN, () -> Items.FIRE_CORAL_FAN),
    HORN_FAN = coralRevival(Items.DEAD_HORN_CORAL_FAN, () -> Items.HORN_CORAL_FAN),

    SPONGE = coralRevival(Blocks.SPONGE.asItem(), Blocks.WET_SPONGE::asItem),

    //UA COMPAT

    UA_ACAN_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_acan_coral_block","acan_coral_block"),
    UA_BRANCH_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_branch_coral_block","branch_coral_block"),
    UA_CHROME_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_chrome_coral_block","chrome_coral_block"),
    UA_FINGER_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_finger_coral_block","finger_coral_block"),
    UA_MOSS_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_moss_coral_block","moss_coral_block"),
    UA_PETAL_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_petal_coral_block","petal_coral_block"),
    UA_PILLOW_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_pillow_coral_block","pillow_coral_block"),
    UA_ROCK_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_rock_coral_block","pillow_rock_block"),
    UA_SILK_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_silk_coral_block","pillow_silk_block"),
    UA_STAR_CORAL_BLOCK = moddedConversion(Mods.UA, "dead_star_coral_block","pillow_star_block"),

    UA_ACAN_CORAL = moddedCoralRevival(Mods.UA, "acan_coral"),
    UA_BRANCH_CORAL = moddedCoralRevival(Mods.UA, "branch_coral"),
    UA_CHROME_CORAL = moddedCoralRevival(Mods.UA, "chrome_coral"),
    UA_FINGER_CORAL = moddedCoralRevival(Mods.UA, "finger_coral"),
    UA_MOSS_CORAL = moddedCoralRevival(Mods.UA, "moss_coral"),
    UA_PETAL_CORAL = moddedCoralRevival(Mods.UA, "petal_coral"),
    UA_PILLOW_CORAL = moddedCoralRevival(Mods.UA, "pillow_coral"),
    UA_ROCK_CORAL = moddedCoralRevival(Mods.UA, "rock_coral"),
    UA_SILK_CORAL = moddedCoralRevival(Mods.UA, "silk_coral"),
    UA_STAR_CORAL = moddedCoralRevival(Mods.UA, "star_coral"),

    UA_ACAN_CORAL_FAN = moddedCoralRevival(Mods.UA, "acan_coral_fan"),
    UA_BRANCH_CORAL_FAN = moddedCoralRevival(Mods.UA, "branch_coral_fan"),
    UA_CHROME_CORAL_FAN = moddedCoralRevival(Mods.UA, "chrome_coral_fan"),
    UA_FINGER_CORAL_FAN = moddedCoralRevival(Mods.UA, "finger_coral_fan"),
    UA_MOSS_CORAL_FAN = moddedCoralRevival(Mods.UA, "moss_coral_fan"),
    UA_PETAL_CORAL_FAN = moddedCoralRevival(Mods.UA, "petal_coral_fan"),
    UA_PILLOW_CORAL_FAN = moddedCoralRevival(Mods.UA, "pillow_coral_fan"),
    UA_ROCK_CORAL_FAN = moddedCoralRevival(Mods.UA, "rock_coral_fan"),
    UA_SILK_CORAL_FAN = moddedCoralRevival(Mods.UA, "silk_coral_fan"),
    UA_STAR_CORAL_FAN = moddedCoralRevival(Mods.UA, "star_coral_fan");

    public CAAChannelingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }

    public GeneratedRecipe moddedConversion(Mods mod, String input, String output) {
        return create(mod.getId() + "/" + output, b -> b
                .require(mod, input)
                .output(mod, output)
                .whenModLoaded(mod.getId()));
    }

    public GeneratedRecipe moddedCoralRevival(Mods mod, String coral){
        return create(mod.getId() + "/" + coral, b -> b
                .require(mod, "dead_"+coral)
                .output(1, mod, coral,1 )
                .output(0.25f, mod, coral,1 )
                .whenModLoaded(mod.getId()));
    }
}
