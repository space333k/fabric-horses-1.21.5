package net.space333.horses.mixin.Client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.tutorial.TutorialManager;
import net.space333.horses.HorsesClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MinecraftClient.class, priority = 960)
public abstract class InventoryAccessorMixin {

    @Shadow @Final private TutorialManager tutorialManager;
    @Shadow public abstract void setScreen(@Nullable Screen screen);
    @Shadow @Nullable public ClientPlayerEntity player;

    @Redirect(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.openRidingInventory ()V"))
    void playerInventoryAccess(ClientPlayerEntity instance) {
        assert this.player != null;
        if (HorsesClient.horsePlayerInventory.isPressed()) {
            tutorialManager.onInventoryOpened();
            setScreen(new InventoryScreen(this.player));
        } else {
            instance.openRidingInventory();
        }
    }
}
