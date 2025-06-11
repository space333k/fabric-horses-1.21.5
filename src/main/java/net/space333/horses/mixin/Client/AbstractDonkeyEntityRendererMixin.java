package net.space333.horses.mixin.Client;

import net.minecraft.client.render.entity.AbstractDonkeyEntityRenderer;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.DonkeyEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.entity.model.HorseSaddleEntityModel;
import net.minecraft.client.render.entity.state.DonkeyEntityRenderState;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractDonkeyEntityRenderer.class)
public abstract class AbstractDonkeyEntityRendererMixin<T extends AbstractDonkeyEntity> extends AbstractHorseEntityRenderer<T, DonkeyEntityRenderState, DonkeyEntityModel> {

    @Unique
    private static ItemStack armor = ItemStack.EMPTY;

    public AbstractDonkeyEntityRendererMixin(EntityRendererFactory.Context context, DonkeyEntityModel model, DonkeyEntityModel babyModel) {
        super(context, model, babyModel);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void addMuleArmorLayer(EntityRendererFactory.Context context, AbstractDonkeyEntityRenderer.Type type, CallbackInfo ci) {
        if (type == AbstractDonkeyEntityRenderer.Type.MULE) {
            AbstractDonkeyEntityRenderer<AbstractDonkeyEntity> current = (AbstractDonkeyEntityRenderer<AbstractDonkeyEntity>) (Object) this;

            ((LivingEntityRendererAccessor) current).getFeature().add(
                    new SaddleFeatureRenderer<>(
                            current,
                            context.getEquipmentRenderer(),
                            EquipmentModel.LayerType.HORSE_BODY,
                            horseEntityRenderState -> armor,
                            new DonkeyEntityModel(context.getPart(EntityModelLayers.MULE)),
                            new DonkeyEntityModel(context.getPart(EntityModelLayers.MULE_BABY))
                    )
            );
        }
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/AbstractDonkeyEntity;Lnet/minecraft/client/render/entity/state/DonkeyEntityRenderState;F)V",
    at = @At(value = "TAIL"))
    private void addArmor(T abstractDonkeyEntity, DonkeyEntityRenderState donkeyEntityRenderState, float f, CallbackInfo ci) {
        armor = abstractDonkeyEntity.getBodyArmor().copy();
    }
}
