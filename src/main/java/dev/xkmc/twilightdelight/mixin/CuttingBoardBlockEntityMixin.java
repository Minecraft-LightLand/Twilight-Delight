package dev.xkmc.twilightdelight.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.util.FieryCuttingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.Optional;

@Mixin(CuttingBoardBlockEntity.class)
public abstract class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {

	@Shadow(remap = false)
	private ResourceLocation lastRecipeID;

	public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	@ModifyReturnValue(at = @At(value = "RETURN", ordinal = 1), method = "getMatchingRecipe")
	public Optional<RecipeHolder<CuttingBoardRecipe>> twilightdelight$getMatchingRecipe(Optional<RecipeHolder<CuttingBoardRecipe>> original, @Local(argsOnly = true) ItemStack toolStack) {
		if (toolStack.is(TDItems.FIERY_KNIFE.get())) {
			return FieryCuttingRecipe.transform(level, Vec3.atBottomCenterOf(getBlockPos()).add(0, 0.1, 0), original);
		}
		return original;
	}


}
