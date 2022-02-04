package net.firstlight.flarmoury.network;

import io.netty.buffer.ByteBuf;
import net.firstlight.flarmoury.item.weapon.FlaGun;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ShootPacket implements IMessage {
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

    public static class Handler implements IMessageHandler<ShootPacket, IMessage> {
        @Override
        public IMessage onMessage(ShootPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                ItemStack stack = player.getHeldItem(message.hand);
                if (stack.getItem() instanceof FlaGun) {
                    ((FlaGun) stack.getItem()).fire(player, stack);
                }
            });
            return null;
        }
    }
}
