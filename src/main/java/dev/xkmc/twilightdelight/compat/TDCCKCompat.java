package dev.xkmc.twilightdelight.compat;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import plus.dragons.createcentralkitchen.content.contraptions.components.actor.FDHarvesterMovementBehaviorExtensions;
import plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;

@ModLoadSubscriber(modid = "farmersdelight")
public class TDCCKCompat {

	public static void registerCCK() {
		HarvesterMovementBehaviourExtension.REGISTRY.put(TDBlocks.MUSHGLOOM_COLONY.get(), FDHarvesterMovementBehaviorExtensions::harvestMushroomColony);

	}

}
