package net.firstlight.firstlight.item.frenzy;

import net.firstlight.firstlight.client.FirstlightSounds;
import net.firstlight.firstlight.item.GunItem;

public class FrenzyGunItem extends GunItem {
    public FrenzyGunItem() {
        super("rfp3_frenzy",4, 20, 1F, true, false, FirstlightSounds.FRENZY_FIRE, FirstlightSounds.FRENZY_RELOAD);
    }
}
