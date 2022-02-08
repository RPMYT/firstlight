package net.firstlight.firstlight.client.model;

import com.google.common.io.CharStreams;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.firstlight.firstlight.Firstlight;
import net.firstlight.firstlight.Resource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class FirstlightPlayerModel extends GeoModelProvider<EntityPlayer> {
    @Override
    public ResourceLocation getModelLocation(EntityPlayer object) {
        ResourceLocation location = Firstlight.getResource("firstlight_player_model.geo.json", Resource.Type.GECKO_MODEL);
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        Set<String> domains = manager.getResourceDomains();
        domains.forEach(System.out::println);
        try {
            IResource resource = manager.getResource(location);
            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
            CharBuffer buffer = CharBuffer.allocate((2^16)-1);
            //noinspection ResultOfMethodCallIgnored
            reader.read(buffer);
            @SuppressWarnings("UnstableApiUsage") String str = CharStreams.toString(reader);
            System.out.println(str);
        } catch (IOException exception) {
            exception.printStackTrace();
            FMLCommonHandler.instance().exitJava(0, true);
        }
        return location;
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