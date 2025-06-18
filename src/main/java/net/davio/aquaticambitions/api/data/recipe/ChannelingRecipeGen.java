package net.davio.aquaticambitions.api.data.recipe;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public  abstract class ChannelingRecipeGen extends StandardProcessingRecipeGen<ChannelingRecipe> {

    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return create(asResource(RegisteredObjectsHelper.getKeyOrThrow(result.get().asItem())
                        .getPath()),
                p -> p.withItemIngredients(input.get())
                        .output(result.get()));
    }

    public GeneratedRecipe coralRevival(Item deadCoral, Supplier<ItemLike> coral) {
        return create(CreateAquaticAmbitions.asResource(RegisteredObjectsHelper.getKeyOrThrow(coral.get().asItem()).getPath()+"_revival"),
                b -> b
                        .require(deadCoral)
                        .output(coral.get(), 1)
                        .output(0.25f, coral.get(), 1));
    }

    public ChannelingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return CAARecipeTypes.CHANNELING;
    }

}

