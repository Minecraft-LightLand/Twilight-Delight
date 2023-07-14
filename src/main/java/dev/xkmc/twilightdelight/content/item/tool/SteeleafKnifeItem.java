package dev.xkmc.twilightdelight.content.item.tool;

import com.tterrag.registrate.util.CreativeModeTabModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import twilightforest.util.TwilightItemTier;

public class SteeleafKnifeItem extends TDKnifeItem {

	public SteeleafKnifeItem(Properties properties) {
		super(TwilightItemTier.STEELEAF, properties);
	}

	public void fillItemCategory(CreativeModeTabModifier tab){
		tab.accept(getDefault());
	}

	public ItemStack getDefault() {
		ItemStack istack = getDefaultInstance();
		istack.enchant(Enchantments.MOB_LOOTING, 2);
		istack.enchant(Enchantments.BLOCK_FORTUNE, 2);
		return istack;
	}

}
