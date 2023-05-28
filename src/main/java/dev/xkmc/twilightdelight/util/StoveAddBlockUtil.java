package dev.xkmc.twilightdelight.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;

public class StoveAddBlockUtil {

	public static void addBlock(BlockEntityType<?> type, Block block) {
		Set<Block> set = new HashSet<>(type.validBlocks);
		set.add(block);
		type.validBlocks = set;
	}

}
