package net.firstlight.flarmoury.client;

import net.firstlight.flarmoury.FLArmoury;
import net.firstlight.flarmoury.item.FlaItems;
import net.firstlight.flarmoury.item.weapon.FlaGun;
import net.firstlight.flarmoury.item.weapon.frenzy.FlaFrenzyRenderer;
import net.firstlight.flarmoury.network.ShootPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
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

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = FLArmoury.MODID, value = Side.CLIENT)
public class FLArmouryClient {
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

            if (itemMain instanceof FlaGun && minecraft.gameSettings.keyBindAttack.isKeyDown()) {
                if (itemOff instanceof FlaGun && ((FlaGun) itemMain).canDualWield() && ((FlaGun) itemOff).canDualWield() && minecraft.gameSettings.keyBindUseItem.isKeyDown()) {
                    if (!tracker.hasCooldown(itemOff) && !tracker.hasCooldown(itemMain)) {
                        FLArmoury.NETWRAPPER.sendToServer(new ShootPacket(EnumHand.OFF_HAND));
                    }
                }

                if (itemOff != Items.AIR && ((FlaGun) itemMain).isTwoHanded()) {
                    return;
                }

                if (!FLArmoury.GCT.hasCooldown(itemMain)) {
                      FLArmoury.NETWRAPPER.sendToServer(new ShootPacket(EnumHand.MAIN_HAND));
                }
            }
        }
    }

    @SubscribeEvent
    public void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(FlaSounds.FRENZY_FIRE);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(FlaItems.RFP3_FRENZY, 0, new ModelResourceLocation(FLArmoury.MODID + ":rfp3_frenzy", "inventory"));

        FlaItems.RFP3_FRENZY.setTileEntityItemStackRenderer(new FlaFrenzyRenderer());
    }
}