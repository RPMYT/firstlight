package net.firstlight.planut;

import net.firstlight.firstlight.Firstlight;
import net.firstlight.firstlight.Resource;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedPlayerEntityModel extends AnimatedGeoModel<AnimatedPlayerEntity> {
	@Override
	public ResourceLocation getAnimationFileLocation(AnimatedPlayerEntity animatable) {
		return Firstlight.getResource("firstlight_player.animation.json", Resource.Type.ANIMATION);
	}

	@Override
	public ResourceLocation getModelLocation(AnimatedPlayerEntity animatable) {
		return Firstlight.getResource("firstlight_player.geo.json", Resource.Type.GECKO_MODEL);
	}

	@Override
	public ResourceLocation getTextureLocation(AnimatedPlayerEntity animatable) {
		return new ResourceLocation("minecraft", "textures/entity/steve.png");
	}
}