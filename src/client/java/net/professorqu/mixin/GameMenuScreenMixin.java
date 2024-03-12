package net.professorqu.mixin;

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
	protected GameMenuScreenMixin() {
		super(Text.of(""));
	}

	@Inject(at = @At("HEAD"), method = "initWidgets")
	private void run(CallbackInfo info) {
		var hacksButton = ButtonWidget.builder(Text.of("ProfQuHack"), button -> {
			if (this.client != null)
				this.client.setScreen(new HackScreen(this));
		}).position(10, 10).build();

		this.addDrawableChild(hacksButton);
	}
}