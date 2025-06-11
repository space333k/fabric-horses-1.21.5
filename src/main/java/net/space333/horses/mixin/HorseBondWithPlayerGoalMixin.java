package net.space333.horses.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.HorseBondWithPlayerGoal;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HorseBondWithPlayerGoal.class)
public class HorseBondWithPlayerGoalMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isTame()Z"))
    private boolean creativeBonding(AbstractHorseEntity instance) {
        Entity entity = instance.getFirstPassenger();
        if(entity instanceof PlayerEntity playerEntity) {
            if(playerEntity.isInCreativeMode()) {
                instance.bondWithPlayer(playerEntity);
                return true;
            }
        }
        return instance.isTame();
    }
}
