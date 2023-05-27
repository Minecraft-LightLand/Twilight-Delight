package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RoseTeaItem extends TDDrinkableItem {

	public RoseTeaItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity consumer) {
		consumer.hurt(new DamageSource("twilightdelight.thorn_rose_tea"), 4);
		return super.finishUsingItem(stack, worldIn, consumer);
	}
}
