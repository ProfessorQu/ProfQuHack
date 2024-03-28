package net.professorqu.mixin;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.professorqu.ProfQuHack;
import net.professorqu.modules.Jesus;
import net.professorqu.modules.XRay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract FluidState getFluidState();

    @Shadow public abstract Block getBlock();

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (getFluidState().isEmpty()) return;

        var client = MinecraftClient.getInstance();
        if (client == null) return;
        if (client.options.sneakKey.isPressed()) return;

        var player = client.player;
        if (player == null) return;
        if (player.isTouchingWater()) return;

        if (ProfQuHack.isEnabled(Jesus.class)) {
            cir.setReturnValue(VoxelShapes.fullCube());
            cir.cancel();
        }
    }

    @Inject(method="getLuminance", at=@At("HEAD"), cancellable = true)
    private void getLuminance(CallbackInfoReturnable<Integer> cir) {
        if (ProfQuHack.isEnabled(XRay.class) && !XRay.isInteresting(this.getBlock())) {
            cir.setReturnValue(15);
            cir.cancel();
        }
    }

    @Inject(method="getAmbientOcclusionLightLevel", at=@At("HEAD"), cancellable = true)
    private void getAmbientOcclusionLightLevel(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (ProfQuHack.isEnabled(XRay.class)) {
            cir.setReturnValue(1.0f);
            cir.cancel();
        }
    }

    @Inject(method="isOpaque", at=@At("HEAD"), cancellable = true)
    private void isOpaque(CallbackInfoReturnable<Boolean> cir) {
        if (ProfQuHack.isEnabled(XRay.class)) {
            cir.setReturnValue(XRay.isInteresting(this.getBlock()));
            cir.cancel();
        }
    }

    @Inject(method="getRenderType", at=@At("HEAD"), cancellable = true)
    private void getRenderType(CallbackInfoReturnable<BlockRenderType> cir) {
        if (ProfQuHack.isEnabled(XRay.class) && !XRay.isInteresting(this.getBlock())) {
            cir.setReturnValue(BlockRenderType.INVISIBLE);
            cir.cancel();
        }
    }
}
