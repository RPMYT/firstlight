package net.firstlight.flarmoury.item.weapon;

import net.firstlight.flarmoury.FLArmoury;

import net.firstlight.flarmoury.client.FlaSounds;
import net.firstlight.flarmoury.entity.FlaBulletEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

@SuppressWarnings("NullableProblems")
public abstract class FlaGunItem extends Item implements FlaGun, IAnimatable {
    private final int shots;
    private final int fireRate;
    private final float damage;
    private final boolean isTwoHanded;
    private final boolean canDuelWield;
    private final SoundEvent fireSound;

    public AnimationFactory factory = new AnimationFactory(this);

    public FlaGunItem(ResourceLocation name, int shots, int fireRate, float damage, boolean canDuelWield, boolean isTwoHanded, SoundEvent fireSound) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name.getResourcePath());

        this.shots = shots;
        this.damage = damage;
        this.fireRate = fireRate;
        this.fireSound = fireSound;
        this.isTwoHanded = isTwoHanded;
        this.canDuelWield = canDuelWield;

    }

    @Override
    public boolean onEntitySwing(EntityLivingBase p_onEntitySwing_1_, ItemStack p_onEntitySwing_2_) {
        return true;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void fire(EntityLivingBase entity, ItemStack stack) {
        World world = entity.getEntityWorld();

        if (!world.isRemote) {
            CooldownTracker tracker = (entity instanceof EntityPlayer ? ((EntityPlayer) entity).getCooldownTracker() : FLArmoury.GCT);
            if (!tracker.hasCooldown(this)) {
                int fired;
                for (fired = 1; fired <= this.shots; fired++) {
                    FlaBulletEntity bullet = new FlaBulletEntity(world, entity, this.damage);
                    bullet.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, 10.0F, 0.0F);
                    world.spawnEntity(bullet);
                }
                entity.getEntityWorld().playSound((entity instanceof EntityPlayer ? (EntityPlayer) entity : null), entity.getPosition(), this.fireSound,
                        (entity instanceof EntityPlayer ? SoundCategory.PLAYERS : SoundCategory.HOSTILE), 1.0F, 1.0F);
                tracker.setCooldown(this, this.fireRate);
                AnimationController controller = GeckoLibUtil.getControllerForStack(this.factory, stack, this.fireAnimationName());
                if (controller.getAnimationState() == AnimationState.Stopped) {
                    controller.setAnimation(new AnimationBuilder().addAnimation("Shooting", false));
                    controller.markNeedsReload();
                }
            }
        }
    }

    @Override
    public boolean isTwoHanded() {
        return this.isTwoHanded;
    }

    @Override
    public boolean canDualWield() {
        return this.canDuelWield;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        CooldownTracker tracker = FLArmoury.GCT;
        if (entityIn instanceof EntityPlayer) {
            return;
        }
        int ticks = (int) tracker.getCooldown(this, 0.0F);
        if (ticks > 0) {
            tracker.setCooldown(this, --ticks);
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<FlaGunItem> controller = new AnimationController<>(this, this.fireAnimationName(), 20, (event -> PlayState.CONTINUE));
        data.addAnimationController(controller);
    }
}