package net.davio.aquaticambitions.infrastructure.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAAItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CAACrushingRecipeGen extends CrushingRecipeGen {
    GeneratedRecipe

    PRISMARINE_BRICKS_TO_LAPIS_EXP_NUGGETS = create(CreateAquaticAmbitions.asResource("prismarine_bricks_to_lapis_and_copper"), b -> b
        .duration(150)
        .require(Items.PRISMARINE_BRICKS)
        .output(.9f, Items.LAPIS_LAZULI, 1)
        .output( 1,  AllItems.EXP_NUGGET, 2)
        .output(.5f, AllItems.EXP_NUGGET, 1)
        .output(.125f, AllItems.COPPER_NUGGET, 1)),

    PRISMARINE__TO_LAPIS_EXP_NUGGETS = create(CreateAquaticAmbitions.asResource("prismarine_to_lapis"), b -> b
        .duration(150)
        .require(Items.PRISMARINE)
        .output(.5f, Items.LAPIS_LAZULI, 1)
        .output( .75f,  AllItems.EXP_NUGGET, 1)),

    NAUTILUS_SHARDS = create(CreateAquaticAmbitions.asResource("nautilus_shards"), b -> b
        .duration(150)
        .require(Items.NAUTILUS_SHELL)
        .output(1, Items.BONE_MEAL, 3)
        .output(.75f, Items.BONE_MEAL, 2)
        .output( 1,  AllItems.EXP_NUGGET, 3)
        .output(.5f, AllItems.EXP_NUGGET, 2)
        .output(.125f, CAAItems.SPIKY_SHELL, 1));

    public CAACrushingRecipeGen(PackOutput output, String defaultNamespace) {
        super(output, CreateAquaticAmbitions.MODID);
    }
}

