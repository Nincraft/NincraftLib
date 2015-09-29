package com.nincraft.nincraftlib.proxy;

public class ServerProxy extends CommonProxy {

	@Override
	public ClientProxy getClientProxy() {
		return null;
	}

	@Override
	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {

	}

	@Override
	public void spawnParticle(String particleName, double xCoord, double yCoord, double zCoord, double xVelocity,
			double yVelocity, double zVelocity) {

	}

}
