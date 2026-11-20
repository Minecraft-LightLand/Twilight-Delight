package dev.xkmc.twilightdelight.content.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ForgeTier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class WroughtIronSwordItem extends SwordItem {

	public static final Tier TIER = new ForgeTier(2, 250, 6f, 4f, 14,
			BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));

	private static final UUID REACH_UUID = UUID.fromString("9f6f7c2e-3b1a-4c5d-8e9f-0a1b2c3d4e5f");

	public WroughtIronSwordItem(Item.Properties p) {
		super(TIER, 3, -2.4f, p);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			return ImmutableMultimap.<Attribute, AttributeModifier>builder()
					.putAll(super.getDefaultAttributeModifiers(slot))
					.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(REACH_UUID,
							"Wrought iron sword reach", 1, AttributeModifier.Operation.ADDITION))
					.build();
		}
		return super.getDefaultAttributeModifiers(slot);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}

}
