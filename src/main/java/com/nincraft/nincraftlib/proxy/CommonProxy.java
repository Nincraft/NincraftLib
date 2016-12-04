package com.nincraft.nincraftlib.proxy;

import com.nincraft.nincraftlib.handler.ConfigurationHandler;
import com.nincraft.nincraftlib.handler.DamageModifierHandler;
import com.nincraft.nincraftlib.handler.ProcHandler;
import com.nincraft.nincraftlib.handler.ThunderJoinHandler;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {

	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new ThunderJoinHandler());
		MinecraftForge.EVENT_BUS.register(new ProcHandler());
		MinecraftForge.EVENT_BUS.register(new DamageModifierHandler());

	}

}
