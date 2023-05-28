package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.twilightdelight.content.block.FierySnakesBlock;
import dev.xkmc.twilightdelight.content.block.LilyChickenBlock;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

public class TDBlocks {

	public static BlockEntry<FierySnakesBlock> FIERY_SNAKES = TwilightDelight.REGISTRATE.block(
					"fiery_snakes_block", p -> new FierySnakesBlock())
			.blockstate((ctx, pvd) -> {
			}).simpleItem().register();//TODO loot

	public static BlockEntry<LilyChickenBlock> LILY_CHICKEN = TwilightDelight.REGISTRATE.block(
					"lily_chicken_block", p -> new LilyChickenBlock())
			.blockstate((ctx, pvd) -> {
			}).simpleItem().register();

	public static BlockEntry<StoveBlock> MAZE_STOVE = TwilightDelight.REGISTRATE.block(
					"maze_stove", p -> new StoveBlock(
							BlockBehaviour.Properties.copy(Blocks.BRICKS)
									.lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)))
			.blockstate((ctx, pvd) -> {
			}).tag(ModTags.HEAT_SOURCES, BlockTags.MINEABLE_WITH_PICKAXE).simpleItem().register();

	public static void register() {

	}

}
