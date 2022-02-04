package net.firstlight.flarmoury.item;

import net.firstlight.flarmoury.item.weapon.frenzy.FlaFrenzyItem;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("flarmoury")
public class FlaItems {
    @GameRegistry.ObjectHolder("rfp3_frenzy")
    public static final FlaFrenzyItem RFP3_FRENZY = new FlaFrenzyItem();
}
