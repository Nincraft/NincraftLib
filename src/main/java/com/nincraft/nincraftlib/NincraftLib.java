package com.nincraft.nincraftlib;

import com.nincraft.nincraftlib.handler.ConfigurationHandler;
import com.nincraft.nincraftlib.proxy.IProxy;
import com.nincraft.nincraftlib.reference.Reference;
import com.nincraft.nincraftlib.utility.LogHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class NincraftLib {

	@Mod.Instance(Reference.MOD_ID)
	public static NincraftLib instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		LogHelper.info("Init Complete");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		LogHelper.info("Post Init Complete");
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());

		LogHelper.info("Pre Init Complete");
	}
}
