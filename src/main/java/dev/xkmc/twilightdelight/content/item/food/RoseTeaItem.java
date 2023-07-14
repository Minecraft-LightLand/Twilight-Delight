package dev.xkmc.twilightdelight.content.item.food;

import dev.xkmc.twilightdelight.init.data.TDDatapackRegistriesGen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RoseTeaItem extends TDDrinkableItem {

	public RoseTeaItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
		consumer.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TDDatapackRegistriesGen.ROSE_TEA)), 4);
		return super.finishUsingItem(stack, level, consumer);
	}
}
