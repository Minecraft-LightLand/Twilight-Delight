package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.List;

public class TDDrinkableItem extends DrinkableItem {

	public TDDrinkableItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag isAdvanced) {
		if (Configuration.FOOD_EFFECT_TOOLTIP.get())
			TDFoodItem.getFoodEffects(stack, list);
	}

}
