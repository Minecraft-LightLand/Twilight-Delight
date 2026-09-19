package dev.xkmc.twilightdelight.compat;

import dev.xkmc.l2core.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Optional compat with Twilit Gourmet.
 * When its counterparts are present, our duplicated entries (cabinets, knives,
 * mushgloom colony, venison/meef cutting chain) are hidden from the creative tab
 * and their recipes carry a {@code twilightdelight:item_absent} condition so they stay disabled.
 * Conditions are per-item (registry check), not per-mod, so they track content
 * even if the other mod renames or removes entries in the future.
 */
@EventBusSubscriber(modid = TwilightDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TGCompat {

	public static final String MODID = "twilitgourmet";

	public static ResourceLocation loc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}

	public static boolean isLoaded() {
		return ModList.get().isLoaded(MODID);
	}

	public static boolean isItemPresent(ResourceLocation item) {
		return BuiltInRegistries.ITEM.containsKey(item);
	}

	public static ICondition absentOf(ResourceLocation item) {
		return new ItemAbsentCondition(item);
	}

	public static RecipeOutput noTG(RecipeOutput pvd, ResourceLocation counterpart) {
		return ConditionalRecipeWrapper.of(pvd, absentOf(counterpart));
	}

	private static final List<Supplier<? extends ItemLike>> DUPLICATES = new ArrayList<>();

	static {
		DUPLICATES.add(TDItems.IRONWOOD_KNIFE);
		DUPLICATES.add(TDItems.STEELEAF_KNIFE);
		DUPLICATES.add(TDItems.KNIGHTMETAL_KNIFE);
		DUPLICATES.add(TDItems.FIERY_KNIFE);
		DUPLICATES.add(TDBlocks.MUSHGLOOM_COLONY);
		for (var cab : TDBlocks.CABINETS) DUPLICATES.add(cab);
		DUPLICATES.add(DelightFood.RAW_VENISON_RIB.item);
		DUPLICATES.add(DelightFood.COOKED_VENISON_RIB.item);
		DUPLICATES.add(DelightFood.RAW_MEEF_SLICE.item);
		DUPLICATES.add(DelightFood.COOKED_MEEF_SLICE.item);
	}

	public static List<ItemStack> duplicateStacks() {
		return DUPLICATES.stream().map(e -> new ItemStack(e.get())).toList();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void hideDuplicates(BuildCreativeModeTabContentsEvent event) {
		if (!isLoaded()) return;
		if (!event.getTabKey().equals(TDBlocks.TAB.key())) return;
		for (var e : DUPLICATES) {
			event.remove(new ItemStack(e.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}
	}

}
