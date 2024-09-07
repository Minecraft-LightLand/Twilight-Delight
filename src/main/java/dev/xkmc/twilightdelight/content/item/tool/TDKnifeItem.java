package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class TDKnifeItem extends KnifeItem {

	public TDKnifeItem(Tier tier, Properties properties) {
		super(tier, properties.attributes(KnifeItem.createAttributes(tier, 0.5f, -2)));
	}

	@Override
	public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
		return enchantment.is(Enchantments.FORTUNE) || super.isPrimaryItemFor(stack, enchantment);
	}

}
