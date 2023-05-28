package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.content.item.tool.FieryKnifeItem;
import dev.xkmc.twilightdelight.util.FieryClickCuttingBoardUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

/**
 * @author Goulixiaoji
 */
@Mixin(CuttingBoardBlock.class)
public class CuttingBoardBlockMixin {

	/**
	 * @author Goulixiaoji
	 * @reason net.twilightdelight.common.item.knife.FieryKnife used twice
	 */

	@Inject(at = @At("HEAD"), method = "use", cancellable = true)
	public void use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		ItemStack stack$ = player.getItemInHand(handIn);
		if (stack$.getItem() instanceof FieryKnifeItem) {
			BlockEntity blockEntity = worldIn.getBlockEntity(pos);
			if (blockEntity instanceof CuttingBoardBlockEntity cuttingBoardBlockEntity && !cuttingBoardBlockEntity.isEmpty()) {
				FieryClickCuttingBoardUtil.clickBoard(stack$, pos, player);
				cir.setReturnValue(InteractionResult.SUCCESS);
				cir.cancel();
			}
		}
	}

}