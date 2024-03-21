package net.professorqu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.professorqu.modules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProfQuHack implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("profquhack");
	private static final List<Hack> HACKS = new ArrayList<>();

	@Override
	public void onInitialize() {
		HACKS.add(new Flight());
		HACKS.add(new NoFall());
		HACKS.add(new BoatFly());
		HACKS.add(new Speed());
		HACKS.add(new Step());
		HACKS.add(new Jesus());
		HACKS.add(new SuperJump());
		HACKS.add(new FullBright());

		ClientTickEvents.START_CLIENT_TICK.register(client ->
			HACKS.forEach(hack -> {
				if (hack.isEnabled())
					hack.tick();
			})
		);

		ClientTickEvents.END_CLIENT_TICK.register(client ->
			HACKS.forEach(hack -> {
				if (hack.isEnabled())
					hack.postTick();
			})
		);
	}

	/**
	 * Get the hack from {@code HACKS} that matches the class {@code clazz}
	 * @param 	clazz the hack to get
	 * @return	the matching hack from {@code HACKS}
	 * @param 	<T> extends Hack
	 */
	public static <T extends Hack> T getHack(Class<T> clazz) {
		for (var hack : HACKS) {
			if (hack.getClass() == clazz) {
				return clazz.cast(hack);
			}
		}

		return null;
	}

	/**
	 * Checks if the hack with class {@code clazz} is enabled
	 * @param 	clazz the class to search for
	 * @return	whether the hack is enabled
	 */
	public static boolean isEnabled(Class<? extends Hack> clazz) {
		var hack = getHack(clazz);
		return hack != null && hack.isEnabled();
	}

	/**
	 * Toggles enabled for the hack with class {@code clazz}
	 * @param clazz the class to toggle
	 */
	public static void toggle(Class<? extends Hack> clazz) {
		var hack = getHack(clazz);
		if (hack != null) {
			hack.toggle();
		}
	}

	/**
	 * Gets a list of loaded hacks
	 * @return a list of hacks
	 */
	public static List<Hack> getHacks() {
		return HACKS;
	}
}