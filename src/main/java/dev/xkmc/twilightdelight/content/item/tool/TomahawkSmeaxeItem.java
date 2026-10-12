package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;
import vectorwing.farmersdelight.common.item.KnifeItem;

/**
 * A tomahawk carved out of a steak, bone handle and all. It swings like a knife for 10 damage,
 * but the blade never wears down from fighting: eating is what takes it off the bone, one bite
 * at a time, and once the last bite is gone all that is left is the bone it was served on.
 * <p>
 * The remaining bites live in the stack's custom data rather than in vanilla durability, so the
 * item can still be eaten into instead of shattering, and the bar is drawn from them directly.
 */
public class TomahawkSmeaxeItem extends KnifeItem {

	/** Absent tag means "never used", so a fresh stack reads as full. */
	public static final String USES_TAG = "RemainingUses";

	/** The stat line is flat, so the tier carries no attack damage bonus of its own. */
	public static final Tier TIER = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, 250, 1.6F, 0F, 10,
			() -> Ingredient.EMPTY);

	private final int maxUses;

	public TomahawkSmeaxeItem(int maxUses, Properties properties) {
		super(TIER, properties.stacksTo(1)
				.attributes(DiggerItem.createAttributes(TIER, 10, -3.0f)));
		this.maxUses = maxUses;
	}

	/** Carries the whole stack data across, so enchantments and remaining bites survive cooking. */
	public static void copyData(ItemStack from, ItemStack to) {
		to.applyComponents(from.getComponents());
	}

	public int getMaxUses() {
		return maxUses;
	}

	public int getRemainingUses(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data == null) return maxUses;
		CompoundTag tag = data.copyTag();
		if (!tag.contains(USES_TAG)) return maxUses;
		return Mth.clamp(tag.getInt(USES_TAG), 0, maxUses);
	}

	private void setRemainingUses(ItemStack stack, int uses) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		CompoundTag tag = data == null ? new CompoundTag() : data.copyTag();
		tag.putInt(USES_TAG, uses);
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getRemainingUses(stack) < maxUses;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round(13.0F * getRemainingUses(stack) / maxUses);
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return Mth.hsvToRgb(getRemainingUses(stack) / (3.0F * maxUses), 1.0F, 1.0F);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
		super.finishUsingItem(stack, level, consumer);
		// creative mode leaves the stack alone, so no bite is spent there
		if (!stack.isEmpty()) return stack;
		stack.setCount(1);
		int left = getRemainingUses(stack) - 1;
		if (left <= 0) return new ItemStack(Items.BONE);
		setRemainingUses(stack, left);
		return stack;
	}

}