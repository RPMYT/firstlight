package net.firstlight.firstlight.client.renderer;

import net.firstlight.firstlight.client.model.FirstlightPlayerModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class FirstlightPlayerRenderer implements IGeoRenderer<EntityPlayer> {
    @Override
    public GeoModelProvider<EntityPlayer> getGeoModelProvider() {
        return new FirstlightPlayerModel();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPlayer instance) {
        return null;
    }
}
