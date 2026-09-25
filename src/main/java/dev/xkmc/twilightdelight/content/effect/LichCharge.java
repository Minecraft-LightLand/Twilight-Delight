package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Restores durability of lich scepters in hotbar and offhand.
 * For every 1000 / (1 + max durability) ticks, restore 1 durability.
 */
public class LichCharge extends MobEffect {

	public LichCharge() {
		super(MobEffectCategory.BENEFICIAL, 0x6A3DE8);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide()) return;
		if (!(entity instanceof Player player)) return;
		for (int i = 0; i < 9; i++) {
			repair(player.getInventory().getItem(i), entity);
		}
		repair(player.getOffhandItem(), entity);
	}

	private static void repair(ItemStack stack, LivingEntity entity) {
		if (stack.isEmpty() || !stack.isDamageableItem() || !stack.isDamaged()) return;
		ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
		if (id == null || !id.getNamespace().equals("twilightforest") || !id.getPath().contains("scepter")) return;
		int max = stack.getMaxDamage();
		if (max <= 0) return;
		int interval = Math.max(1, (int) Math.round(1000.0 / (1 + max)));
		if (entity.tickCount % interval != 0) return;
		stack.setDamageValue(stack.getDamageValue() - 1);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

}
