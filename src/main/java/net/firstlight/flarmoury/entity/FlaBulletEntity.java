package net.firstlight.flarmoury.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class FlaBulletEntity extends EntityArrow {
    private float damage;

    public FlaBulletEntity(World worldIn, EntityLivingBase shooter, float damage) {
        super(worldIn, shooter);

        this.damage = damage;

        this.setNoGravity(true);
    }

    public FlaBulletEntity(World world) {
        super(world);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        Entity entity = result.entityHit;

        if (entity instanceof EntityLivingBase) {
            DamageSource source = new EntityDamageSource("bullet", this.shootingEntity).setProjectile().setDamageIsAbsolute();
            EntityLivingBase living = (EntityLivingBase) entity;
            living.attackEntityFrom(source, this.damage);
            entity.hurtResistantTime = 0;
            this.setDead();
        }
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > 20) {
            this.world.removeEntity(this);
        }
    }

    @Override
    protected void doBlockCollisions() {
        super.doBlockCollisions();
        this.world.removeEntity(this);
    }
}
