package net.poisonvenom.waypointsmod;

import net.minecraft.util.math.BlockPos;

public class Waypoint {
    public String name;
    public BlockPos position;

    public Waypoint(String name, BlockPos position) {
        this.name = name;
        this.position = position;
    }
}
