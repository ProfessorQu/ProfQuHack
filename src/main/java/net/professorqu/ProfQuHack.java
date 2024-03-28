package net.professorqu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
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
		HACKS.add(new XRay());
		HACKS.add(new NoWeather());
		HACKS.add(new AntiHunger());
		HACKS.add(new KillAura());
//		HACKS.add(new Listener());

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

		HudRenderCallback.EVENT.register((drawContext, delta) -> {
			if (MinecraftClient.getInstance().getDebugHud().shouldShowDebugHud()) return;
			drawHud(drawContext);
		});
	}

	/**
	 * Draw the HUD
	 * @param drawContext	the context of drawing
	 */
	private static void drawHud(DrawContext drawContext) {
		var player = MinecraftClient.getInstance().player;
		if (player == null) return;

		var textRenderer = MinecraftClient.getInstance().textRenderer;

		drawWatermark(drawContext, textRenderer, player.age);

		var index = 1;
		for (var hack : getHacks()) {
			if (!hack.isEnabled()) continue;

			float hue = ((float) player.age / 200f + (float) index / (ProfQuHack.numHacks() + 1)) % 1f;
			var color = MathHelper.hsvToRgb(hue, 1f, 1f);

			drawContext.drawText(
				textRenderer,
//				Text.translatable("hacks.", hack.getClass().getSimpleName().toLowerCase()),
				ProfQuHack.getTranslatableText(hack.getClass()),
				10, 25 + index * 10,
				color, true
			);

			index++;
		}
	}

	/**
	 * Draw the watermark on the hud
	 * @param drawContext	the context of drawing
	 * @param textRenderer	the renderer of text
	 * @param age			the age of the player
	 */
	private static void drawWatermark(DrawContext drawContext, TextRenderer textRenderer, int age) {
		float hue = ((float) age / 200f) % 1f;
		var color = MathHelper.hsvToRgb(hue, 1f, 1f);

		var scale = 1.5f;

		var matrices = drawContext.getMatrices().peek().getPositionMatrix();
		matrices.scale(scale);

		textRenderer.draw(
			Text.translatable("hacks.title"),
			10, 10,
			color, true,
			matrices,
			drawContext.getVertexConsumers(),
			TextRenderer.TextLayerType.NORMAL,
			0, 0xF000F0
		);

		matrices.scale(1f / scale);
		drawContext.draw();
	}

	/**
	 * Get the hack from {@code HACKS} that matches the class {@code hackClass}
	 * @param 	hackClass the hack to get
	 * @return	the matching hack from {@code HACKS}
	 * @param 	<T> extends Hack
	 */
	public static <T extends Hack> T getHack(Class<T> hackClass) {
		for (var hack : getHacks()) {
			if (hack.getClass() == hackClass) {
				return hackClass.cast(hack);
			}
		}

		return null;
	}

	/**
	 * Checks if the hack with class {@code hackClass} is enabled
	 * @param 	hackClass the class to search for
	 * @return	whether the hack is enabled
	 */
	public static boolean isEnabled(Class<? extends Hack> hackClass) {
		var hack = getHack(hackClass);
		return hack != null && hack.isEnabled();
	}

	/**
	 * Toggles enabled for the hack with class {@code hackClass}
	 * @param hackClass the class to toggle
	 */
	public static void toggle(Class<? extends Hack> hackClass) {
		var hack = getHack(hackClass);
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

	/**
	 * Get the translatable text from the given {@code hackclass}
	 * @param hackClass	the class to give the translatable text for
	 * @return			the translatable text
	 */
	public static Text getTranslatableText(Class<? extends Hack> hackClass) {
		return Text.translatable("hacks." + hackClass.getSimpleName().toLowerCase());
	}

	public static int numHacks() {
		return getHacks().size();
	}
}