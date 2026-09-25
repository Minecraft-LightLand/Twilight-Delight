package dev.xkmc.twilightdelight.compat.neapolitan;

import com.teamabnormals.neapolitan.common.item.MilkshakeItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TDMilkshakeItem extends MilkshakeItem {

	public TDMilkshakeItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
		TDFoodItem.getFoodEffects(stack, list);
	}

}
