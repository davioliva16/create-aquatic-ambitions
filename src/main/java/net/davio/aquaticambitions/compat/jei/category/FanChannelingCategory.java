package net.davio.aquaticambitions.compat.jei.category;

import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.davio.aquaticambitions.compat.jei.category.animations.AnimatedConduit;
import net.davio.aquaticambitions.kinetics.fan.processing.ChannelingRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class FanChannelingCategory extends ProcessingViaFanCategory.MultiOutput<ChannelingRecipe> {

        private final AnimatedConduit conduit = new AnimatedConduit();
        public FanChannelingCategory(Info<ChannelingRecipe> info) {super(info);}

        @Override
        protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
        }
        @Override
        protected void renderAttachedBlock(GuiGraphics graphics) {
                conduit.draw(graphics,0,0);
        }
}
