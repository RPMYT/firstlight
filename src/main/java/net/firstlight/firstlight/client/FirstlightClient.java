package net.firstlight.firstlight.client;

import net.firstlight.firstlight.Firstlight;
import net.firstlight.firstlight.item.FirstlightArmouryItems;
import net.firstlight.firstlight.item.Gun;
import net.firstlight.firstlight.item.frenzy.FrenzyGunRenderer;
import net.firstlight.firstlight.network.ShootPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Firstlight.MODID, value = Side.CLIENT)
public class FirstlightClient {
    @SubscribeEvent
    public static void onPostClientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (event.phase != TickEvent.Phase.END || !minecraft.inGameHasFocus) {
            return;
        }

        EntityPlayer player = minecraft.player;
        if (player != null) {
            ItemStack stackMain = player.getHeldItemMainhand();
            ItemStack stackOff = player.getHeldItemOffhand();
            Item itemMain = stackMain.getItem();
            Item itemOff = stackOff.getItem();

            CooldownTracker tracker = player.getCooldownTracker();

            if (itemMain instanceof Gun && minecraft.gameSettings.keyBindAttack.isKeyDown()) {
                if (itemOff instanceof Gun && ((Gun) itemMain).canDualWield() && ((Gun) itemOff).canDualWield() && minecraft.gameSettings.keyBindUseItem.isKeyDown()) {
                    if (!tracker.hasCooldown(itemOff) && !tracker.hasCooldown(itemMain)) {
                        Firstlight.NETWRAPPER.sendToServer(new ShootPacket(EnumHand.OFF_HAND));
                    }
                }

                if (itemOff != Items.AIR && ((Gun) itemMain).isTwoHanded()) {
                    return;
                }

                if (!Firstlight.GCT.hasCooldown(itemMain)) {
                      Firstlight.NETWRAPPER.sendToServer(new ShootPacket(EnumHand.MAIN_HAND));
                }
            }
        }
    }

    @SubscribeEvent
    public void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(FirstlightSounds.FRENZY_FIRE);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(FirstlightArmouryItems.RFP3_FRENZY, 0, new ModelResourceLocation(Firstlight.MODID + ":rfp3_frenzy", "inventory"));

        FirstlightArmouryItems.RFP3_FRENZY.setTileEntityItemStackRenderer(new FrenzyGunRenderer());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderFirstPersonGun(RenderPlayerEvent.Pre event) {
        EntityPlayer entity = event.getEntityPlayer();

        ItemStack stackOff = entity.getHeldItemOffhand();
        ItemStack stackMain = entity.getHeldItemMainhand();
        Item itemOff = stackOff.getItem();
        Item itemMain = stackMain.getItem();

        if (itemMain instanceof Gun) {
            if (itemOff == Items.AIR) {
                event.setCanceled(true);

            }
        }
    }
}