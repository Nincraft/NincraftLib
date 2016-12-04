package com.nincraft.nincraftlib.proxy;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public ClientProxy getClientProxy() {
		return this;
	}

	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
	}

	@Override
	public void spawnParticle(String particleName, double xCoord, double yCoord, double zCoord, double xVelocity,
			double yVelocity, double zVelocity) {
		FMLClientHandler.instance().getWorldClient().spawnParticle(EnumParticleTypes.valueOf(particleName), xCoord,
				yCoord, zCoord, xVelocity, yVelocity, zVelocity);
	}

}
