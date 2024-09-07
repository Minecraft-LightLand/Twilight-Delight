package dev.xkmc.twilightdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.TwilightForestMod;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

import java.util.Optional;

@Mixin(CookingPotBlockEntity.class)
public abstract class CookingPotBlockEntityMixin extends SyncedBlockEntity implements HeatableBlockEntity {

	@Shadow(remap = false)
	private int cookTime;

	@Shadow(remap = false)
	protected abstract boolean hasInput();

	@Shadow(remap = false)
	public abstract boolean isHeated();

	@Shadow(remap = false)
	protected abstract Optional<CookingPotRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper);

	@Shadow(remap = false)
	@Final
	private ItemStackHandler inventory;

	@Shadow(remap = false)
	protected abstract boolean canCook(CookingPotRecipe recipe);

	@Shadow(remap = false)
	protected abstract boolean processCooking(RecipeHolder<CookingPotRecipe> recipe, CookingPotBlockEntity cookingPot);

	@Shadow(remap = false)
	private int cookTimeTotal;

	@Shadow(remap = false)
	public abstract ItemStack getMeal();

	@Shadow(remap = false)
	protected abstract boolean doesMealHaveContainer(ItemStack meal);

	@Shadow(remap = false)
	protected abstract void moveMealToOutput();

	@Shadow(remap = false)
	protected abstract void useStoredContainersOnMeal();

	public CookingPotBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	@Inject(at = @At("HEAD"), method = "processCooking", remap = false)
	public void twilightdelight$processCooking$fasterCooking(RecipeHolder<CookingPotRecipe> recipe, CookingPotBlockEntity cookingPot, CallbackInfoReturnable<Boolean> cir) {
		if (level == null) return;
		BlockState below = level.getBlockState(getBlockPos().below());
		boolean fiery = getBlockState().is(TDBlocks.FIERY_POT.get());
		boolean maze = below.getBlock() == TDBlocks.MAZE_STOVE.get();
		if (fiery || maze) {
			Item item = recipe.value().getResultItem(level.registryAccess()).getItem();
			ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
			if ((id.getNamespace().equals(TwilightForestMod.ID) || id.getNamespace().equals(TwilightDelight.MODID))) {
				int time = recipe.value().getCookTime();
				if (fiery) time = Math.min(200, time);
				if (maze) time /= 2;
				int total = Math.max(1, time);
				int factor = recipe.value().getCookTime() / total;
				cookTime += factor - 1;
			}
		}

	}

	@Inject(at = @At("HEAD"), method = "isHeated", remap = false, cancellable = true)
	public void twilightdelight$isHeated$fieryPot(CallbackInfoReturnable<Boolean> cir) {
		if (getBlockState().is(TDBlocks.FIERY_POT.get())) {
			cir.setReturnValue(true);
		}
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lvectorwing/farmersdelight/common/block/entity/CookingPotBlockEntity;isHeated(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"), method = {"cookingTick", "animationTick"})
	private static boolean twilightdelight$animationTick$isHeated(CookingPotBlockEntity instance, Level level, BlockPos pos, Operation<Boolean> original) {
		if (instance.getBlockState().is(TDBlocks.FIERY_POT.get())) {
			return true;
		}
		return original.call(instance, level, pos);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = {"getMealFromItem", "takeServingFromItem", "getContainerFromItem"})
	private static boolean twilightDelight$getMealFromItem$fiery(ItemStack stack, Item item, Operation<Boolean> old) {
		return stack.is(TDBlocks.FIERY_POT.asItem()) || old.call(stack, item);
	}

}
