package net.professorqu.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.professorqu.ProfQuHack;
import net.professorqu.modules.NoWeather;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @Inject(method = "hasPrecipitation", at = @At("HEAD"), cancellable = true)
    void hasPrecipitation(CallbackInfoReturnable<Boolean> cir) {
        if (ProfQuHack.isEnabled(NoWeather.class)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
