package dev.xkmc.twilightdelight.init.registrate;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TDTab extends CreativeModeTab {

	public TDTab() {
		super("tab." + TwilightDelight.MODID);
	}

	@Override
	public @NotNull ItemStack makeIcon() {
		return TDBlocks.MAZE_STOVE.asStack();
	}

}
