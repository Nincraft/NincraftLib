package com.nincraft.nincraftlib.handler;

import java.io.File;

import com.nincraft.nincraftlib.reference.ConfigurationNincraftLib;
import com.nincraft.nincraftlib.reference.Reference;
import com.nincraft.nincraftlib.reference.Settings;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	private static void loadConfiguration() {
		
		loadSillyConfigs(ConfigurationNincraftLib.CATEGORY_SILLY);
		loadAbilityConfigs(ConfigurationNincraftLib.CATEGORY_ABILITIES);	


		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadAbilityConfigs(String category) {
		Settings.Abilities.canProc = configuration.getBoolean("canProc", category, true,
				"Using Jimmy's Sword will proc. (All credit for the proc and idea goes to Rob Moran creator of Dwarves VS Zombies)");
		Settings.Abilities.canJimmyPvP = configuration.getBoolean("canJimmyPvP", category, false,
				"Allows Jimmy's Sword to attack other players");
	}
	
	private static void loadSillyConfigs(String category) {
		Settings.Silly.thunderList = configuration.getStringList("thunderList", category,
				new String[] { "Nincodedo", "Undead_Zeratul" }, "Thundertastic");
		Settings.Silly.moonPhasesOPPlzNerf = configuration.getBoolean("moonPhasesOPPlzNerf", category, true,
				"Moon phases cause shenanigans");
		Settings.Silly.minMoonDamage = configuration.getFloat("minMoonDamage", category, 0.7F, 0F, 10F,
				"Minimum moon damage multiplier");
		Settings.Silly.maxMoonDamage = configuration.getFloat("maxMoonDamage", category, 1.15F, 0F, 10F,
				"Maximum moon damage multiplier");
	}

}
