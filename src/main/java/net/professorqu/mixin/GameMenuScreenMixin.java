package net.professorqu.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.professorqu.gui.HackScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
	protected GameMenuScreenMixin(Text title) {
		super(title);
	}

	/**
	 * Create and add the button to go to the screen to enable/disable hacks
	 * @param ci the callback info from the injection
	 */
	@Inject(method = "initWidgets", at = @At("HEAD"))
	void initWidgets(CallbackInfo ci) {
		var buttonWidget = ButtonWidget.builder(
				Text.translatable("hacks.title"),
				button -> {
					if (MinecraftClient.getInstance() != null) {
						MinecraftClient.getInstance().setScreen(new HackScreen(this));
					}
				}
		).position(10, 10).build();

		this.addDrawableChild(buttonWidget);
	}
}