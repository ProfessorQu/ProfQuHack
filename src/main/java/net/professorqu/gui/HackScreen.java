package net.professorqu.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.professorqu.ProfQuHack;

public class HackScreen extends Screen {
    public HackScreen() {
        super(Text.translatable("hacks.title"));
    }

    /**
     * Initialize the screen
     */
    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(4);

        // Add hack buttons
        for (var hack : ProfQuHack.getHacks()) {
            adder.add(new ToggleHackButton(hack.getClass()));
        }

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridWidget.forEachChild(this::addDrawableChild);
    }
}
