package net.firstlight.flarmoury.item.weapon.frenzy;

import net.firstlight.flarmoury.FLArmoury;
import net.firstlight.flarmoury.client.FlaSounds;
import net.firstlight.flarmoury.item.weapon.FlaGunItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class FlaFrenzyItem extends FlaGunItem implements IAnimatable {
    public FlaFrenzyItem() {
        super(new ResourceLocation(FLArmoury.MODID, "rfp3_frenzy"), 4, 20, 1F, true, false, FlaSounds.FRENZY_FIRE);
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
    public String fireAnimationName() {
        return "frenzy_fire";
    }
}
