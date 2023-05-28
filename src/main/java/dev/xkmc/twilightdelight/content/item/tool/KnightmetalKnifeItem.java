package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import twilightforest.util.TwilightItemTier;

import javax.annotation.Nullable;
import java.util.List;

public class KnightmetalKnifeItem extends TDKnifeItem {

	public KnightmetalKnifeItem(Properties prop) {
		super(TwilightItemTier.KNIGHTMETAL, prop);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
		super.appendHoverText(stack, world, list, flags);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}

}
