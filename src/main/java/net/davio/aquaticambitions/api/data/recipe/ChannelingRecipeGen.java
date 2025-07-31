package net.davio.aquaticambitions.api.data.recipe;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.createmod.catnip.platform.CatnipServices;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public  abstract class ChannelingRecipeGen extends ProcessingRecipeGen {

    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return create(asResource(CatnipServices.REGISTRIES.getKeyOrThrow(result.get()
                                .asItem())
                        .getPath()),
                p -> p.withItemIngredients(input.get())
                        .output(result.get()));
    }

    public GeneratedRecipe coralRevival(Supplier<ItemLike> deadCoral, Supplier<ItemLike> coral) {
        return create(deadCoral, b -> b.output(coral.get(), 1)
                .output(0.25f, coral.get(), 1));
    }

    public ChannelingRecipeGen(PackOutput output, String defaultNamespace) {
        super(output, defaultNamespace);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return CAARecipeTypes.CHANNELING;
    }
}

