package net.firstlight.firstlight.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface Gun {
    void fire(EntityLivingBase entity, ItemStack stack);
    boolean isTwoHanded();
    boolean canDualWield();
    String getName();
}
