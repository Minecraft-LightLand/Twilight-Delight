package dev.xkmc.twilightdelight.compat.neapolitan;

import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TDCakeBlock extends FlavoredCakeBlock {

	private final FoodProperties food;

	public TDCakeBlock(FoodProperties food, Properties properties) {
		super(food, properties);
		this.food = food;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		TDFoodItem.getFoodEffects(food, pTooltip);
	}

}
