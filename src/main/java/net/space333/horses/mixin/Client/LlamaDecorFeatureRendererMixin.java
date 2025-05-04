package net.space333.horses.mixin.Client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.LlamaDecorFeatureRenderer;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.minecraft.client.render.entity.state.LlamaEntityRenderState;
import net.space333.horses.render.entity.model.ExtendedRideableEquippableEntityModel;
import net.space333.horses.render.entity.state.ExtendedRideableEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LlamaDecorFeatureRenderer.class, priority = 960)
public class LlamaDecorFeatureRendererMixin {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/LlamaEntityRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/registry/RegistryKey;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/LlamaEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/LlamaEntityRenderState;)V",
                    shift = At.Shift.AFTER))
    void updatePlayerPassenger(CallbackInfo callbackInfo, @Local(argsOnly = true) LlamaEntityRenderState llamaEntityRenderState, @Local LlamaEntityModel llamaEntityModel) {
        if (llamaEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
            boolean isPlayerPassenger = extendedRideableEntityRenderState.horses$isPlayerPassenger();
            ((ExtendedRideableEquippableEntityModel) llamaEntityModel).horses$setPlayerPassenger(isPlayerPassenger);
        }
    }
}
