package net.space333.horses.mixin.Client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.space333.horses.render.entity.state.ExtendedRideableEntityRenderState;
import net.space333.horses.utils.RenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.space333.horses.utils.RenderUtils.isRideableEntityRenderState;


@Mixin(value = LivingEntityRenderer.class, priority = 960)
public abstract class LivingEntityRendererMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL"))
    void setPlayerPassenger(CallbackInfo ci, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Local(argsOnly = true) LivingEntity livingEntity) {
        if (isRideableEntityRenderState(livingEntityRenderState)) {
            boolean playerPassenger = livingEntity.hasPassenger(MinecraftClient.getInstance().player);
            ((ExtendedRideableEntityRenderState) livingEntityRenderState).horses$setId(livingEntity.getId());
            ((ExtendedRideableEntityRenderState) livingEntityRenderState).horses$setPlayerPassenger(playerPassenger);
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD"))
    void setAlpha(CallbackInfo ci, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Share("alpha") LocalIntRef alpha) {
        if (livingEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
            alpha.set(RenderUtils.getAlpha(extendedRideableEntityRenderState.horses$isPlayerPassenger()));
        }
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/render/RenderLayer;"),
            index = 2)
    boolean makeRenderLayerTranslucent(boolean translucent, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Share("alpha") LocalIntRef alphaRef) {
        if (translucent) return true;
        if (isRideableEntityRenderState(livingEntityRenderState))
            return alphaRef.get() != 255;
        return false;
    }
}
