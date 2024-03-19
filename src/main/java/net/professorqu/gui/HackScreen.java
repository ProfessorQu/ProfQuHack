package net.professorqu.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.professorqu.ProfQuHack;
import net.professorqu.modules.Hack;

import java.util.HashMap;
import java.util.Map;

public class HackScreen extends Screen {
    private final Screen parent;

    private final Map<ButtonWidget, Class<? extends Hack>> toggleableHackButtons = new HashMap<>();

    public HackScreen(Screen parent) {
        super(Text.translatable("hacks.title"));
        this.parent = parent;
    }

    /**
     * Initialize the screen
     */
    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        // Add hack buttons
        for (var hack : ProfQuHack.getHacks()) {
            this.toggleableHackButtons.put(adder.add(ButtonWidget.builder(
                            Text.empty(),
                            button -> ProfQuHack.toggle(hack.getClass())
                    ).build()),
                    hack.getClass()
            );
        }

        adder.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).width(200).build(), 2, adder.copyPositioner().marginTop(6));

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    /**
     * Render the screen
     * @param context   the drawing context
     * @param mouseX    the X position of the mouse
     * @param mouseY    the Y position of the mouse
     * @param delta     delta time
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Change the text on the buttons depending on if the hacks are enabled
        for (var entry : this.toggleableHackButtons.entrySet()) {
            var button = entry.getKey();
            var clazz = entry.getValue();

            button.setMessage(buttonText(clazz));
        }

        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * A simple utility function to generate the enabled button text for a hack
     * @param clazz the hack to get the button text for
     * @return      the generated text for the button
     */
    private Text buttonText(Class<? extends Hack> clazz) {
        var classText = Text.translatable("hacks." + clazz.getSimpleName().toLowerCase()).append(" ");
        var isText = Text.translatable("hacks.is").append(" ");
        var enabledText = Text.translatable(ProfQuHack.isEnabled(clazz) ? "hacks.enabled" : "hacks.disabled");

        var text = classText.append(isText).append(enabledText);

        return text;
    }
}
