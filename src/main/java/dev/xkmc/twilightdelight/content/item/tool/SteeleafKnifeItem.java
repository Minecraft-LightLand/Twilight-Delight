package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TwilightItemTier;

public class SteeleafKnifeItem extends TDKnifeItem {

	public SteeleafKnifeItem(Properties properties) {
		super(TwilightItemTier.STEELEAF, properties);
	}

	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (this.allowedIn(tab)) {
			ItemStack stack = new ItemStack(this);
			stack.enchant(Enchantments.MOB_LOOTING, 2);
			items.add(stack);
		}

	}

}
