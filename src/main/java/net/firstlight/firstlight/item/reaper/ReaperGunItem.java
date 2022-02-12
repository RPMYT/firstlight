package net.firstlight.firstlight.item.reaper;

import net.firstlight.firstlight.client.FirstlightSounds;
import net.firstlight.firstlight.item.GunItem;
public class ReaperGunItem extends GunItem {
    public ReaperGunItem() {
        super("ar1_reaper", 1, 1, 0.5F, false, true, FirstlightSounds.REAPER_FIRE, FirstlightSounds.REAPER_RELOAD);
    }
}