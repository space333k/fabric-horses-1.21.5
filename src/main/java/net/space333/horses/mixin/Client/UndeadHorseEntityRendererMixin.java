package net.space333.horses.mixin.Client;

import net.minecraft.client.render.entity.AbstractDonkeyEntityRenderer;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.UndeadHorseEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.DonkeyEntityRenderState;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UndeadHorseEntityRenderer.class)
public abstract class UndeadHorseEntityRendererMixin extends AbstractHorseEntityRenderer<AbstractHorseEntity, LivingHorseEntityRenderState, AbstractHorseEntityModel<LivingHorseEntityRenderState>> {

    @Unique
    private static ItemStack armor = ItemStack.EMPTY;

    public UndeadHorseEntityRendererMixin(EntityRendererFactory.Context context, AbstractHorseEntityModel<LivingHorseEntityRenderState> model, AbstractHorseEntityModel<LivingHorseEntityRenderState> babyModel) {
        super(context, model, babyModel);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void addZombieArmorLayer(EntityRendererFactory.Context ctx, UndeadHorseEntityRenderer.Type type, CallbackInfo ci) {
        if (type == UndeadHorseEntityRenderer.Type.ZOMBIE) {
            UndeadHorseEntityRenderer current = (UndeadHorseEntityRenderer) (Object) this;

            ((LivingEntityRendererAccessor) current).getFeature().add(
                    new SaddleFeatureRenderer<>(
                            current,
                            ctx.getEquipmentRenderer(),
                            EquipmentModel.LayerType.HORSE_BODY,
                            horseEntityRenderState -> armor,
                            new HorseEntityModel(ctx.getPart(EntityModelLayers.ZOMBIE_HORSE)),
                            new HorseEntityModel(ctx.getPart(EntityModelLayers.ZOMBIE_HORSE_BABY))
                    )
            );
        }
    }

    public void updateRenderState(AbstractHorseEntity abstractHorseEntity, LivingHorseEntityRenderState livingHorseEntityRenderState, float f) {
        super.updateRenderState(abstractHorseEntity, livingHorseEntityRenderState, f);
        armor = abstractHorseEntity.getBodyArmor().copy();
    }

}
