package net.firstlight.firstlight.mixin;

import net.firstlight.firstlight.Firstlight;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import software.bernie.geckolib3.file.AnimationFileLoader;
import software.bernie.geckolib3.file.GeoModelLoader;

import java.io.File;
import java.io.IOException;

public class ExternalResourceEnablementMixins {
    @Mixin(GeoModelLoader.class)
    public static class EREM_GeoModelLoader {
        @Redirect(at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib3/file/AnimationFileLoader;getResourceAsString(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/resources/IResourceManager;)Ljava/lang/String;"), method = "loadModel", remap = false)
        private String sledgehammerForANail(ResourceLocation location, IResourceManager manager) throws IOException {
            if (location.getResourceDomain().toLowerCase().contains(Firstlight.NAME.toLowerCase())) {
                File resource = Firstlight.getExternalResource(location.getResourcePath());
                if (resource.exists()) {
                    return Firstlight.getExternalResourceContent(resource);
                } else {
                    return null;
                }
            }
            return AnimationFileLoader.getResourceAsString(location, manager);
        }
    }
}
