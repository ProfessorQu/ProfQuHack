package net.professorqu.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.professorqu.mixin.PlayerMoveC2SPacketAccessor;

public class Flight extends Hack {
    private static final double FALL_DIST = 0.4;
    private static final int MAX_FLOATING_TICKS = 10;
    private double previousY;
    private int floatingTicks;

    @Override
    public void toggle() {
        super.toggle();

        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        player.getAbilities().allowFlying = this.enabled;
        if (!this.enabled) {
            player.getAbilities().flying = false;
        }

        this.previousY = player.getY();
        this.floatingTicks = 0;
    }

    @Override
    public boolean modifyPacket(Packet<?> packet) {
        if (!(packet instanceof PlayerMoveC2SPacket)) return false;

        // Go down a tiny bit to not get kicked for flying
        if (floatingTicks >= MAX_FLOATING_TICKS) {
            ((PlayerMoveC2SPacketAccessor) packet).setY(this.previousY - FALL_DIST);

            floatingTicks = 0;
        }

        return false;
    }

    @Override
    public void tick() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        player.getAbilities().allowFlying = this.enabled;

        if (!player.getAbilities().flying) return;

        if (player.getY() >= this.previousY - FALL_DIST)
            this.floatingTicks++;

        this.previousY = player.getY();
    }
}
