package net.firstlight.firstlight.item;

import net.firstlight.firstlight.item.frenzy.FrenzyGunItem;
import net.firstlight.firstlight.item.reaper.ReaperGunItem;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("firstlight")
public class FirstlightItems {
    @GameRegistry.ObjectHolder("rfp3_frenzy")
    public static final FrenzyGunItem RFP3_FRENZY = new FrenzyGunItem();

    @GameRegistry.ObjectHolder("ar1_reaper")
    public static final ReaperGunItem AR1_REAPER = new ReaperGunItem();
}
