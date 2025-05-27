package net.davio.aquaticambitions.content.kinetics.fan.processing;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.davio.aquaticambitions.registry.recipe.CAARecipeTypes;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ChannelingRecipe extends ProcessingRecipe<SingleRecipeInput> {

    public ChannelingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
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

}

