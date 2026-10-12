package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.content.item.tool.TomahawkSmeaxeItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCookingRecipe.class)
public abstract class AbstractCookingRecipeMixin {

	/**
	 * A tomahawk smeaxe is the same steak before and after cooking, so the furnace hands back
	 * the same blade it was given: its enchantments and its remaining bites ride along instead of
	 * being wiped by a fresh result stack.
	 */
	@Inject(at = @At("RETURN"), method = "assemble", require = 0)
	private void twilightdelight$assemble$keepSmeaxeData(SingleRecipeInput input, HolderLookup.Provider registries,
														CallbackInfoReturnable<ItemStack> cir) {
		ItemStack result = cir.getReturnValue();
		ItemStack meat = input.item();
		if (meat.getItem() instanceof TomahawkSmeaxeItem && result.getItem() instanceof TomahawkSmeaxeItem) {
			TomahawkSmeaxeItem.copyData(meat, result);
		}
	}

}