package net.davio.aquaticambitions.compat.KubeJS.schemas;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeValidationContext;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.compat.KubeJS.create.ProcessingOutputRecipeComponent;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface ChannelingRecipeSchema {
    RecipeKey<List<Ingredient>> INGREDIENT = IngredientComponent.INGREDIENT.instance().asList().inputKey("ingredients");
    RecipeKey<List<ProcessingOutput>> RESULTS = ProcessingOutputRecipeComponent.TYPE.instance().asList().outputKey("results");

    class RecipeValidation extends KubeRecipe {
        @Override
        public void validate(RecipeValidationContext cx) {
            if (cx.recipe().get("ingredients") instanceof List<?> list && list.size() > 1) {
                cx.errors().push(new KubeRuntimeException("Recipe cannot have more than 1 ingredient").source(sourceLine));
            }

            if (cx.recipe().get("results") instanceof List<?> list && list.size() > 12) {
                cx.errors().push(new KubeRuntimeException("Recipe cannot have more than 12 results").source(sourceLine));
            }
        }
    }

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENT, RESULTS)
            .factory(new KubeRecipeFactory(location("channeling"), TypeInfo.of(ChannelingRecipe.class), RecipeValidation::new));

    private static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(CreateAquaticAmbitions.MODID, path);
    }
}
