package com.nincraft.nincraftlib.handler;

import com.nincraft.nincraftlib.NincraftLib;
import com.nincraft.nincraftlib.api.item.IProcBuff;
import com.nincraft.nincraftlib.reference.Names;
import com.nincraft.nincraftlib.reference.Settings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameData;

public class ProcHandler {

	@SubscribeEvent
	public void procOnDeath(LivingDeathEvent event) {
		if (Settings.Abilities.canProc && event.getSource().getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.getSource().getEntity();
			if (!player.isEntityEqual(event.getEntity()) && isUsingProcSword(player)) {
				spawnParticle(player, 1.4, 1.3);
				spawnParticle(player, 1.4, 0.3);
				if (!player.worldObj.isRemote) {
					player.addPotionEffect(new PotionEffect(GameData.getPotionRegistry().getObjectById(5), 9));
					player.addPotionEffect(new PotionEffect(GameData.getPotionRegistry().getObjectById(1), 2));
				}
			}
		}
	}

	private void spawnParticle(final EntityPlayerMP player, final double yOffset, final double velocity) {
		NincraftLib.proxy.spawnParticle(Names.Particles.GREEN_SPARKLES, player.posX, player.posY + yOffset, player.posZ,
				velocity, velocity, velocity);
	}

	@SubscribeEvent
	public void jimmysSwordPvP(LivingAttackEvent event) {
		if (event.getSource().getEntity() instanceof EntityPlayerMP && event.getEntity() instanceof EntityPlayerMP
				&& isUsingProcSword((EntityPlayerMP) event.getSource().getEntity()) && !Settings.Abilities.canJimmyPvP) {
			event.setCanceled(true);
		}
	}

	private boolean isUsingProcSword(EntityPlayer player) {
		if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() != null)
			return player.getHeldItemMainhand().getItem() instanceof IProcBuff;
		return false;
	}
}
