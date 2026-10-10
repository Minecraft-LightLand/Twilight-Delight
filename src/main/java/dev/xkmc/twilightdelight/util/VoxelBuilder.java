//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package dev.xkmc.twilightdelight.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/**
 * Builds one cube in block space (1/16 units, north facing) and rotates it around the Y axis,
 * so blocks with a horizontal facing can derive their shape from a single north facing one.
 */
public class VoxelBuilder {
	private final Vec3 lo;
	private final Vec3 hi;

	public VoxelBuilder(int x0, int y0, int z0, int x1, int y1, int z1) {
		this.lo = (new Vec3(x0, y0, z0)).subtract(8.0F, 8.0F, 8.0F);
		this.hi = (new Vec3(x1, y1, z1)).subtract(8.0F, 8.0F, 8.0F);
	}

	public VoxelShape rotateFromNorth(Direction dir) {
		float angle = (float)(((180.0F - dir.toYRot()) / 180.0F) * Math.PI);
		Vec3 xlo = this.lo.yRot(angle).add(8.0F, 8.0F, 8.0F);
		Vec3 xhi = this.hi.yRot(angle).add(8.0F, 8.0F, 8.0F);
		AABB bound = new AABB(xlo, xhi);
		return Block.box(bound.minX, bound.minY, bound.minZ, bound.maxX, bound.maxY, bound.maxZ);
	}

	/**
	 * Rotates every cube towards each horizontal direction and joins them into one shape.
	 * The result is indexed by {@link Direction#get2DDataValue()}.
	 */
	public static VoxelShape[] buildRotatedShapes(List<VoxelBuilder> cubes) {
		VoxelShape[] rotated = new VoxelShape[4];
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			VoxelShape shape = null;
			for (VoxelBuilder cube : cubes) {
				VoxelShape box = cube.rotateFromNorth(dir);
				shape = shape == null ? box : Shapes.or(shape, box);
			}
			rotated[dir.get2DDataValue()] = shape == null ? Shapes.empty() : shape;
		}
		return rotated;
	}
}
