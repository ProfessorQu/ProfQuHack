package net.professorqu.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import net.professorqu.mixin.VehicleMoveC2SPacketAccessor;

public class BoatFly extends Hack {
    private static final double FALL_DIST = 0.4;
    private static final int MAX_FLOATING_TICKS = 10;
    private double previousY;
    private int floatingTicks;

    public static final double speed = 1;
    public static final double upVelocity = 0.3;

    @Override
    public void tick() {
        var mc = MinecraftClient.getInstance();
        var player = mc.player;
        if (player == null) return;

        var vehicle = player.getVehicle();
        if (vehicle == null) return;

        // Set the velocity for going up
        double velY = mc.options.jumpKey.isPressed() ? upVelocity : vehicle.getVelocity().getY();

        // Move fast in the direction of the vehicle
        double velX = 0;
        double velZ = 0;
        if (mc.options.forwardKey.isPressed()) {
            Vec3d forward = Vec3d.fromPolar(0, vehicle.getYaw());

            velX = forward.x * speed;
            velZ = forward.z * speed;
        }

        vehicle.setVelocity(velX, velY, velZ);

        if (vehicle.getY() >= this.previousY - FALL_DIST)
            this.floatingTicks++;

        this.previousY = vehicle.getY();
    }

    @Override
    public boolean modifyPacket(Packet<?> packet) {
        if (!(packet instanceof VehicleMoveC2SPacket)) return false;

        // Go down a tiny bit to not get kicked for flying
        if (floatingTicks >= MAX_FLOATING_TICKS) {
            ((VehicleMoveC2SPacketAccessor) packet).setY(this.previousY - FALL_DIST);

            floatingTicks = 0;
        }

        return false;
    }
}
