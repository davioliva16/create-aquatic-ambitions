package net.davio.aquaticambitions.kinetics.fan.processing;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import net.davio.aquaticambitions.entry.CCARecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ChannelingRecipe extends ProcessingRecipe<ChannelingRecipe.ChannelingWrapper> {
    public ChannelingRecipe(ProcessingRecipeParams params) {
        super(CCARecipeTypes.CHANNELING, params);
    }
    @Override
    public boolean matches(ChannelingWrapper inv, Level worldIn){
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0));
    }
    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    public static class ChannelingWrapper extends RecipeWrapper{
        public ChannelingWrapper() {
            super(new ItemStackHandler(1));
        }
    }
}
