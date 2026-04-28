package dev.xkmc.twilightdelight.content.block;

import com.mojang.serialization.MapCodec;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.registry.ModSounds;

import javax.annotation.Nullable;

public class MazeStoveBlock extends AbstractStoveBlock {

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}

	public MazeStoveBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MazeStoveBlockEntity(TDBlocks.MAZE_BE.get(), pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> bet) {
		return level.isClientSide && state.getValue(LIT) ?
				createTickerHelper(bet, TDBlocks.MAZE_BE.get(), MazeStoveBlockEntity::particleTick) :
				createStoveTicker(level, bet, TDBlocks.MAZE_BE.get());
	}

	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(CampfireBlock.LIT)) {
			double x = pos.getX() + 0.5F;
			double y = pos.getY();
			double z = pos.getZ() + 0.5F;
			if (random.nextInt(10) == 0) {
				level.playLocalSound(x, y, z, ModSounds.BLOCK_STOVE_CRACKLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}

			Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
			Direction.Axis direction$axis = direction.getAxis();
			double horizontalOffset = random.nextDouble() * 0.6 - 0.3;
			double xOffset = direction$axis == Direction.Axis.X ? direction.getStepX() * 0.52 : horizontalOffset;
			double yOffset = random.nextDouble() * 6.0F / 16.0F;
			double zOffset = direction$axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : horizontalOffset;
			level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0F, 0.0F, 0.0F);
			level.addParticle(ParticleTypes.FLAME, x + xOffset, y + yOffset, z + zOffset, 0.0F, 0.0F, 0.0F);
		}
	}
}