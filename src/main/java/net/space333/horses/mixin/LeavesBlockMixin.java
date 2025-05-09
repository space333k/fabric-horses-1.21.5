package net.space333.horses.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Makes leaves collision shape work for controlled entities like scaffolding.
 */
@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    // Allow the vehicle to both move through and walk on top of leaves
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext) {
            // If completely above the leaf block, treat as solid to allow standing on
            if (context.isAbove(VoxelShapes.fullCube(), pos, true) && !context.isDescending()) return super.getCollisionShape(state, world, pos, context);
                // If not, treat as empty
            else if (entityContext.getEntity() != null && entityContext.getEntity().hasControllingPassenger()) return VoxelShapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    // Prevent the camera from getting stuck on leaves
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext) {
            // If camera-owning entity (player) is controlling a vehicle, treat as empty
            if (entityContext.getEntity() != null && entityContext.getEntity().getControllingVehicle() != null) return VoxelShapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    // Slow down the vehicle when moving though leaves

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (entity.hasControllingPassenger()) entity.slowMovement(state, new Vec3d(0.85, 1, 0.85));
    }
}
