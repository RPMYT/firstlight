package net.firstlight.firstlight.item;

import net.firstlight.firstlight.item.frenzy.FrenzyGunItem;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("firstlight")
public class FirstlightItems {
    @GameRegistry.ObjectHolder("rfp3_frenzy")
    public static final FrenzyGunItem RFP3_FRENZY = new FrenzyGunItem();
}
