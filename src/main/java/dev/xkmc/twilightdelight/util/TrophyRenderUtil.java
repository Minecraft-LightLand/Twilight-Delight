package dev.xkmc.twilightdelight.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import twilightforest.init.TFBlocks;
import twilightforest.item.TrophyItem;

public class TrophyRenderUtil {

	public static void translate(TrophyItem item, PoseStack poseStack) {
		boolean is_ghast = item == TFBlocks.UR_GHAST_TROPHY.get().asItem();
		if (is_ghast) {
			poseStack.mulPose(Axis.XP.rotationDegrees(90));
			poseStack.translate(0, 0.7, 0);
		} else poseStack.translate(0, 0.12, 0);
	}
}
