package net.space333.horses.mixin.Client;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractHorseEntityModel;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.space333.horses.config.ModConfig;
import net.space333.horses.render.entity.state.ExtendedRideableEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractHorseEntityModel.class, priority = 960)
public abstract class HeadPitchOffsetMixin {

    @Shadow @Final protected ModelPart head;

    // Thanks dorianpb#9929 for the tip on where to mixin
    @Inject(method = "setAngles(Lnet/minecraft/client/render/entity/state/LivingHorseEntityRenderState;)V", at = @At("TAIL"))
    void headPitch(LivingHorseEntityRenderState livingHorseEntityRenderState, CallbackInfo ci) {
        if (livingHorseEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
            if (extendedRideableEntityRenderState.horses$isPlayerPassenger() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                this.head.pitch = Math.min(this.head.pitch + ModConfig.getInstance().horseHeadAngleOffset / 100f, 1.5f);
            }
        }
    }
}
