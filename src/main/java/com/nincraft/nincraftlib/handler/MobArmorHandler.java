package com.nincraft.nincraftlib.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class MobArmorHandler {
	
	
	
	@SubscribeEvent
	public void onLivingSpawn(LivingSpawnEvent event){
		EntityLivingBase mob = event.entityLiving;
		if(mob instanceof EntitySkeleton || mob instanceof EntityZombie){
			
		}
	}

}
