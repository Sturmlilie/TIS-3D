package li.cil.tis3d.client.network.handler;

import li.cil.tis3d.common.block.entity.CasingBlockEntity;
import li.cil.tis3d.common.network.handler.AbstractMessageHandlerWithLocation;
import li.cil.tis3d.common.network.message.CasingEnabledStateMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;

@Environment(EnvType.CLIENT)
public final class CasingEnabledStateMessageHandler extends AbstractMessageHandlerWithLocation<CasingEnabledStateMessage> {
    @Override
    protected void onMessageSynchronized(final CasingEnabledStateMessage message, final PacketContext context) {
        final BlockEntity blockEntity = getBlockEntity(message, context);
        if (!(blockEntity instanceof CasingBlockEntity)) {
            return;
        }

        final CasingBlockEntity casing = (CasingBlockEntity)blockEntity;
        casing.setEnabledClient(message.isEnabled());
    }
}
