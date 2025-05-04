package net.space333.horses.mixin.Server;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.space333.horses.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AbstractHorseEntity.class, priority = 960)
public abstract class NoBuckMixin {

    @Shadow protected boolean jumping;
    @Shadow public abstract boolean isTame();
    @Shadow public abstract LivingEntity getControllingPassenger();

    @ModifyReturnValue(method = "isAngry", at = @At("RETURN"))
    private boolean isAngry(boolean original) {
        if (ModConfig.getInstance().noBuck
            && jumping
            && isTame()
            && getControllingPassenger() != null) {
            return false;
        }
        return original;
    }
}
