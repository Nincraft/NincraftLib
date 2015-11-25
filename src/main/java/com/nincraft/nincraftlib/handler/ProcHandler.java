package com.nincraft.nincraftlib.handler;

import com.nincraft.nincraftlib.NincraftLib;
import com.nincraft.nincraftlib.api.item.IProcBuff;
import com.nincraft.nincraftlib.reference.Names;
import com.nincraft.nincraftlib.reference.Settings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ProcHandler {

	@SubscribeEvent
	public void procOnDeath(LivingDeathEvent event) {
		if (Settings.Abilities.canProc && event.source.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.source.getEntity();
			if (!player.isEntityEqual(event.entity) && isUsingProcSword(player)) {
				spawnParticle(player, 1.4, 1.3);
				spawnParticle(player, 1.4, 0.3);
				if (!player.worldObj.isRemote) {
					player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, Names.Sounds.PROC_ATTACK, 1, 1);
					player.addPotionEffect(new PotionEffect(5, 100, 9));
					player.addPotionEffect(new PotionEffect(1, 100, 2));
				}
			}
		}
	}

	private void spawnParticle(final EntityPlayerMP player, final double yOffset, final double velocity) {
		NincraftLib.proxy.spawnParticle(Names.Particles.GREEN_SPARKLES, player.posX, player.posY + yOffset,
				player.posZ, velocity, velocity, velocity);
	}

	@SubscribeEvent
	public void jimmysSwordPvP(LivingAttackEvent event) {
		if (event.source.getEntity() instanceof EntityPlayerMP && event.entity instanceof EntityPlayerMP
				&& isUsingProcSword((EntityPlayerMP) event.source.getEntity()) && !Settings.Abilities.canJimmyPvP) {
			event.setCanceled(true);
		}
	}

	private boolean isUsingProcSword(EntityPlayer player) {
		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() != null)
			return player.getCurrentEquippedItem().getItem() instanceof IProcBuff;
		return false;
	}
}
