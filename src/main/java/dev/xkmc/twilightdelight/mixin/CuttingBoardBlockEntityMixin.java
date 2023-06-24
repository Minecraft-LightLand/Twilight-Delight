package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.util.FieryCuttingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.List;
import java.util.Optional;

@Mixin(CuttingBoardBlockEntity.class)
public abstract class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {

	@Shadow(remap = false)
	private ResourceLocation lastRecipeID;

	public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	//TODO use modify return value from MixinExtras
	@Inject(at = @At("HEAD"), method = "getMatchingRecipe", remap = false, cancellable = true)
	public void twilightdelight$getMatchingRecipe(RecipeWrapper recipeWrapper, ItemStack toolStack, Player player, CallbackInfoReturnable<Optional<CuttingBoardRecipe>> cir) {
		if (toolStack.is(TDItems.FIERY_KNIFE.get())) {
			Optional<CuttingBoardRecipe> old;
			// TODO start copy old method
			while (true) {
				if (this.level == null) {
					old = Optional.empty();
					break;
				} else {
					if (this.lastRecipeID != null) {
						Recipe<RecipeWrapper> recipe = ((RecipeManagerAccessor) this.level.getRecipeManager()).getRecipeMap(ModRecipeTypes.CUTTING.get()).get(this.lastRecipeID);
						if (recipe instanceof CuttingBoardRecipe && recipe.matches(recipeWrapper, this.level) && ((CuttingBoardRecipe) recipe).getTool().test(toolStack)) {
							old = Optional.of((CuttingBoardRecipe) recipe);
							break;
						}
					}

					List<CuttingBoardRecipe> recipeList = this.level.getRecipeManager().getRecipesFor(ModRecipeTypes.CUTTING.get(), recipeWrapper, this.level);
					if (recipeList.isEmpty()) {
						if (player != null) {
							player.displayClientMessage(TextUtils.getTranslation("block.cutting_board.invalid_item"), true);
						}
						old = Optional.empty();
						break;
					} else {
						Optional<CuttingBoardRecipe> recipe = recipeList.stream().filter((cuttingRecipe) -> cuttingRecipe.getTool().test(toolStack)).findFirst();
						if (recipe.isEmpty()) {
							if (player != null) {
								player.displayClientMessage(TextUtils.getTranslation("block.cutting_board.invalid_tool"), true);
							}
							old = Optional.empty();
							break;
						} else {
							this.lastRecipeID = recipe.get().getId();
							old = recipe;
							break;
						}
					}
				}
			}
			// TODO end copy old method
			cir.setReturnValue(FieryCuttingRecipe.transform(level, Vec3.atBottomCenterOf(getBlockPos()).add(0, 0.1, 0), old));
		}
	}


}
