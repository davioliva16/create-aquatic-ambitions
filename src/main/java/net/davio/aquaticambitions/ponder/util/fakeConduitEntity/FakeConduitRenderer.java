package net.davio.aquaticambitions.ponder.util.fakeConduitEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.createmod.catnip.animation.AnimationTickHolder;
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
import org.jetbrains.annotations.Nullable;

public class FakeConduitRenderer extends SafeBlockEntityRenderer<FakeActiveConduitBlockEntity> {

    //The ENTIRE point of this block is being able to render a conduit in ponder scenes.
    //It will not be available anywhere but with commands

    public FakeConduitRenderer(BlockEntityRendererProvider.Context context) {};

    @Override
    protected void renderSafe(FakeActiveConduitBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        Level level = be.getLevel();
        BlockState blockState = be.getBlockState();
        int hashCode = be.hashCode();

        renderShared(ms, null, bufferSource,
                level, blockState, hashCode);
    }

    private void renderShared(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
        Level level, BlockState blockState, int hashCode) {

        float time = AnimationTickHolder.getRenderTime(level);
        float eyeY = Mth.DEG_TO_RAD*15*(Mth.sin(((time * 4f) % 360)*Mth.DEG_TO_RAD));
        float rotateYZ = Mth.DEG_TO_RAD*(time * 2f) % 360;
        float rotateX = Mth.DEG_TO_RAD*30*(Mth.sin(((time * 2f) % 360)*Mth.DEG_TO_RAD));

        VertexConsumer solid = bufferSource.getBuffer(RenderType.solid());
        VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());

        ms.pushPose();

        SuperByteBuffer headBuffer = CachedBuffers.partial(CAAPartials.CONDUIT_EYE, blockState);
        if (modelTransform != null)
            headBuffer.transform(modelTransform);
        headBuffer.translate(0, eyeY, 0);
        draw(headBuffer, 45, ms, bufferSource.getBuffer(RenderType.cutout()));

        SuperByteBuffer cageBuffer = CachedBuffers.partial(CAAPartials.CONDUIT_CAGE, blockState);
        if (modelTransform != null)
            cageBuffer.transform(modelTransform);
        cageBuffer
                .translate(0, eyeY, 0)
                .rotateXCentered(rotateX)
                .rotateZCentered(rotateYZ);
        draw(cageBuffer, rotateYZ, ms, bufferSource.getBuffer(RenderType.cutout()));

        SuperByteBuffer windBuffer = CachedBuffers.partial(CAAPartials.CONDUIT_WIND, blockState);
        if (modelTransform != null)
            windBuffer.transform(modelTransform);
        windBuffer
        .translate(0, 0, 0);
        draw(windBuffer, 0, ms, bufferSource.getBuffer(RenderType.cutout()));

        ms.popPose();
    }

    private static void draw(SuperByteBuffer buffer, float horizontalAngle, PoseStack ms, VertexConsumer vc) {
        buffer.rotateCentered(horizontalAngle, Direction.UP)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(ms, vc);
    };
}
