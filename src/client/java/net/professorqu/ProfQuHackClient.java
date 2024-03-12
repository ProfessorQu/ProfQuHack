package net.professorqu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.professorqu.modules.Flight;
import net.professorqu.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class ProfQuHackClient implements ClientModInitializer {
	private static final List<Module> modules = new ArrayList<>();

	public static MinecraftClient client;

	@Override
	public void onInitializeClient() {
		client = MinecraftClient.getInstance();

		modules.add(new Flight());

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			modules.forEach(Module::tick);
		});
	}

	public static <T extends Module> T getModule(Class<T> clazz) {
		for (var module : modules) {
			if (module.getClass() == clazz) {
				return clazz.cast(module);
			}
		}

		return clazz.cast(modules.get(0));
	}
}