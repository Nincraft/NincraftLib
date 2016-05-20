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
		if (event.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			if (isPlayerThundertastic(player)) {
				event.getWorld().spawnEntityInWorld(
						new EntityLightningBolt(event.getWorld(), player.posX, player.posY, player.posZ, false));
			}
		}
	}

}
