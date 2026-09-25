package dev.xkmc.twilightdelight.compat.neapolitan;

import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TDCakeBlock extends FlavoredCakeBlock {

	private final FoodProperties food;

	public final NeapolitanCakes cake;

	public TDCakeBlock(FoodProperties food, Properties properties, NeapolitanCakes cake) {
		super(food, properties);
		this.food = food;
		this.cake = cake;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		TDFoodItem.getFoodEffects(food, tooltip);
	}

}
