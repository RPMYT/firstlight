package net.firstlight.firstlight.item.frenzy;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FrenzyGunModel extends AnimatedGeoModel<FrenzyGunItem> {
    @Override
    public ResourceLocation getModelLocation(FrenzyGunItem object) {
        return new ResourceLocation("firstlight", "geo/rfp3_frenzy.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FrenzyGunItem object) {
        return new ResourceLocation("firstlight", "textures/item/rfp3_frenzy.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FrenzyGunItem animatable) {
        return new ResourceLocation("firstlight", "animations/rfp3_frenzy.animation.json");
    }
}
