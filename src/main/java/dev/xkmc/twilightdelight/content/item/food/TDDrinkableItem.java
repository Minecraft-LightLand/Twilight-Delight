package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

public class TDDrinkableItem extends Item {

	public TDDrinkableItem(Properties properties) {
		super(properties);
	}

	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag isAdvanced) {
		if (Configuration.FOOD_EFFECT_TOOLTIP.get())
			TDFoodItem.getFoodEffects(stack, list);
	}

}
