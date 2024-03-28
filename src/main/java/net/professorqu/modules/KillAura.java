package net.professorqu.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class KillAura extends Hack {
    private static int ticksPassedSinceAttack = 0;

    @Override
    public void tick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        var player = mc.player;
        if (player == null) return;

        World world = mc.world;
        if (world == null) return;

        Entity closestEntity = findClosestEntity(world, player);
        if (closestEntity == null) return;

        if (ticksPassedSinceAttack >= player.getAttackCooldownProgressPerTick()) {
            player.attack(closestEntity);
            player.networkHandler.getConnection().send(PlayerInteractEntityC2SPacket.attack(closestEntity, player.isSneaking()));

            ticksPassedSinceAttack = 0;
        } else {
            ticksPassedSinceAttack++;
        }
    }

    /**
     * Find the closest entity in the world to player
     * @param world     the clientside world with all the entities
     * @param player    the player to get the closest to
     * @return          the closest entity to {@code player} in {@code world}
     */
    private static Entity findClosestEntity(World world, PlayerEntity player) {
        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        // Create the box
        Vec3d entityPos = player.getPos();
        final double REACH = PlayerEntity.getReachDistance(player.isCreative());
        Vec3d reachVec = new Vec3d(REACH, REACH, REACH);

        Vec3d pos1 = entityPos.subtract(reachVec);
        Vec3d pos2 = entityPos.add(reachVec);

        Box box = new Box(pos1, pos2);

        // Find entities
        List<Entity> entities = world.getOtherEntities(
            player, box,
            // Make sure we are hitting a living entity that is alive
            otherEntity -> (otherEntity instanceof LivingEntity) && otherEntity.isAlive()
        );

        // Loop over all entities found
        for (Entity otherEntity : entities) {
            double distance = entityPos.distanceTo(otherEntity.getPos());

            if (distance < closestDistance) {
                closestEntity = otherEntity;
                closestDistance = distance;
            }
        }

        // If we can reach them, return it.
        if (closestDistance <= REACH) {
            return closestEntity;
        } else {
            return null;
        }
    }
}
