package net.firstlight.flarmoury.item.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface FlaGun {
    void fire(EntityLivingBase entity, ItemStack stack);
    boolean isTwoHanded();
    boolean canDualWield();
    String getName();
}
