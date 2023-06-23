package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;

@Mixin(CookingPotMenu.class)
public abstract class CookingPotMenuMixin extends RecipeBookMenu<RecipeWrapper> {

	@Shadow
	@Final
	private ContainerLevelAccess canInteractWithCallable;

	public CookingPotMenuMixin(MenuType<?> pMenuType, int pContainerId) {
		super(pMenuType, pContainerId);
	}

	@Inject(at = @At("HEAD"), method = "stillValid", cancellable = true)
	public void twilightdelight$stillValid$AddBlock(Player player, CallbackInfoReturnable<Boolean> cir) {
		if (stillValid(canInteractWithCallable, player, TDBlocks.FIERY_POT.get())) {
			cir.setReturnValue(true);
		}
	}

}
