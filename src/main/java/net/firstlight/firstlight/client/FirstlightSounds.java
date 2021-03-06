package net.firstlight.firstlight.client;

import net.firstlight.firstlight.Firstlight;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class FirstlightSounds {
    private static SoundEvent create(String name) {
        ResourceLocation location = new ResourceLocation(Firstlight.MODID, name);
        return new SoundEvent(location).setRegistryName(location);
    }

    public static final SoundEvent FRENZY_RELOAD = create("frenzy_reload");
    public static final SoundEvent REAPER_RELOAD = create("reaper_reload");

    public static final SoundEvent FRENZY_FIRE = create("frenzy_fire");
    public static final SoundEvent REAPER_FIRE = create("reaper_fire");
}