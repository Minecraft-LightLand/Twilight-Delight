package dev.xkmc.twilightdelight.init.data;

import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;

public class AdvancementGen {

	public static void genAdvancements(RegistrateAdvancementProvider pvd) {
		pvd.accept(Advancement.Builder.advancement()
				.display(DelightFood.DEADBUG_STICK.item.get(),
						pvd.title(TwilightDelight.MODID, "no_more_bugs", "No More Bugs"),
						pvd.desc(TwilightDelight.MODID, "no_more_bugs", "Eat a skewer of bugs"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("eat_deadbug_stick",
						ConsumeItemTrigger.TriggerInstance.usedItem(DelightFood.DEADBUG_STICK.item.get()))
				.build(TwilightDelight.loc("no_more_bugs")));
	}

}