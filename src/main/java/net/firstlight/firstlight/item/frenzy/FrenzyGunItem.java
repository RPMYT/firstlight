package net.firstlight.firstlight.item.frenzy;

import net.firstlight.firstlight.Firstlight;
import net.firstlight.firstlight.client.FirstlightSounds;
import net.firstlight.firstlight.item.GunItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class FrenzyGunItem extends GunItem implements IAnimatable {
    public FrenzyGunItem() {
        super(new ResourceLocation(Firstlight.MODID, "rfp3_frenzy"), 4, 20, 1F, true, false, FirstlightSounds.FRENZY_FIRE);
    }

    @Override
    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public String getName() {
        return "rfp3_frenzy";
    }
}
