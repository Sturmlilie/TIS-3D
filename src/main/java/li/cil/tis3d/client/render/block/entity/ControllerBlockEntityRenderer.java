package li.cil.tis3d.client.render.block.entity;

import li.cil.tis3d.common.block.entity.ControllerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public final class ControllerBlockEntityRenderer extends BlockEntityRenderer<ControllerBlockEntity> {
    public ControllerBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    private void renderState(final ControllerBlockEntity.ControllerState state, MatrixStack matrices, VertexConsumerProvider vc) {
        final Camera camera = this.dispatcher.camera;
        // I would add a check for distance to camera here, but this seems to happen automatically (?)
        String str = I18n.translate(state.translateKey);
        matrices.push();
        matrices.translate(0.5D, 1.4D, 0.5D);
        matrices.multiply(camera.getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);

        final Matrix4f modMat = matrices.peek().getModel();
        final int bg = MinecraftClient.getInstance().options.getTextBackgroundColor(0.25F);
        final TextRenderer textRenderer = dispatcher.getTextRenderer();
        float x = -textRenderer.getStringWidth(str) / 2.0F;
        textRenderer.draw(str, x, 0, 553648127, false, modMat, vc, true, bg, 15728880);
        textRenderer.draw(str, x, 0, -1, false, modMat, vc, false, 0, 15728880);

        matrices.pop();
    }

    @Override
    public void render(final ControllerBlockEntity controller, float partialTicks, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final ControllerBlockEntity.ControllerState state = controller.getState();
        if (!state.isError) {
            return;
        }

        final HitResult hitResult = dispatcher.crosshairTarget;
        if (hitResult == null) {
            return;
        }
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        final BlockHitResult blockHitResult = (BlockHitResult)hitResult;
        if (blockHitResult.getBlockPos() == null) {
            return;
        }
        if (!blockHitResult.getBlockPos().equals(controller.getPos())) {
            return;
        }

        //~ disableLightmap(true);
        renderState(state, matrices, vertexConsumers);
        //~ disableLightmap(false);
    }
}
