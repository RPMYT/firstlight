package net.firstlight.flarmoury.item.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.core.controller.AnimationController;

public interface FlaGun {
    void fire(EntityLivingBase entity, ItemStack stack);
    boolean isTwoHanded();
    boolean canDualWield();
    String fireAnimationName();
}
