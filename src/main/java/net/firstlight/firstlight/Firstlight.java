package net.firstlight.firstlight;

import net.firstlight.firstlight.client.model.FirstlightPlayerModel;
import net.firstlight.firstlight.client.renderer.FirstlightPlayerRenderer;
import net.firstlight.firstlight.entity.BulletEntity;
import net.firstlight.firstlight.item.FirstlightArmouryItems;
import net.firstlight.firstlight.network.AnimationPacket;
import net.firstlight.firstlight.network.ShootPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import software.bernie.geckolib3.GeckoLib;

@Mod.EventBusSubscriber(modid = Firstlight.MODID)
@Mod(modid = Firstlight.MODID, name = Firstlight.NAME, version = Firstlight.VERSION, dependencies = "required-after:geckolib3")
public class Firstlight {
    public static final CooldownTracker GCT = new CooldownTracker();
    public static final String VERSION = "1.0";
    public static final String NAME = "Firstlight";
    public static final String MODID = "firstlight";
    public static final SimpleNetworkWrapper NETWRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    public Firstlight() {
        GeckoLib.initialize();
    }

    public static ResourceLocation getResource(String name, Resource type) {
        return new ResourceLocation(Firstlight.MODID, type.getPrefix() + name);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Firstlight.NETWRAPPER.registerMessage(ShootPacket.Handler.class, ShootPacket.class, 0, Side.SERVER);
        Firstlight.NETWRAPPER.registerMessage(AnimationPacket.Handler.class, AnimationPacket.class, 1, Side.CLIENT);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(Firstlight.MODID, "bullet"), BulletEntity.class, "firstlight.bullet", 0, this, 80, 64, false);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(FirstlightArmouryItems.RFP3_FRENZY);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overwritePlayerModel(RenderPlayerEvent.Pre event) {
        event.setCanceled(true);
        EntityPlayer player = event.getEntityPlayer();
        FirstlightPlayerRenderer renderer = new FirstlightPlayerRenderer();
        FirstlightPlayerModel model = (FirstlightPlayerModel) renderer.getGeoModelProvider();
        renderer.render(model.getModel(model.getModelLocation(player)), player, 0, 1F, 1F, 1F, 1F);
    }
}
