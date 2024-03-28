package net.professorqu.mixin;

import net.minecraft.world.World;
import net.professorqu.ProfQuHack;
import net.professorqu.modules.NoWeather;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
    @Inject(method = "getRainGradient", at = @At("HEAD"), cancellable = true)
    private void getRainGradient(float delta, CallbackInfoReturnable<Float> cir) {
        if (ProfQuHack.isEnabled(NoWeather.class)) {
            cir.setReturnValue(0.0f);
            cir.cancel();
        }
    }
}
