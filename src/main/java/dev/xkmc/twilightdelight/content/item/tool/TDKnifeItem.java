package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class TDKnifeItem extends KnifeItem {

	public TDKnifeItem(Tier tier, Properties properties) {
		super(tier, 0.5f, -2, properties);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.BLOCK_FORTUNE || super.canApplyAtEnchantingTable(stack, enchantment);
	}
}
