package dev.xkmc.twilightdelight.compat.neapolitan;

import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

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
	public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		TDFoodItem.getFoodEffects(food, pTooltip);
	}

}
