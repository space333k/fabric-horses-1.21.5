package net.space333.horses.mixin.Server;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.math.Vec3d;
import net.space333.horses.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Lower wander speed for saddled horses
@Mixin(value = LivingEntity.class, priority = 960)
public abstract class NoWanderMixin {

    @ModifyArg(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V"))
    private Vec3d lowerWanderSpeed(Vec3d input) {
        if (ModConfig.getInstance().noWander
          && (horses$thiz() instanceof AbstractHorseEntity horse
          && horse.hasSaddleEquipped()))
            return(Vec3d.ZERO);
        return input;
    }

    @Unique
    private LivingEntity horses$thiz() {
        return ((LivingEntity)(Object)this);
    }
}
