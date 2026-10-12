package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.content.item.tool.TomahawkSmeaxeItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
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
	private void twilightdelight$assemble$keepSmeaxeData(Container container, RegistryAccess access,
														CallbackInfoReturnable<ItemStack> cir) {
		ItemStack result = cir.getReturnValue();
		ItemStack input = container.getItem(0);
		if (input.getItem() instanceof TomahawkSmeaxeItem && result.getItem() instanceof TomahawkSmeaxeItem) {
			TomahawkSmeaxeItem.copyData(input, result);
		}
	}

}