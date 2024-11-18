package net.poisonvenom.waypointsmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;


public class WaypointCommand {

    public static void register() {
        // Register setWaypoint command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("setWaypoint")
                    .then(ClientCommandManager.argument("name", StringArgumentType.word())
                            .executes(context -> {
                                String name = StringArgumentType.getString(context, "name");
                                BlockPos pos = MinecraftClient.getInstance().player.getBlockPos();

                                WaypointModData.addWaypoint(name, pos);
                                context.getSource().sendFeedback((Text.literal("Waypoint '" + name + "' added at your current location.")));
                                return 1;
                            })
                    ));

            // Register removeWaypoint command
            dispatcher.register(ClientCommandManager.literal("removeWaypoint")
                    .then(ClientCommandManager.argument("name", StringArgumentType.word())
                            .executes(context -> {
                                String name = StringArgumentType.getString(context, "name");

                                boolean removed = WaypointModData.removeWaypoint(name);
                                if (removed) {
                                    context.getSource().sendFeedback(Text.literal("Waypoint '" + name + "' removed."));
                                } else {
                                    context.getSource().sendFeedback(Text.literal("Waypoint '" + name + "' not found."));
                                }
                                return 1;
                            })
                    ));
        });
    }
}