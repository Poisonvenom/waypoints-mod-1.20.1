package net.poisonvenom.waypointsmod;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class WaypointModData {
    public static List<Waypoint> waypoints = new ArrayList<>();

    public static void addWaypoint(String name, BlockPos position) {
        waypoints.add(new Waypoint(name, position));
    }

    public static boolean removeWaypoint(String name) {
        return waypoints.removeIf(waypoint -> waypoint.name.equalsIgnoreCase(name));
    }
}
