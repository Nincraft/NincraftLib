package com.nincraft.nincraftlib.handler;

import static com.nincraft.nincraftlib.utility.StackHelper.isBlock;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Sets;

import mantle.utils.ItemMetaWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.items.Pattern;

public class TConstructHandler {

	private static final int INGOT_COST_MB = 144;

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

	public static void setToolPartCost(final Integer toolPartIndex, final Integer newCost) {
		// If either the index or new cost are invalid, back out.
		if (toolPartIndex < 0 || newCost < 1) {
			return;
		}

		// Otherwise, set the new pattern cost (configured to represent ingots,
		// patterns represent half-ingots).
		Pattern.setPatternCost(toolPartIndex + 1, newCost * 2);

		// Also find the appropriate melting recipes
		// and adjust their amounts as well.
		for (Entry<Integer, ToolMaterial> material : TConstructRegistry.toolMaterials.entrySet()) {
			adjustCastingFluidAmounts(new ItemMetaWrapper(TinkerTools.patternOutputs[toolPartIndex], material.getKey()),
					toolPartIndex, newCost);
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

	private static void adjustCastingFluidAmounts(final ItemMetaWrapper toolPart, final Integer toolPartIndex,
			final Integer newCost) {
		FluidStack metal = Smeltery.getSmeltingList().get(toolPart);

		// If we find a valid casting recipe,
		// adjust its amounts.
		if (metal != null) {
			metal.amount = newCost * INGOT_COST_MB;
			Smeltery.getSmeltingList().put(toolPart, metal.copy());

			// Find all the casting recipes using this toolpart
			// and adjust their required amounts.
			CastingRecipe recipe = TConstructRegistry.getTableCasting().getCastingRecipe(metal,
					new ItemStack(TinkerSmeltery.metalPattern, 1, toolPartIndex + 1));

			if (recipe != null) {
				recipe.castingMetal.amount = newCost * INGOT_COST_MB;
			}
		}
	}
}
