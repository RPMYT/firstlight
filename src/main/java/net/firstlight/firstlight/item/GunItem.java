package net.firstlight.firstlight.item;

import net.firstlight.firstlight.Firstlight;
import net.firstlight.firstlight.entity.BulletEntity;
import net.firstlight.firstlight.network.AnimationPacket;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

@SuppressWarnings({"NullableProblems", "rawtypes"})
public class GunItem extends Item implements Gun, IAnimatable {
    public static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("Idle");
    public static final AnimationBuilder SHOOTING_ANIMATION = new AnimationBuilder().addAnimation("Shooting");
    public static final AnimationBuilder RELOADING_ANIMATION = new AnimationBuilder().addAnimation("Reloading");

    private final int shots;
    private final String name;
    private final int fireRate;
    private final float damage;
    private final boolean isTwoHanded;
    private final boolean canDuelWield;
    private final SoundEvent fireSound;
    private final SoundEvent reloadSound;

    public AnimationFactory factory = new AnimationFactory(this);

    public GunItem(String name, int shots, int fireRate, float damage, boolean canDuelWield, boolean isTwoHanded, SoundEvent fireSound, SoundEvent reloadSound) {
        this.reloadSound = reloadSound;
        this.setRegistryName("firstlight:" + this.getName());
        this.setUnlocalizedName("item.firstlight." + this.getName());

        this.name = name;
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
    public void reload(EntityLivingBase entityLivingBase, ItemStack stack) {

    }

    @Override
    public void fire(EntityLivingBase entity, ItemStack stack) {
        World world = entity.getEntityWorld();

        if (!world.isRemote) {
            CooldownTracker tracker = (entity instanceof EntityPlayer ? ((EntityPlayer) entity).getCooldownTracker() : Firstlight.GCT);
            if (!tracker.hasCooldown(this)) {
                int fired;
                for (fired = 1; fired <= this.shots; fired++) {
                    BulletEntity bullet = new BulletEntity(world, entity, this.damage);
                    bullet.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, 10.0F, 0.0F);
                    world.spawnEntity(bullet);
                }
                if (this.fireSound != null) {
                    entity.getEntityWorld().playSound(null, entity.getPosition(), this.fireSound, (entity instanceof EntityPlayer ? SoundCategory.PLAYERS : SoundCategory.HOSTILE), 1.0F, 1.0F);
                }
                tracker.setCooldown(this, this.fireRate);

                if (entity instanceof EntityPlayerSP) {
                    AnimationController controller = GeckoLibUtil.getControllerForStack(this.factory, stack, this.getName());
                    controller.setAnimation(SHOOTING_ANIMATION);
                    controller.markNeedsReload();
                }

                if (entity instanceof EntityPlayerMP) {
                    Firstlight.NETWRAPPER.sendTo(new AnimationPacket(stack, 0), (EntityPlayerMP) entity);
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
        if (worldIn.isRemote || entityIn instanceof EntityPlayerSP) {
            AnimationController controller = GeckoLibUtil.getControllerForStack(this.factory, stack, this.getName());
            if (controller.getAnimationState() == AnimationState.Stopped) {
                Animation animation = controller.getCurrentAnimation();
                if (animation != null) {
                    if (!animation.animationName.equals("Idle")) {
                        controller.setAnimation(IDLE_ANIMATION);
                        controller.markNeedsReload();
                    }
                }
            }
            return;
        }

        if (entityIn instanceof EntityPlayer) {
            return;
        }

        CooldownTracker tracker = Firstlight.GCT;
        int ticks = (int) tracker.getCooldown(this, 0.0F);
        if (ticks > 0) {
            tracker.setCooldown(this, --ticks);
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<GunItem> controller = new AnimationController<>(this, this.getName(), 1, (event -> PlayState.CONTINUE));
        controller.easingType = EasingType.NONE;
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public String getName() {
        return this.name;
    }
}