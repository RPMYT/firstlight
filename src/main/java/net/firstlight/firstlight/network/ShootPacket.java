package net.firstlight.firstlight.network;

import io.netty.buffer.ByteBuf;
import net.firstlight.firstlight.item.Gun;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class ShootPacket implements IMessage {
    private EnumHand hand;

    public ShootPacket() {
    }

    public ShootPacket(EnumHand hand) {
        this.hand = hand;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            this.hand = EnumHand.values()[buf.readByte()];
        } catch (ArrayIndexOutOfBoundsException exception) {
            this.hand = EnumHand.MAIN_HAND;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.hand.ordinal());
    }

    public static final class Handler implements IMessageHandler<ShootPacket, IMessage> {
        @Override
        public IMessage onMessage(ShootPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                ItemStack stack = player.getHeldItem(message.hand);
                Item item = stack.getItem();
                if (item instanceof Gun) {
                    ((Gun) item).fire(player, stack);
                    if (message.hand == EnumHand.OFF_HAND && player.getHeldItemMainhand().getItem() == item) {
                        player.getCooldownTracker().removeCooldown(item);
                        ((Gun) item).fire(player, stack);
                    }
                }
            });
            return null;
        }
    }
}
