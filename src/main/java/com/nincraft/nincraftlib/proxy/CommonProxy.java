package com.nincraft.nincraftlib.proxy;

import com.nincraft.nincraftlib.handler.ConfigurationHandler;

import cpw.mods.fml.common.FMLCommonHandler;

public abstract class CommonProxy implements IProxy {

	public void registerEventHandlers() {
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
	}

}
