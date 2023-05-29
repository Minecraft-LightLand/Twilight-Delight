package dev.xkmc.twilightdelight.compat.neapolitan;

import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TDIceCreamItem extends BowlFoodItem {

	public TDIceCreamItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag isAdvanced) {
		TDFoodItem.getFoodEffects(stack, list);
	}

}
