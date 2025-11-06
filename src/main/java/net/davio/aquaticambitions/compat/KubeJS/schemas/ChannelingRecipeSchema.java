package net.davio.aquaticambitions.compat.KubeJS.schemas;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import net.davio.aquaticambitions.compat.KubeJS.create.ProcessingOutputRecipeComponent;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface ChannelingRecipeSchema {
    RecipeKey<List<Ingredient>> INGREDIENT = IngredientComponent.INGREDIENT.instance().asList().withBounds(IntBounds.of(1, 1)).inputKey("ingredients");
    RecipeKey<List<ProcessingOutput>> RESULTS = ProcessingOutputRecipeComponent.TYPE.instance().asList().withBounds(IntBounds.of(1, 16)).outputKey("results");

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENT, RESULTS);
}
