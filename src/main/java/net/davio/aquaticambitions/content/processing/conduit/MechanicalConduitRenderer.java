package net.davio.aquaticambitions.content.processing.conduit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.davio.aquaticambitions.registry.CAAPartials;
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
        float eyeAngle = AngleHelper.rad(be.eyeAngle.getValue(partialTicks));
        int hashCode = be.hashCode();

        renderShared(ms, null, bufferSource,
                level, blockState, conduitLevel, animation, eyeAngle, hashCode);
    }

    private void renderShared(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
                              Level level, BlockState blockState, ConduitPowerLevel conduitLevel, float animation, float playerTrackAngle, int hashCode) {

        float time = AnimationTickHolder.getRenderTime(level);
        float renderTick = time + (hashCode % 13) * 16f;
        float offsetMult = conduitLevel.isAwakened() ? 64 : 16;
        float offset = Mth.sin((float) ((renderTick / 16f) % (2 * Math.PI))) / offsetMult;
        float eyeY = offset - (animation * .75f) + (conduitLevel.isAwakened() ? 0.2f : 0);
        float rotateYZ = Mth.DEG_TO_RAD * (time * 2f) % 360;
        float rotateX = Mth.DEG_TO_RAD * 30 * (Mth.sin(((time * 2f) % 360) * Mth.DEG_TO_RAD));

        VertexConsumer solid = bufferSource.getBuffer(RenderType.solid());
        VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());

        ms.pushPose();

        var eyeModel = CAAPartials.CONDUIT_EYE;
        var cageModel = CAAPartials.CONDUIT_CAGE;
        var inactiveModel = CAAPartials.INACTIVE_CONDUIT;

        if (eyeModel == null) {
            ms.popPose();
            return;
        }

        SuperByteBuffer eyeBuffer = CachedBuffers.partial(eyeModel, blockState);
        if (modelTransform != null)
            eyeBuffer.transform(modelTransform);
        eyeBuffer.translate(0, eyeY, 0);
        draw(eyeBuffer, playerTrackAngle, ms, bufferSource.getBuffer(RenderType.cutout()));

        if (conduitLevel.isAwakened()) {
            if (cageModel == null) {
                ms.popPose();
                return;
            }

            SuperByteBuffer cageBuffer = CachedBuffers.partial(cageModel, blockState);
            if (modelTransform != null)
                cageBuffer.transform(modelTransform);
            cageBuffer
                    .translate(0, eyeY, 0)
                    .rotateXCentered(rotateX)
                    .rotateZCentered(rotateYZ);
            draw(cageBuffer, rotateYZ, ms, bufferSource.getBuffer(RenderType.cutout()));

        } else {
            if (inactiveModel == null) {
                ms.popPose();
                return;
            }
            SuperByteBuffer inactiveConduitBuffer = CachedBuffers.partial(inactiveModel, blockState);
            if (modelTransform != null)
                inactiveConduitBuffer.transform(modelTransform);
            inactiveConduitBuffer.translate(0, eyeY, 0);
            draw(inactiveConduitBuffer, playerTrackAngle, ms, bufferSource.getBuffer(RenderType.cutout()));
        }

        ms.popPose();
    }

    private static void draw(SuperByteBuffer buffer, float horizontalAngle, PoseStack ms, VertexConsumer vc) {
        buffer.rotateCentered(horizontalAngle, Direction.UP)
                .light(LightTexture.FULL_BLOCK)
                .renderInto(ms, vc);
    };
}
