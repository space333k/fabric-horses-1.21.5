package net.space333.horses;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HorsesClient implements ClientModInitializer {
    public static KeyBinding horsePlayerInventory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "text.Horses.keybinding.horsePlayerInventory",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            "text.Horses.keybinding.category"
    ));

    @Override
    public void onInitializeClient() {

    }
}
