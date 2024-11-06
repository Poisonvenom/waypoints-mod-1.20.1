package net.poisonvenom.waypointsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class WaypointsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registering the HUD rendering callback
        HudRenderCallback.EVENT.register(WaypointsModClient::renderWaypoints);
    }

    private static void renderWaypoints(DrawContext drawcontext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && !WaypointModData.waypoints.isEmpty()) {
            int yOffset = 10; // Y offset for each waypoint on the screen
            for (Waypoint waypoint : WaypointModData.waypoints) {
                String waypointText = String.format("%s - X: %d, Y: %d, Z: %d",
                        waypoint.name, waypoint.position.getX(),
                        waypoint.position.getY(), waypoint.position.getZ());

                drawcontext.drawTextWithShadow(client.textRenderer, waypointText, 5,yOffset, 0xFFFFFF);
//                client.textRenderer.drawTextWithShadow(
//                        drawcontext,
//                        waypointText,
//                        10, yOffset,
//                        0xFFFFFF // White color
//                );
                yOffset += 10; // Move down for the next waypoint
            }
        }
    }
}
