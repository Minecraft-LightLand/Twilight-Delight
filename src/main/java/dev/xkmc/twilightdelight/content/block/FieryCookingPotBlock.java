package dev.xkmc.twilightdelight.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

public class FieryCookingPotBlock extends CookingPotBlock {

	public FieryCookingPotBlock(Properties properties) {
		super(properties);
	}

	@Override
	public String getDescriptionId() {
		return super.getDescriptionId();
	}

	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		ItemStack stack = super.getCloneItemStack(level, pos, state);
		var ans = new ItemStack(this);
		ans.applyComponents(stack.getComponentsPatch());
		return ans;
	}

}
