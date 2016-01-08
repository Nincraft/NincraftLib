package com.nincraft.nincraftlib.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobArmorHandler {
	
	
	
	@SubscribeEvent
	public void onLivingSpawn(LivingSpawnEvent event){
		EntityLivingBase mob = event.entityLiving;
		if(mob instanceof EntitySkeleton || mob instanceof EntityZombie){
			
		}
	}

}
