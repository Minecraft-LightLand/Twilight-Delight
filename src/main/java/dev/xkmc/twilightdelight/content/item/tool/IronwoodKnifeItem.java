package dev.xkmc.twilightdelight.content.item.tool;

import com.tterrag.registrate.util.CreativeModeTabModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TwilightItemTier;

public class IronwoodKnifeItem extends TDKnifeItem {

	public IronwoodKnifeItem(Properties properties) {
		super(TwilightItemTier.IRONWOOD, properties);
	}

	public void fillItemCategory(CreativeModeTabModifier tab){
		tab.accept(getDefault());
	}

	public ItemStack getDefault() {
		ItemStack istack = getDefaultInstance();
		istack.enchant(Enchantments.KNOCKBACK, 1);
		istack.enchant(Enchantments.UNBREAKING, 1);
		return istack;
	}

}
