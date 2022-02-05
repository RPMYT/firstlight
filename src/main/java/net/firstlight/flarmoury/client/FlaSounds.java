package net.firstlight.flarmoury.client;

import net.firstlight.flarmoury.FLArmoury;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class FlaSounds {
    private static SoundEvent create(String name) {
        ResourceLocation location = new ResourceLocation(FLArmoury.MODID, name);
        return new SoundEvent(location).setRegistryName(location);
    }

    public static final SoundEvent FRENZY_FIRE = create("frenzy_fire");
}
