package com.user.lunarlite;

import com.user.lunarlite.config.ConfigManager;
import com.user.lunarlite.modules.HudManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import com.user.lunarlite.gui.MainMenuScreen;
import org.lwjgl.glfw.GLFW;

public class LiteClient implements ClientModInitializer {
    private boolean wasRightShiftPressed = false;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        HudManager.init();

        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            HudManager.renderAll(context, tickDelta);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            long handle = client.getWindow().getHandle();
            boolean isPressed = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

            if (isPressed && !wasRightShiftPressed) {
                if (client.currentScreen == null) {
                    client.setScreen(new MainMenuScreen());
                }
            }
            wasRightShiftPressed = isPressed;
        });
    }
}