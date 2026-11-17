package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Food that drops a handful of experience orbs when eaten, like Twilight Forest's essence berry.
 * The experience is spawned as orbs rather than granted directly so it still has to be picked up.
 */
public class XPFoodItem extends TDFoodItem {

	private final int minXP, maxXP;

	public XPFoodItem(Properties props, int minXP, int maxXP) {
		super(props);
		this.minXP = minXP;
		this.maxXP = maxXP;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (!level.isClientSide) {
			int xp = minXP + level.random.nextInt(maxXP - minXP + 1);
			level.addFreshEntity(new ExperienceOrb(level, entity.getX(), entity.getY() + 0.5, entity.getZ(), xp));
		}
		return super.finishUsingItem(stack, level, entity);
	}

}
