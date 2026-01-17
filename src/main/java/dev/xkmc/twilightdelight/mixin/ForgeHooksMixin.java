package dev.xkmc.twilightdelight.mixin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dev.xkmc.twilightdelight.events.GeneralEventHandlers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgeHooks.class)
public class ForgeHooksMixin {

	@Inject(method = "loadLootTable", at = @At("HEAD"), cancellable = true, remap = false)
	private static void twilightdelight$removeLogErrors(Gson gson, ResourceLocation name, JsonElement data, boolean custom, CallbackInfoReturnable<LootTable> cir) {
		if (GeneralEventHandlers.skipLootTable(name)) {
			cir.setReturnValue(null);
		}
	}

}
