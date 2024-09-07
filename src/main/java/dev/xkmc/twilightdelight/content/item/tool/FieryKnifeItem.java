package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import twilightforest.util.TFToolMaterials;

import javax.annotation.Nullable;
import java.util.List;

public class FieryKnifeItem extends TDKnifeItem {

	public FieryKnifeItem(Item.Properties p) {
		super(TFToolMaterials.FIERY, p.rarity(Rarity.UNCOMMON).fireResistant());
	}

	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment != Enchantments.FIRE_ASPECT && super.canApplyAtEnchantingTable(stack, enchantment);
	}

	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return !EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.FIRE_ASPECT) && super.isBookEnchantable(stack, book);
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		boolean result = super.hurtEnemy(stack, target, attacker);
		if (result && !target.level().isClientSide() && !target.fireImmune()) {
			target.setSecondsOnFire(15);
		} else {
			for (int var1 = 0; var1 < 20; ++var1) {
				double px = target.getX() + (double) (target.level().getRandom().nextFloat() * target.getBbWidth() * 2.0F) - (double) target.getBbWidth();
				double py = target.getY() + (double) (target.level().getRandom().nextFloat() * target.getBbHeight());
				double pz = target.getZ() + (double) (target.level().getRandom().nextFloat() * target.getBbWidth() * 2.0F) - (double) target.getBbWidth();
				target.level().addParticle(ParticleTypes.FLAME, px, py, pz, 0.02D, 0.02D, 0.02D);
			}
		}
		return result;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
		super.appendHoverText(stack, world, list, flags);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}

}

