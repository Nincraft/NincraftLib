package com.nincraft.nincraftlib.handler;

import static com.nincraft.nincraftlib.utility.StackHelper.isBlock;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Sets;

import mantle.utils.ItemMetaWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.Smeltery;

public class TConstructHandler {

	public static void setMeltingTemp(final String fluidName, final int newTemp) {
		// If either the input or new temperature are null or invalid, back out.
		if (fluidName == null || newTemp <= 0) {
			return;
		}

		Map<ItemMetaWrapper, Integer> meltingTemps = Smeltery.getTemperatureList();

		// Otherwise, find matching recipe and adjust its melting temperature.
		for (ItemMetaWrapper in : getSmeltingInputsByFluid(fluidName)) {
			for (ItemMetaWrapper meltingInput : meltingTemps.keySet()) {
				if (in.equals(meltingInput)) {
					meltingTemps.put(meltingInput, newTemp);
					break;
				}
			}
		}
	}

	public static void addAlloy(final FluidStack output, final FluidStack[] input) {
		// If either the input or output passed in are null, we can back out.
		if (input == null || output == null) {
			return;
		}

		// If there exists alloy recipe with the same exact output, back out.
		for (AlloyMix alloy : Smeltery.getAlloyList()) {
			if (output.isFluidEqual(alloy.result)) {
				return;
			}
		}

		// Otherwise, add a new alloy recipe to the list.
		Smeltery.addAlloyMixing(output, input);
	}

	public static void removeAlloy(final FluidStack output) {
		// If the output passed in is null, back out.
		if (output == null) {
			return;
		}

		List<AlloyMix> alloys = Smeltery.getAlloyList();

		// Otherwise, search through the existing alloy list,
		// and if we find the one we're looking for,
		// remove it.
		for (AlloyMix alloy : alloys) {
			if (output.isFluidEqual(alloy.result)) {
				alloys.remove(alloy);
				return;
			}
		}
	}

	public static void addMeltingRecipe(final ItemStack input, final FluidStack output, final int temp,
			final ItemStack block) {
		// If output or input are null,
		// or block isn't a Block,
		// or temp is at most 0, back out.
		if (output == null || input == null || !isBlock(block) || temp <= 0) {
			return;
		}

		// If there exists a melting recipe with the same input, back out.
		for (ItemMetaWrapper in : Smeltery.getSmeltingList().keySet()) {
			if (input.equals(in)) {
				return;
			}
		}

		Smeltery.addMelting(input, Block.getBlockFromItem(block.getItem()), block.getItemDamage(), temp, output);
	}

	public static void addMeltingRecipe(final String input, final FluidStack output, final int temp,
			final ItemStack block) {
		// If the ore dictionary string is null or doesn't actually exist, back
		// out.
		if (input == null || !OreDictionary.doesOreNameExist(input)) {
			return;
		}

		// Otherwise, for every enty in the dictionary, add a melting recipe.
		for (ItemStack in : OreDictionary.getOres(input)) {
			addMeltingRecipe(in, output, temp, block);
		}
	}

	public static void removeMeltingRecipe(final ItemStack input) {
		// If the input passed in is null, back out.
		if (input == null) {
			return;
		}

		// Otherwise, search through the existing smelting list,
		// and if we find the one we're looking for,
		// remove it.
		for (ItemMetaWrapper in : Smeltery.getSmeltingList().keySet()) {
			if (in.item == input.getItem() && in.meta == input.getItemDamage()) {
				Smeltery.getSmeltingList().remove(in);
				Smeltery.getTemperatureList().remove(in);
				Smeltery.getRenderIndex().remove(in);
				return;
			}
		}
	}

	public static void addBasinRecipe(final ItemStack output, final FluidStack metal, final ItemStack cast,
			final boolean isConsumed, final int delay) {
		// If the output or molten metal are null, back out.
		if (output == null || metal == null) {
			return;
		}

		// Otherwise, add the basin casting recipe.
		TConstructRegistry.getBasinCasting().addCastingRecipe(output, metal, cast, isConsumed, delay, null);
	}

	public static void removeBasinRecipe(final FluidStack metal, final ItemStack cast) {
		removeCastingRecipe(TConstructRegistry.getBasinCasting().getCastingRecipe(metal, cast),
				TConstructRegistry.getBasinCasting());
	}

	public static void addTableRecipe(final ItemStack output, final FluidStack metal, final ItemStack cast,
			final boolean isConsumed, final int delay) {
		// If the output or molten metal are null, back out.
		if (output == null || metal == null) {
			return;
		}

		// Otherwise, add the table casting recipe.
		TConstructRegistry.getTableCasting().addCastingRecipe(output, metal, cast, isConsumed, delay, null);
	}

	public static void removeTableRecipe(final FluidStack metal, final ItemStack cast) {
		removeCastingRecipe(TConstructRegistry.getBasinCasting().getCastingRecipe(metal, cast),
				TConstructRegistry.getTableCasting());
	}

	private static void removeCastingRecipe(final CastingRecipe recipe, final LiquidCasting castingType) {
		// If the recipe or casting type is null, back out.
		if (recipe == null || castingType == null) {
			return;
		}

		// Otherwise, remove the casting recipe.
		castingType.getCastingRecipes().remove(recipe);
	}

	private static Set<ItemMetaWrapper> getSmeltingInputsByFluid(String fluidName) {
		Set<ItemMetaWrapper> keys = Sets.newHashSet();
		Map<ItemMetaWrapper, FluidStack> meltingList = Smeltery.getSmeltingList();

		for (Entry<ItemMetaWrapper, FluidStack> entry : meltingList.entrySet()) {
			if (FluidRegistry.getFluidName(entry.getValue()).equalsIgnoreCase(fluidName)) {
				keys.add(entry.getKey());
			}
		}

		return keys;
	}
}
