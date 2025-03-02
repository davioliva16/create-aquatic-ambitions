package net.davio.create_aquatic_ambitions.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.davio.create_aquatic_ambitions.entry.CCAPartials;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
public class AnimatedConduit extends AnimatedKinetics {

	public static float getConduitAngle(boolean xAxis) {
		if (xAxis){
			return 30*(Mth.sin(((AnimationTickHolder.getRenderTime() * 2f) % 360)*(float)Math.PI/180));
		} else {
			return (AnimationTickHolder.getRenderTime() * 2f) % 360;
		}
	}
	public static float getConduitHeight() {
		return (0.2f*(Mth.sin(((AnimationTickHolder.getRenderTime() * 4f) % 360)*(float)Math.PI/180)));
	}

	@Override
	public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
		PoseStack matrixStack = graphics.pose();
		matrixStack.pushPose();
		matrixStack.translate(0, 0, 0);
		int scale = 24;

		blockElement(CCAPartials.CONDUIT_CAGE)
				.atLocal(0,getConduitHeight(),2)
				.rotateBlock(getConduitAngle(true),getConduitAngle(false),0)
				.scale(scale)
				.render(graphics);

		blockElement(CCAPartials.CONDUIT_EYE)
				.atLocal(0,getConduitHeight(),2)
				.rotateBlock( 0,-22.5,0)
				.scale(scale)
				.render(graphics);

		blockElement(CCAPartials.CONDUIT_WIND)
				.atLocal(0,0,2)
				.scale(scale)
				.render(graphics);

		matrixStack.popPose();

	}
}
