package dev.xkmc.twilightdelight.content.item.tool;

import com.tterrag.registrate.util.CreativeModeTabModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TFToolMaterials;

public class SteeleafKnifeItem extends TDKnifeItem {

	public SteeleafKnifeItem(Properties properties) {
		super(TFToolMaterials.STEELEAF, properties);
	}

	public void fillItemCategory(CreativeModeTabModifier tab) {
		tab.accept(getDefault(tab.getParameters().holders()));
	}

	public ItemStack getDefault(HolderLookup.Provider holders) {
		ItemStack istack = getDefaultInstance();
		istack.enchant(holders.holderOrThrow(Enchantments.LOOTING), 2);
		istack.enchant(holders.holderOrThrow(Enchantments.FORTUNE), 2);
		return istack;
	}

}
