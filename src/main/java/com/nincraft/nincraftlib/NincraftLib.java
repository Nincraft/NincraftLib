package com.nincraft.nincraftlib;

import com.nincraft.nincraftlib.handler.ConfigurationHandler;
import com.nincraft.nincraftlib.proxy.IProxy;
import com.nincraft.nincraftlib.reference.Reference;
import com.nincraft.nincraftlib.utility.LogHelper;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
		proxy.registerEventHandlers();
		LogHelper.info("Pre Init Complete");
	}
}
