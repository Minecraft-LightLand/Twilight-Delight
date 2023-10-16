package dev.xkmc.twilightdelight.compat.neapolitan;

import dev.xkmc.twilightdelight.content.item.food.InBowlItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TDIceCreamItem extends InBowlItem {

	public TDIceCreamItem(Properties properties) {
		super(properties);
	}

	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		entity.setTicksFrozen(entity.getTicksFrozen() + 200);
		return super.finishUsingItem(stack, level, entity);
	}

}
