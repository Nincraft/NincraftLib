package com.nincraft.nincraftlib.handler;

import com.nincraft.nincraftlib.api.item.IMoonDamage;
import com.nincraft.nincraftlib.reference.Settings;
import com.nincraft.nincraftlib.utility.MoonModifierDamageSource;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DamageModifierHandler {

	@SubscribeEvent
	public void attackedEntity(LivingAttackEvent event) {
		if (event.getSource().getEntity() instanceof EntityPlayerMP && event.getSource().damageType.equals("player")
				&& Settings.Silly.moonPhasesOPPlzNerf) {
			EntityPlayerMP player = (EntityPlayerMP) event.getSource().getEntity();
			if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IMoonDamage
					&& event.getEntityLiving().getHealth() > 0) {
				event.setCanceled(true);
				if (!player.worldObj.isRemote) {
					event.getEntity().attackEntityFrom(new MoonModifierDamageSource("moonModifier", player),
							getMoonDamage(player.worldObj.getCurrentMoonPhaseFactor(), event.getAmount()));
					int itemDamage = player.getHeldItemMainhand().getItemDamage() + 1;
					player.getHeldItemMainhand().getItem().setDamage(player.getHeldItemMainhand(), itemDamage);
				}
			}
		}
	}

	/**
	 * Returns the new damage amount after going through this formula (assuming
	 * default configs) http://i.imgur.com/VjDFzQG.png This formula essentially
	 * makes moon phase 0 equal to the minumum config value, and phase 1 equal
	 * to the maxinum config value. The values inbetween increase/decrease
	 * linearly.
	 **/
	private float getMoonDamage(float phase, float damage) {
		return (float) (damage * ((phase * 4) * ((Settings.Silly.maxMoonDamage - Settings.Silly.minMoonDamage) / 4))
				+ Settings.Silly.minMoonDamage);
	}

}
