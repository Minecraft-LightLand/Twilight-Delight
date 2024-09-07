package dev.xkmc.twilightdelight.content.block;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.StoveBlock;

import java.util.List;

public class MazeStoveBlock extends StoveBlock {

	public MazeStoveBlock(Properties builder) {
		super(builder);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}
}
