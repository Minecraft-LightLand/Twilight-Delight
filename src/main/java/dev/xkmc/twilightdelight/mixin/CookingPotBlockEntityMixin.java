package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.TwilightForestMod;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

@Mixin(CookingPotBlockEntity.class)
public abstract class CookingPotBlockEntityMixin extends SyncedBlockEntity {

	@Shadow(remap = false)
	private int cookTime;

	public CookingPotBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	@Inject(at = @At("HEAD"), method = "processCooking", remap = false)
	public void twilightdelight$processCooking$fasterCooking(CookingPotRecipe recipe, CookingPotBlockEntity cookingPot, CallbackInfoReturnable<Boolean> cir) {
		if (level == null) return;
		BlockState below = level.getBlockState(getBlockPos().below());
		if (below.getBlock() == TDBlocks.MAZE_STOVE.get()) {
			Item item = recipe.getResultItem().getItem();
			ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
			if (id != null && (id.getNamespace().equals(TwilightForestMod.ID) || id.getNamespace().equals(TwilightDelight.MODID))) {
				int total = Math.max(1, Math.min(200, recipe.getCookTime()) / 2);
				int factor = recipe.getCookTime() / total;
				cookTime += factor - 1;
			}
		}
	}

}
