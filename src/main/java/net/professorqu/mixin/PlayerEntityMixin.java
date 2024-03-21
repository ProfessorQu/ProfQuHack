package net.professorqu.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.professorqu.ProfQuHack;
import net.professorqu.modules.Speed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if (ProfQuHack.isEnabled(Speed.class)) {
            cir.setReturnValue(Speed.movementSpeed);
            cir.cancel();
        }
    }
}
