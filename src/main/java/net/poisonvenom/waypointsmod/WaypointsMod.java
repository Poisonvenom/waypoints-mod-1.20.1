package net.poisonvenom.waypointsmod;

import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaypointsMod implements ModInitializer {
	public static final String MOD_ID = "waypointsmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static List<Waypoint> waypoints = new ArrayList<>();

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded Waypoints Mod.");
		WaypointCommand.register();
	}
}