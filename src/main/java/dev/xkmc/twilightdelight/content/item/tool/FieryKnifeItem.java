package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TwilightItemTier;

public class FieryKnifeItem extends TDKnifeItem {

	public FieryKnifeItem(Item.Properties p) {
		super(TwilightItemTier.FIERY, p.rarity(Rarity.UNCOMMON).fireResistant());
	}

	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment != Enchantments.FIRE_ASPECT && super.canApplyAtEnchantingTable(stack, enchantment);
	}

	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return !EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.FIRE_ASPECT) && super.isBookEnchantable(stack, book);
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		boolean result = super.hurtEnemy(stack, target, attacker);
		if (result && !target.getLevel().isClientSide() && !target.fireImmune()) {
			target.setSecondsOnFire(15);
		} else {
			for (int var1 = 0; var1 < 20; ++var1) {
				double px = target.getX() + (double) (target.getLevel().getRandom().nextFloat() * target.getBbWidth() * 2.0F) - (double) target.getBbWidth();
				double py = target.getY() + (double) (target.getLevel().getRandom().nextFloat() * target.getBbHeight());
				double pz = target.getZ() + (double) (target.getLevel().getRandom().nextFloat() * target.getBbWidth() * 2.0F) - (double) target.getBbWidth();
				target.getLevel().addParticle(ParticleTypes.FLAME, px, py, pz, 0.02D, 0.02D, 0.02D);
			}
		}
		return result;
	}

}

