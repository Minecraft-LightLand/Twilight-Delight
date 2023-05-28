package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TwilightItemTier;

public class IronwoodKnifeItem extends TDKnifeItem {

	public IronwoodKnifeItem(Properties properties) {
		super(TwilightItemTier.IRONWOOD, properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (allowedIn(tab)) {
			list.add(getDefault());
		}
	}

	public ItemStack getDefault() {
		ItemStack istack = getDefaultInstance();
		istack.enchant(Enchantments.KNOCKBACK, 1);
		return istack;
	}

}
