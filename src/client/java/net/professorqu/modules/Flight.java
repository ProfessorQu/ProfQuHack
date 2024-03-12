package net.professorqu.modules;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.professorqu.ProfQuHack;
import net.professorqu.ProfQuHackClient;
import net.professorqu.mixin.ClientConnectionAccessor;

public class Flight extends Module {
    private static final double FALL_DIST = 0.45;

    private int floating_ticks = 0;
    private double previousY;

    @Override
    public void toggle() {
        super.toggle();

        var player = ProfQuHackClient.client.player;
        if (player == null)
            return;

        player.getAbilities().allowFlying = this.isEnabled();

        previousY = player.getY();
    }

    @Override
    public void tick() {
        var player = ProfQuHackClient.client.player;
        if (player == null)
            return;

        if (player.getAbilities().flying && player.getY() >= this.previousY - FALL_DIST) {
            this.floating_ticks++;
        }

        if (this.floating_ticks >= 20) {
            ((ClientConnectionAccessor)ProfQuHackClient.client.getNetworkHandler().getConnection()).sendIm(
                    new PlayerMoveC2SPacket.PositionAndOnGround(player.getX(), player.getY() - FALL_DIST, player.getZ(), false),
                    null,
                    true);

            ProfQuHack.LOGGER.warn("Sending packet");

            this.floating_ticks = 0;
        }
    }
}
