package net.davio.aquaticambitions.content.kinetics.fan.processing;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ChannelingRecipe extends StandardProcessingRecipe<SingleRecipeInput> {

    public ChannelingRecipe(ProcessingRecipeParams params) {
        super(CAARecipeTypes.CHANNELING, params);
    }

    @Override
    public boolean matches(SingleRecipeInput inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0).test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    public static StandardProcessingRecipe.Builder<ChannelingRecipe> builder(ResourceLocation id) {
        return new StandardProcessingRecipe.Builder<>(ChannelingRecipe::new, id);
    }
}

