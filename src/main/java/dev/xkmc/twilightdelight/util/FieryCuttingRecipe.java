package dev.xkmc.twilightdelight.util;

import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FieryCuttingRecipe extends CuttingBoardRecipe {

	@SuppressWarnings({"OptionalUsedAsFieldOrParameterType"})
	public static Optional<CuttingBoardRecipe> transform(Level level, Vec3 pos, Optional<CuttingBoardRecipe> optional) {
		if (optional.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new FieryCuttingRecipe(level, pos, optional.get()));
	}

	private static Ingredient extract(NonNullList<Ingredient> ingredients) {
		return ingredients.size() == 0 ? Ingredient.EMPTY : ingredients.get(0);
	}

	private final Level level;
	private final Vec3 pos;

	private FieryCuttingRecipe(Level level, Vec3 pos, CuttingBoardRecipe recipe) {
		super(recipe.getId(), recipe.getGroup(), extract(recipe.getIngredients()), recipe.getTool(), recipe.getRollableResults(), recipe.getSoundEventID());
		this.level = level;
		this.pos = pos;
	}

	@Override
	public List<ItemStack> rollResults(RandomSource rand, int fortuneLevel) {
		List<ItemStack> prev = super.rollResults(rand, fortuneLevel);
		List<ItemStack> ans = new ArrayList<>();
		int particle = 0;
		for (ItemStack stack : prev) {
			SimpleContainer cont = new SimpleContainer(stack);
			var opt = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, cont, level);
			if (opt.isEmpty()) {
				ans.add(stack);
			} else {
				ItemStack result = opt.get().assemble(cont);
				result.setCount(stack.getCount());
				ans.add(result);
				particle += stack.getCount();
			}
		}
		if (particle > 0) {
			spawnParticlesAndPlaySound(particle);
		}
		return ans;
	}

	private void spawnParticlesAndPlaySound(int particle) {
		spawnCuttingParticles(particle);
		playSound(SoundEvents.FIRE_EXTINGUISH, 1, 1);
	}

	private void playSound(SoundEvent sound, float volume, float pitch) {
		if (this.level != null) {
			this.level.playSound(null, pos.x(), pos.y(), pos.z(), sound, SoundSource.BLOCKS, volume, pitch);
		}

	}

	private void spawnCuttingParticles(int count) {
		for (int i = 0; i < count; ++i) {
			Vec3 vec3d = new Vec3((level.random.nextFloat() - 0.5D) * 0.1D, level.random.nextFloat() * 0.05D, (level.random.nextFloat() - 0.5D) * 0.1D);
			if (level instanceof ServerLevel sl) {
				sl.sendParticles(ParticleTypes.FLAME, pos.x(), pos.y(), pos.z(), 1, vec3d.x, vec3d.y, vec3d.z, 0.0D);
			} else {
				level.addParticle(ParticleTypes.FLAME, pos.x(), pos.y(), pos.z(), vec3d.x, vec3d.y, vec3d.z);
			}
		}

	}

}
