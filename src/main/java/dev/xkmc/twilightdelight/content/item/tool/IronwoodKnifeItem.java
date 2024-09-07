package dev.xkmc.twilightdelight.content.item.tool;

import com.tterrag.registrate.util.CreativeModeTabModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TFToolMaterials;

public class IronwoodKnifeItem extends TDKnifeItem {

	public IronwoodKnifeItem(Properties properties) {
		super(TFToolMaterials.IRONWOOD, properties);
	}

	public void fillItemCategory(CreativeModeTabModifier tab) {
		tab.accept(getDefault(tab.getParameters().holders()));
	}

	public ItemStack getDefault(HolderLookup.Provider holders) {
		ItemStack istack = getDefaultInstance();
		istack.enchant(holders.holderOrThrow(Enchantments.KNOCKBACK), 1);
		istack.enchant(holders.holderOrThrow(Enchantments.UNBREAKING), 1);
		return istack;
	}

}
