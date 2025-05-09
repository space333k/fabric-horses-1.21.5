package net.space333.horses.mixin.Server;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.RaftEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBoatEntity.class)
public abstract class AllowRaftMixin {

    @ModifyReturnValue(method = "isSmallerThanBoat", at = @At("RETURN"))
    private boolean allowRaft(boolean original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof AbstractHorseEntity && horses$thiz() instanceof RaftEntity)
            return true;
        return original;
    }

    @Unique
    private AbstractBoatEntity horses$thiz() {
        return ((AbstractBoatEntity) (Object) this);
    }
}
