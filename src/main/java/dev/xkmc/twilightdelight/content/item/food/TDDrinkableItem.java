package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.List;

public class TDDrinkableItem extends DrinkableItem {

	public TDDrinkableItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity consumer) {
		super.finishUsingItem(stack, worldIn, consumer);
		ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
		if (stack.isEmpty()) {
			return itemStack;
		}

		if (consumer instanceof Player player && !player.getAbilities().instabuild) {
			if (!player.getInventory().add(itemStack)) {
				player.drop(itemStack, false);
			}
		}

		return stack;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
		return new ItemStack(Items.GLASS_BOTTLE);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag isAdvanced) {
		TDFoodItem.getFoodEffects(stack, list);
	}
}
