package net.professorqu.mixin;

import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VehicleMoveC2SPacket.class)
public interface VehicleMoveC2SPacketAccessor {
    @Mutable
    @Accessor("y")
    void setY(double y);
}
