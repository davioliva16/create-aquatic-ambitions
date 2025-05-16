package net.davio.aquaticambitions.content.processing.conduit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.davio.aquaticambitions.registry.CCAPartials;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock.ConduitPowerLevel;
import org.jetbrains.annotations.Nullable;

public class MechanicalConduitRenderer extends SafeBlockEntityRenderer<MechanicalConduitBlockEntity> {

    public MechanicalConduitRenderer(BlockEntityRendererProvider.Context context) {};

    @Override
    protected void renderSafe(MechanicalConduitBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        ConduitPowerLevel conduitLevel = be.getConduitLevelFromBlock();

        Level level = be.getLevel();
        BlockState blockState = be.getBlockState();
        float animation = be.eyeAnimation.getValue(partialTicks) * .175f;
        float horizontalAngle = AngleHelper.rad(be.eyeAngle.getValue(partialTicks));
        int hashCode = be.hashCode();

        renderShared(ms, null, bufferSource,
                level, blockState, conduitLevel, animation, horizontalAngle, hashCode);
    }

    private void renderShared(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
        Level level, BlockState blockState, ConduitPowerLevel conduitLevel, float animation, float horizontalAngle, int hashCode) {

        float time = AnimationTickHolder.getRenderTime(level);
        float renderTick = time + (hashCode % 13) * 16f;
        float offsetMult = conduitLevel.isAwakened() ? 64 : 16;
        float offset = Mth.sin((float) ((renderTick / 16f) % (2 * Math.PI))) / offsetMult;
        float eyeY = offset - (animation * .75f);

        VertexConsumer solid = bufferSource.getBuffer(RenderType.solid());
        VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());

        ms.pushPose();

        SuperByteBuffer headBuffer = CachedBuffers.partial(CCAPartials.CONDUIT_EYE, blockState);
        if (modelTransform != null)
            headBuffer.transform(modelTransform);
        headBuffer.translate(0, eyeY, 0);
        draw(headBuffer, horizontalAngle, ms, bufferSource.getBuffer(RenderType.cutout()));

        if (conduitLevel.isAwakened()) {

            SuperByteBuffer cageBuffer = CachedBuffers.partial(CCAPartials.CONDUIT_CAGE, blockState);
            if (modelTransform != null)
                cageBuffer.transform(modelTransform);
            cageBuffer.translate(0, eyeY, 0);
            draw(cageBuffer, horizontalAngle, ms, bufferSource.getBuffer(RenderType.cutout()));

        } else {

            SuperByteBuffer inactiveConduitBuffer = CachedBuffers.partial(CCAPartials.INACTIVE_CONDUIT, blockState);
            if (modelTransform != null)
                inactiveConduitBuffer.transform(modelTransform);
            inactiveConduitBuffer.translate(0, eyeY, 0);
            draw(inactiveConduitBuffer, horizontalAngle, ms, bufferSource.getBuffer(RenderType.cutout()));
        }

        ms.popPose();

    }

    private static void draw(SuperByteBuffer buffer, float horizontalAngle, PoseStack ms, VertexConsumer vc) {
        buffer.rotateCentered(horizontalAngle, Direction.UP)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(ms, vc);
    };
}
