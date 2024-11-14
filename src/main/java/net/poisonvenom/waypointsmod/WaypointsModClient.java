package net.poisonvenom.waypointsmod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BeaconBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WaypointsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registering the HUD rendering callback
        HudRenderCallback.EVENT.register(WaypointsModClient::renderWaypoints);
        // Same but for beams
        WorldRenderEvents.AFTER_ENTITIES.register(WaypointsModClient::renderWaypointsBeam);
    }

    private static void renderWaypointsBeam(WorldRenderContext worldRenderContext) {
        MatrixStack mtx = worldRenderContext.matrixStack();
        MinecraftClient client = MinecraftClient.getInstance();
        Camera cam = worldRenderContext.camera();
        TextRenderer textRenderer = client.textRenderer;
        Vec3d cameraPos = cam.getPos();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        // Render each waypoint
        for (Waypoint waypoint : WaypointModData.waypoints) {
            BlockPos pos = waypoint.position;
            Vec3d waypointPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);

            // Check if waypoint is within rendering distance
            if (cameraPos.distanceTo(waypointPos) < 100.0) { // Render only if within 100 blocks
                mtx.push();
                buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

                // Translate the matrix stack to the waypoint's position
                mtx.translate(waypointPos.x - cameraPos.x, waypointPos.y - cameraPos.y, waypointPos.z - cameraPos.z);

                // Rotate the text to face the player
                mtx.multiply(cam.getRotation());

                // Scale the text (smaller as it is farther away)
                float scale = 0.025f; // Adjust this to change text size
                mtx.scale(-scale, -scale, scale);

                // Render the waypoint name
                String waypointText = waypoint.name + " (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";
                int textWidth = textRenderer.getWidth(waypointText) / 2;

                // Render text with shadow
                buffer.vertex(mtx.peek().getPositionMatrix(), 20, 20, 5).color(0xFF414141).next();
                buffer.vertex(mtx.peek().getPositionMatrix(), 5, 40, 5).color(0xFF000000).next();
                buffer.vertex(mtx.peek().getPositionMatrix(), 35, 40, 5).color(0xFF000000).next();
                buffer.vertex(mtx.peek().getPositionMatrix(), 20, 60, 5).color(0xFF414141).next();

                RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                tess.draw();
                mtx.pop();
            }
        }

    }

    private static void renderWaypoints(DrawContext drawcontext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        //HUD render list
        if (client.player != null && !WaypointModData.waypoints.isEmpty()) {
            int yOffset = 10; // Y offset for each waypoint on the screen
            for (Waypoint waypoint : WaypointModData.waypoints) {
                String waypointText = String.format("%s - X: %d, Y: %d, Z: %d",
                        waypoint.name, waypoint.position.getX(),
                        waypoint.position.getY(), waypoint.position.getZ());

                drawcontext.drawTextWithShadow(client.textRenderer, waypointText, 5,yOffset, 0xFFFFFF);
                yOffset += 10; // Move down for the next waypoint
            }
        }
    }
}
