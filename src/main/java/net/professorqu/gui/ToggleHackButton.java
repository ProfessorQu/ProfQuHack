package net.professorqu.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.math.ColorHelper;
import net.professorqu.ProfQuHack;
import net.professorqu.modules.Hack;

public class ToggleHackButton extends ButtonWidget {
    private final Class<? extends Hack> hackClass;

    public ToggleHackButton(Class<? extends Hack> hackClass) {
        super(0, 0,
            DEFAULT_WIDTH_SMALL, DEFAULT_HEIGHT,
            ProfQuHack.getTranslatableText(hackClass),
            button -> ProfQuHack.toggle(hackClass),
            DEFAULT_NARRATION_SUPPLIER
        );

        this.hackClass = hackClass;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(
            this.getX(), this.getY(),
            this.getX() + this.getWidth(), this.getY() + DEFAULT_HEIGHT,
            this.color()
        );

        this.drawMessage(context, MinecraftClient.getInstance().textRenderer, 0xFFFFFFFF);
    }

    private int color() {
        int blue = ProfQuHack.isEnabled(this.hackClass) ? 255 : 100;
        return ColorHelper.Argb.getArgb(255, 100, 100, blue);
    }
}
