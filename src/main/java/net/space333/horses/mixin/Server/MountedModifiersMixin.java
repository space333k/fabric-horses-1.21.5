package net.space333.horses.mixin.Server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.space333.horses.Horses;
import net.space333.horses.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// adds DataPack attribute modifiers to player & horse while mounted to
// - increase horse's StepHeight by 10% (when enabled)
// - remove BreakSpeed debuff from not being grounded (when enabled)
@Mixin(value = PlayerEntity.class, priority = 960)
public abstract class MountedModifiersMixin extends LivingEntity {

    protected MountedModifiersMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    EntityAttributeModifier horses$mountedStepHeight = new EntityAttributeModifier(Identifier.of(Horses.MOD_ID, "mounted-step-height"), 0.1, EntityAttributeModifier.Operation.ADD_VALUE);
    @Unique
    EntityAttributeModifier horses$mountedBreakSpeed = new EntityAttributeModifier(Identifier.of(Horses.MOD_ID, "mounted-break-speed"), 5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        boolean result = super.startRiding(entity, force);
        if (!(super.getWorld() instanceof ServerWorld && entity instanceof AbstractHorseEntity horse))
            return result;

        if (ModConfig.getInstance().stepHeight) {
            EntityAttributeInstance stepHeight = horse.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
            if (stepHeight != null && !stepHeight.hasModifier(horses$mountedStepHeight.id())) {
                stepHeight.addTemporaryModifier(horses$mountedStepHeight);
            }
        }

        if (ModConfig.getInstance().breakSpeed) {
            EntityAttributeInstance breakSpeed = horse.getAttributeInstance(EntityAttributes.BLOCK_BREAK_SPEED);
            if (breakSpeed != null && !breakSpeed.hasModifier(horses$mountedBreakSpeed.id())) {
                breakSpeed.addTemporaryModifier(horses$mountedBreakSpeed);
            }
        }
        return result;
    }

    @Override
    public boolean startRiding(Entity entity) {
        return this.startRiding(entity, false);
    }

    @Override
    public void stopRiding() {
        if (!(super.getWorld() instanceof ServerWorld && getVehicle() instanceof AbstractHorseEntity horse)) {
            super.stopRiding();
            return;
        }

        EntityAttributeInstance stepHeight = horse.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
        if (stepHeight != null) stepHeight.removeModifier(horses$mountedStepHeight);

        EntityAttributeInstance breakSpeed = getAttributeInstance(EntityAttributes.BLOCK_BREAK_SPEED);
        if (breakSpeed != null) breakSpeed.removeModifier(horses$mountedBreakSpeed);

        super.stopRiding();
    }
}
