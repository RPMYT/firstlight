package net.firstlight.firstlight.core;

import net.firstlight.firstlight.Firstlight;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

//import java.util.jar.JarEntry;
//import java.util.jar.JarFile;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import org.spongepowered.asm.mixin.transformer.ClassInfo;
//import org.spongepowered.asm.service.MixinService;
//import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;
//import net.minecraft.launchwrapper.Launch;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("FirstlightMixinLoader")
public class FirstlightMixinLoader implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    @Nullable
    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
        // try {
        //     String path = Launch.minecraftHome.getAbsolutePath() + "/mods/" + Firstlight.GECKOLIB_FILENAME;
        //     JarFile mod = new JarFile(path);
        //     Enumeration<JarEntry> entries = mod.entries();
        //
        //     ArrayList<ClassLoader> loaders = new ArrayList<>();
        //     loaders.add(this.getClass().getClassLoader());
        //     loaders.add(MixinBootstrap.class.getClassLoader());
        //
        //     while (entries.hasMoreElements()) {
        //         JarEntry entry = entries.nextElement();
        //         if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
        //             continue;
        //         }
        //         String name = entry.getName().substring(0, entry.getName().length() - 6).replace("/", ".");
        //
        //         if (name.equals("software.bernie.geckolib3.file.GeoModelLoader")) {
        //             InputStream stream = mod.getInputStream(entry);
        //             byte[] bytes = new byte[(int) entry.getSize()];
        //             //noinspection ResultOfMethodCallIgnored
        //             stream.read(bytes);
        //             Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
        //             method.setAccessible(true);
        //             loaders.forEach(loader -> {
        //                 Class defined = null;
        //                 try {
        //                     defined = (Class) method.invoke(loader, name, bytes, 0, bytes.length);
        //                 } catch (IllegalAccessException exception) {
        //                     exception.printStackTrace();
        //                 } catch (InvocationTargetException ignored) {}
        //                 System.out.println(defined);
        //             });
        //         }
        //     }
        // } catch (IOException | NoSuchMethodException exception) {
        //     exception.printStackTrace();
        // }

        MixinBootstrap.init();
        Mixins.addConfiguration(Firstlight.MODID + ".mixins.json");
        if (!FMLLaunchHandler.isDeobfuscatedEnvironment()) {
            MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
