package net.space333.horses.mixin.Client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererAccessor {

    @Accessor("features")
    List<FeatureRenderer<?, ?>> getFeature();
}
