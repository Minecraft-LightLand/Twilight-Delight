package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(SkilletBlockEntity.class)
public abstract class SkilletBlockEntityMixin extends SyncedBlockEntity {

	@Shadow(remap = false)
	private int cookingTime;

	public SkilletBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	@Inject(at = @At("HEAD"), method = "cookAndOutputItems", remap = false)
	public void twilightdelight$cookAndOutputItem$mazeStove(ItemStack cookingStack, Level level, CallbackInfo ci) {
		if (level != null && level.getBlockState(getBlockPos().below()).is(TDBlocks.MAZE_STOVE.get())) {
			cookingTime++;
		}

	}

}
