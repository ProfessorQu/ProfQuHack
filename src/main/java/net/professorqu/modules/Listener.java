package net.professorqu.modules;

import net.minecraft.network.packet.Packet;
import net.professorqu.ProfQuHack;

public class Listener extends Hack {
    @Override
    public boolean modifyPacket(Packet<?> packet) {
        ProfQuHack.LOGGER.info("Sent Packet: " + packet.getClass());

        return false;
    }
}
