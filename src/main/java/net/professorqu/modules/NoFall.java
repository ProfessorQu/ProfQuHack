package net.professorqu.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.professorqu.mixin.PlayerMoveC2SPacketAccessor;

public class NoFall extends Hack {
    @Override
    public void modifyPacket(Packet<?> packet) {
        if (!(packet instanceof PlayerMoveC2SPacket)) return;

        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        if (player.getAbilities().flying) {
            ((PlayerMoveC2SPacketAccessor) packet).setOnGround(true);
        }
        else {
            // Allow Elytra Flying
            if (player.isFallFlying()) return;
            // Don't kill the player when NoFall is turned on too late
            if (player.getVelocity().getY() > -0.5) return;
            ((PlayerMoveC2SPacketAccessor) packet).setOnGround(true);
        }
    }
}
