package com.nincraft.nincraftlib.handler;



import com.nincraft.nincraftlib.reference.Settings;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ThunderJoinHandler {

	private boolean isPlayerThundertastic(EntityPlayerMP player) {
		for (String playerName : Settings.Silly.thunderList) {
			if (playerName.equals(player.getDisplayName())) {
				return true;
			}
		}
		return false;
	}

	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			if (isPlayerThundertastic(player)) {
				event.world.spawnEntityInWorld(new EntityLightningBolt(event.world, player.posX, 257, player.posZ));
			}
		}
	}

}
