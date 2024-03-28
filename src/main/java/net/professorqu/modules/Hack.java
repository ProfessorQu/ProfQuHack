package net.professorqu.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.packet.Packet;
import net.professorqu.ProfQuHack;
import org.lwjgl.glfw.GLFW;

public abstract class Hack {
    protected boolean enabled = false;

    public final KeyBinding keyBinding;

    public Hack() {
        this.keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.hacks." + this.getClass().getSimpleName().toLowerCase(),
            GLFW.GLFW_KEY_UNKNOWN,
            "category.hacks.hacks"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.keyBinding.wasPressed())
                ProfQuHack.toggle(this.getClass());
        });
    }

    /**
     * Toggle the hack
     */
    public void toggle() {
        this.enabled = !this.enabled;
    }

    /**
     * Check if the hack is enabled
     * @return  true if the hack is enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Modify the input packet in some way
     * @param packet    the packet to modify
     */
    public boolean modifyPacket(Packet<?> packet) {
        return false;
    }

    /**
     * Is called at {@code START_CLIENT_TICK}
     */
    public void tick() { }

    /**
     * Is called at {@code END_CLIENT_TICK}
     */
    public void postTick() { }
}
