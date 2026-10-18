package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A bow carved out of an antler. It is light enough to draw twice as fast as a normal bow:
 * full draw, and with it full shot power, arrives in 10 ticks instead of 20. That is not a
 * property of the bow itself but of the use countdown, so it comes from the use-tick handler
 * in {@code GeneralEventHandlers}; all that is left to do here is say so on the tooltip.
 */
public class AntlerBowItem extends BowItem {

	public AntlerBowItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
		super.appendHoverText(stack, world, list, flags);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}

}