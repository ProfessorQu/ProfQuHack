package net.professorqu.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Jesus extends Hack {
    @Override
    public void tick() {
        var client = MinecraftClient.getInstance();
        var player = client.player;
        if (player == null) return;
        if (client.options.sneakKey.isPressed()) return;

        if (player.isTouchingWater() || player.isInLava()) {
            Vec3d velocity = player.getVelocity();
            player.setVelocity(velocity.getX(), 0.5, velocity.getZ());
        }
    }
}
