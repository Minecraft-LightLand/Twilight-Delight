package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.TwilightForestMod;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(StoveBlockEntity.class)
public abstract class StoveBlockEntityMixin extends SyncedBlockEntity {

	@Shadow(remap = false)
	@Final
	private int[] cookingTimesTotal;

	public StoveBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	@Inject(at = @At(value = "RETURN", ordinal = 0), method = "addItem", remap = false)
	public void twilightdelight$addItem$doubleSpeed(ItemStack stack, CampfireCookingRecipe recipe, int slot, CallbackInfoReturnable<Boolean> cir) {
		if (getBlockState().getBlock() == TDBlocks.MAZE_STOVE.get()) {
			Item item = recipe.getResultItem().getItem();
			ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
			if (id != null && (id.getNamespace().equals(TwilightForestMod.ID) || id.getNamespace().equals(TwilightDelight.MODID))) {
				cookingTimesTotal[slot] = Math.min(200, cookingTimesTotal[slot]) / 2;
			}
		}
	}

}
