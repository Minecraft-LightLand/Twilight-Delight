package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

@SerialClass
public class BaseEffectRecipe<T extends BaseEffectRecipe<T>> extends BaseRecipe<T, BaseEffectRecipe<?>, WorldInv> {

	@SerialClass.SerialField
	public Ingredient ingredient;

	@SerialClass.SerialField
	public ItemStack result;

	public BaseEffectRecipe(ResourceLocation id, BaseRecipe.RecType<T, BaseEffectRecipe<?>, WorldInv> type) {
		super(id, type);
	}

	@Override
	public boolean matches(WorldInv worldInv, Level level) {
		return ingredient.test(worldInv.getItem(0));
	}

	@Override
	public ItemStack assemble(WorldInv worldInv, RegistryAccess access) {
		var ans = result.copy();
		ans.setCount(result.getCount() * worldInv.getItem(0).getCount());
		return ans;
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return result;
	}

}
