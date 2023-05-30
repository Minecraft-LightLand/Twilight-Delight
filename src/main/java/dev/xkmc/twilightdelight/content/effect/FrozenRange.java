package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import twilightforest.init.TFItems;
import twilightforest.init.TFMobEffects;

public class FrozenRange extends RangeSearchEffect {

	public FrozenRange() {
		super(MobEffectCategory.BENEFICIAL, -16724788);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.hasEffect(TFMobEffects.FROSTY.get())) {
			entity.removeEffect(TFMobEffects.FROSTY.get());
		}
		super.applyEffectTick(entity, amplifier);
	}

	@Override
	protected void searchEntities(LivingEntity entity, int amplifier) {
		super.searchEntities(entity, amplifier);
		for (ItemEntity e : getEntitiesInRange(entity, ItemEntity.class)) {
			if (e.getItem().is(Items.BLUE_ICE)) {
				e.setItem(new ItemStack(TFItems.ICE_BOMB.get(), e.getItem().getCount()));
			}
		}
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 21, 4));
	}

}
