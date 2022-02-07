package net.firstlight.firstlight.network;

import io.netty.buffer.ByteBuf;
import net.firstlight.firstlight.item.GunItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.util.GeckoLibUtil;

public final class AnimationPacket implements IMessage {
    private int animation;
    private ItemStack stack;

    public AnimationPacket() {
    }

    public AnimationPacket(ItemStack stack, int animation) {
        this.stack = stack;
        this.animation = animation;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
        this.animation = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.stack);
        buf.writeInt(this.animation);
    }

    @SuppressWarnings("rawtypes")
    public static final class Handler implements IMessageHandler<AnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(AnimationPacket message, MessageContext ctx) {
            ItemStack stack = message.stack;
            Item item = stack.getItem();
            if (item instanceof GunItem) {
                AnimationController controller = GeckoLibUtil.getControllerForStack(((GunItem) item).factory, stack, ((GunItem) item).getName());

                switch (message.animation) {
                    case 0:
                        controller.setAnimation(GunItem.SHOOTING_ANIMATION);
                        break;
                    case 1:
                        controller.setAnimation(GunItem.RELOADING_ANIMATION);
                        break;
                    case 2:
                    default:
                        controller.setAnimation(GunItem.IDLE_ANIMATION);
                        break;
                }

                controller.markNeedsReload();
            }

            return null;
        }
    }
}
