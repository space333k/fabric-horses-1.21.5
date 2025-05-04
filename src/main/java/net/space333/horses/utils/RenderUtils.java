package net.space333.horses.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.state.CamelEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.minecraft.client.render.entity.state.LlamaEntityRenderState;
import net.space333.horses.config.ModConfig;

public class RenderUtils {
    public static int getAlpha(boolean isPlayerPassenger) {
        ModConfig.FadeConfig pitchFadeConfig = ModConfig.getInstance().pitchFade;
        if (!pitchFadeConfig.enabled) return 255;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.options.getPerspective().isFirstPerson() || !isPlayerPassenger)
            return 255;

        int minAlpha = 255 - Math.round(pitchFadeConfig.maxTransparency * 2.25f);
        int rate = (255 - minAlpha) / (pitchFadeConfig.startAngle - pitchFadeConfig.endAngle);
        int unclampedAlpha = Math.round(rate * (client.player.renderPitch - pitchFadeConfig.endAngle));

        return Math.min(Math.max(unclampedAlpha, minAlpha), 255);
    }

    public static boolean isRideableEntityRenderState(LivingEntityRenderState livingEntityRenderState) {
        return livingEntityRenderState instanceof LivingHorseEntityRenderState ||
                livingEntityRenderState instanceof LlamaEntityRenderState ||
                livingEntityRenderState instanceof CamelEntityRenderState;
    }
}
