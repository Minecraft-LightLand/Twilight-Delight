
package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class InBowlItem extends TDFoodItem {

	public InBowlItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity consumer) {
		super.finishUsingItem(stack, worldIn, consumer);
		ItemStack itemStack = new ItemStack(Items.BOWL);
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
		return new ItemStack(Items.BOWL);
	}

}
