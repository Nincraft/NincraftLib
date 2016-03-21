package com.nincraft.nincraftlib.utility;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class StackHelper {
	public static boolean isBlock(final ItemStack block) {
		return block.getItem() instanceof ItemBlock;
	}
}
