package net.firstlight.flarmoury.item.weapon.frenzy;

import net.firstlight.flarmoury.FLArmoury;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlaFrenzyModel extends AnimatedGeoModel<FlaFrenzyItem> {
    @Override
    public ResourceLocation getModelLocation(FlaFrenzyItem object) {
        return new ResourceLocation(FLArmoury.MODID, "geo/rfp3_frenzy.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FlaFrenzyItem object) {
        return new ResourceLocation(FLArmoury.MODID, "textures/item/rfp3_frenzy.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FlaFrenzyItem animatable) {
        return new ResourceLocation(FLArmoury.MODID, "animations/rfp3_frenzy.animation.json");
    }
}
