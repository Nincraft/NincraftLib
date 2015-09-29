package com.nincraft.nincraftlib.proxy;

public interface IProxy {
	public abstract ClientProxy getClientProxy();

	public abstract void registerEventHandlers();

	public abstract void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume,
			float pitch);

	public abstract void spawnParticle(String particleName, double xCoord, double yCoord, double zCoord,
			double xVelocity, double yVelocity, double zVelocity);
}
