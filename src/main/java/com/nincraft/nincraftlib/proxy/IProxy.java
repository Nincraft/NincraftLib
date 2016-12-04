package com.nincraft.nincraftlib.proxy;

public interface IProxy {
	ClientProxy getClientProxy();

	void registerEventHandlers();

	void spawnParticle(String particleName, double xCoord, double yCoord, double zCoord,
					   double xVelocity, double yVelocity, double zVelocity);
}
