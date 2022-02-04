package net.firstlight.flarmoury;

import net.firstlight.flarmoury.entity.FlaBulletEntity;
import net.firstlight.flarmoury.item.FlaItems;
import net.firstlight.flarmoury.network.ShootPacket;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod.EventBusSubscriber(modid = FLArmoury.MODID)
@Mod(modid = FLArmoury.MODID, name = FLArmoury.NAME, version = FLArmoury.VERSION, dependencies = "required-after:geckolib3")
public class FLArmoury {
    public static final String MODID = "flarmoury";
    public static final String NAME = "FL Armoury";
    public static final String VERSION = "1.0";

    public static final CooldownTracker GCT = new CooldownTracker();

    public static final SimpleNetworkWrapper NETWRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    private static Logger LOGGER;

    public FLArmoury() {
        GeckoLib.initialize();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        NETWRAPPER.registerMessage(ShootPacket.Handler.class, ShootPacket.class, 0, Side.SERVER);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(FLArmoury.MODID, "bullet"), FlaBulletEntity.class, "flarmoury.bullet", 0, this, 80, 64, false);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(FlaItems.RFP3_FRENZY);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
