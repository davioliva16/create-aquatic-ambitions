package net.davio.create_aquatic_ambitions.kinetics.fan.processing;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import net.davio.create_aquatic_ambitions.entry.CCARecipeTypes;
import net.minecraft.world.level.Level;
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

	public static class ChannelingWrapper extends ItemStackHandlerContainer {
		public ChannelingWrapper() {
			super(1);
		}
	}
}
