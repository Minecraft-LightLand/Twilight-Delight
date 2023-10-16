package dev.xkmc.twilightdelight.mixin;

import com.google.gson.JsonElement;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootTables.class)
public class LootTablesMixin {

	@Inject(at = @At("HEAD"), method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V")
	public void twilightdelight$removeLootTable(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler, CallbackInfo ci) {
		map.keySet().removeIf(e -> {
			if (!e.getNamespace().equals(TwilightDelight.MODID))
				return false;
			if (!e.getPath().startsWith("blocks/"))
				return false;
			ResourceLocation id = new ResourceLocation(TwilightDelight.MODID, e.getPath().substring(7));
			return !ForgeRegistries.BLOCKS.containsKey(id);
		});
	}

}
