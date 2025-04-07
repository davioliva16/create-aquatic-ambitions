package net.davio.aquaticambitions.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;

public class CustomProcessingSerializer <T extends ProcessingRecipe<?>> extends ProcessingRecipeSerializer<T> {

    public final MapCodec<T> CODEC = CCARecipeTypes.CODEC.dispatchMap(t -> (CCARecipeTypes) t.getTypeInfo(), CCARecipeTypes::processingCodec);


    public CustomProcessingSerializer(ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory) {
        super(factory);
    }

    @Override
    public MapCodec<T> codec() {
        return CODEC;
    }

}
