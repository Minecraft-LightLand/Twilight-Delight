package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Restores durability of lich scepters in hotbar and offhand.
 * For every 1000 / (1 + max durability) ticks, restore 1 durability.
 */
public class LichCharge extends MobEffect {

	public LichCharge() {
		super(MobEffectCategory.BENEFICIAL, 0x6A3DE8);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide()) return true;
		if (entity instanceof Player player) {
			for (int i = 0; i < 9; i++) {
				repair(player.getInventory().getItem(i), entity);
			}
			repair(player.getOffhandItem(), entity);
		}
		return true;
	}

	private static void repair(ItemStack stack, LivingEntity entity) {
		if (stack.isEmpty() || !stack.isDamageableItem() || !stack.isDamaged()) return;
		ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
		if (id == null || !id.getNamespace().equals("twilightforest") || !id.getPath().contains("scepter")) return;
		int max = stack.getMaxDamage();
		if (max <= 0) return;
		int interval = Math.max(1, (int) Math.round(1000.0 / (1 + max)));
		if (entity.tickCount % interval != 0) return;
		stack.setDamageValue(stack.getDamageValue() - 1);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

}
