package net.firstlight.firstlight.client.model;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.firstlight.firstlight.Firstlight;
import net.firstlight.firstlight.Resource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class FirstlightPlayerModel extends GeoModelProvider<EntityPlayer> {
    @Override
    public ResourceLocation getModelLocation(EntityPlayer object) {
        return Firstlight.getResource("firstlight_player_model", Resource.Type.GECKO_MODEL);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPlayer object) {
        SkinManager manager = Minecraft.getMinecraft().getSkinManager();
        GameProfile profile = object.getGameProfile();
        manager.loadProfileTextures(profile, null, false);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures = manager.loadSkinFromCache(profile);
        return manager.loadSkin(textures.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
    }
}