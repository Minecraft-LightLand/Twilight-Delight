package dev.xkmc.twilightdelight.content.item.tool;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.List;

public class WroughtIronSwordItem extends SwordItem {

	public static final Tier TIER = new SimpleTier(
			BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6f, 4f, 14,
			() -> Ingredient.of(Items.IRON_INGOT));

	private static final ResourceLocation REACH_ID = TwilightDelight.loc("wrought_iron_sword");

	public WroughtIronSwordItem(Item.Properties p) {
		super(TIER, p.attributes(SwordItem.createAttributes(TIER, 3, -2.4f)
				.withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE,
						new AttributeModifier(REACH_ID, 1, AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)));
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext world, List<Component> list, TooltipFlag flags) {
		super.appendHoverText(stack, world, list, flags);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}

}
