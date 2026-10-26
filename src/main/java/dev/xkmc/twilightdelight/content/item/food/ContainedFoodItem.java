package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Food served in a container, such as a bowl of stew or a bucket of curd. Eating
 * hands the container back, and crafting leaves it behind as well.
 */
public class ContainedFoodItem extends TDFoodItem {

	private final Item container;

	public ContainedFoodItem(Item container, Properties properties) {
		super(properties);
		this.container = container;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity consumer) {
		super.finishUsingItem(stack, worldIn, consumer);
		ItemStack itemStack = new ItemStack(container);
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
		return new ItemStack(container);
	}

}
