package net.poisonvenom.waypointsmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class WaypointCommand {

    public static void register() {
        // Register setWaypoint command
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("setWaypoint")
                    .then(CommandManager.argument("name", StringArgumentType.word())
                            .executes(context -> {
                                String name = StringArgumentType.getString(context, "name");
                                ServerCommandSource source = context.getSource();
                                BlockPos pos = source.getPlayer().getBlockPos();

                                WaypointModData.addWaypoint(name, pos);
                                source.sendFeedback(() -> Text.literal("Waypoint '" + name + "' added at your current location."), false);
                                return 1;
                            })
                    ));

            // Register removeWaypoint command
            dispatcher.register(CommandManager.literal("removeWaypoint")
                    .then(CommandManager.argument("name", StringArgumentType.word())
                            .executes(context -> {
                                String name = StringArgumentType.getString(context, "name");
                                ServerCommandSource source = context.getSource();

                                boolean removed = WaypointModData.removeWaypoint(name);
                                if (removed) {
                                    source.sendFeedback(() -> Text.literal("Waypoint '" + name + "' removed."), false);
                                } else {
                                    source.sendFeedback(() -> Text.literal("Waypoint '" + name + "' not found."), false);
                                }
                                return 1;
                            })
                    ));
        });
    }
}