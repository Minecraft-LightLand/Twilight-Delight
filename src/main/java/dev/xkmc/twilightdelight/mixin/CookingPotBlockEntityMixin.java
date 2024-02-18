package dev.xkmc.twilightdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2library.util.code.Wrappers;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.TwilightForestMod;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;

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
	protected abstract boolean processCooking(CookingPotRecipe recipe, CookingPotBlockEntity cookingPot);

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
	public void twilightdelight$processCooking$fasterCooking(CookingPotRecipe recipe, CookingPotBlockEntity cookingPot, CallbackInfoReturnable<Boolean> cir) {
		if (level == null) return;
		BlockState below = level.getBlockState(getBlockPos().below());
		boolean fiery = getBlockState().is(TDBlocks.FIERY_POT.get());
		boolean maze = below.getBlock() == TDBlocks.MAZE_STOVE.get();
		if (fiery || maze) {
			Item item = recipe.getResultItem().getItem();
			ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
			if (id != null && (id.getNamespace().equals(TwilightForestMod.ID) || id.getNamespace().equals(TwilightDelight.MODID))) {
				int time = recipe.getCookTime();
				if (fiery) time = Math.min(200, time);
				if (maze) time /= 2;
				int total = Math.max(1, time);
				int factor = recipe.getCookTime() / total;
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

	//TODO should use API
	@Inject(at = @At("HEAD"), method = "cookingTick", remap = false, cancellable = true)
	private static void twilightDelight$cookingTick$impl(Level level, BlockPos pos, BlockState state, CookingPotBlockEntity self, CallbackInfo ci) {
		if (state.is(TDBlocks.FIERY_POT.get())) {
			CookingPotBlockEntityMixin cookingPot = Wrappers.cast(self);
			boolean isHeated = cookingPot.isHeated();
			boolean didInventoryChange = false;
			if (isHeated && cookingPot.hasInput()) {
				Optional<CookingPotRecipe> recipe = cookingPot.getMatchingRecipe(new RecipeWrapper(cookingPot.inventory));
				if (recipe.isPresent() && cookingPot.canCook(recipe.get())) {
					didInventoryChange = cookingPot.processCooking(recipe.get(), self);
				} else {
					cookingPot.cookTime = 0;
				}
			} else if (cookingPot.cookTime > 0) {
				cookingPot.cookTime = Mth.clamp(cookingPot.cookTime - 2, 0, cookingPot.cookTimeTotal);
			}

			ItemStack mealStack = cookingPot.getMeal();
			if (!mealStack.isEmpty()) {
				if (!cookingPot.doesMealHaveContainer(mealStack)) {
					cookingPot.moveMealToOutput();
					didInventoryChange = true;
				} else if (!cookingPot.inventory.getStackInSlot(7).isEmpty()) {
					cookingPot.useStoredContainersOnMeal();
					didInventoryChange = true;
				}
			}

			if (didInventoryChange) {
				cookingPot.inventoryChanged();
			}
			ci.cancel();
		}
	}

	//TODO should use API
	@Inject(at = @At("HEAD"), method = "animationTick", remap = false, cancellable = true)
	private static void twilightDelight$animationTick$impl(Level level, BlockPos pos, BlockState state, CookingPotBlockEntity self, CallbackInfo ci) {
		if (state.is(TDBlocks.FIERY_POT.get())) {
			CookingPotBlockEntityMixin cookingPot = Wrappers.cast(self);
			if (cookingPot.isHeated()) {
				RandomSource random = level.random;
				double x;
				double y;
				double z;
				if (random.nextFloat() < 0.2F) {
					x = (double) pos.getX() + 0.5D + (random.nextDouble() * 0.6D - 0.3D);
					y = (double) pos.getY() + 0.7D;
					z = (double) pos.getZ() + 0.5D + (random.nextDouble() * 0.6D - 0.3D);
					level.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0.0D, 0.0D, 0.0D);
				}

				if (random.nextFloat() < 0.05F) {
					x = (double) pos.getX() + 0.5D + (random.nextDouble() * 0.4D - 0.2D);
					y = (double) pos.getY() + 0.5D;
					z = (double) pos.getZ() + 0.5D + (random.nextDouble() * 0.4D - 0.2D);
					double motionY = random.nextBoolean() ? 0.015D : 0.005D;
					level.addParticle(ModParticleTypes.STEAM.get(), x, y, z, 0.0D, motionY, 0.0D);
				}
			}
			ci.cancel();
		}
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = "getMealFromItem")
	private static boolean twilightDelight$getMealFromItem$fiery(ItemStack stack, Item item, Operation<Boolean> old) {
		return stack.is(TDBlocks.FIERY_POT.get().asItem()) || old.call(stack, item);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = "takeServingFromItem")
	private static boolean twilightDelight$takeServingFromItem$fiery(ItemStack stack, Item item, Operation<Boolean> old) {
		return stack.is(TDBlocks.FIERY_POT.get().asItem()) || old.call(stack, item);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = "getContainerFromItem")
	private static boolean twilightDelight$getContainerFromItem$fiery(ItemStack stack, Item item, Operation<Boolean> old) {
		return stack.is(TDBlocks.FIERY_POT.get().asItem()) || old.call(stack, item);
	}

}
