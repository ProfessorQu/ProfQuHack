package net.professorqu.modules;

import net.minecraft.network.packet.Packet;

public abstract class Hack {
    protected boolean enabled = false;

    /**
     * Toggle the hack
     */
    public void toggle() {
        this.enabled = !this.enabled;
    }

    /**
     * Check if the hack is enabled
     * @return true if the hack is enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Modify the input packet in some way
     * @param packet the packet to modify
     */
    public void modifyPacket(Packet<?> packet) {}

    /**
     * Is called at {@code START_CLIENT_TICK}
     */
    public void tick() {}
    /**
     * Is called at {@code END_CLIENT_TICK}
     */
    public void postTick() {}
}
