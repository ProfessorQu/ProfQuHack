package net.professorqu.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.professorqu.ProfQuHackClient;
import net.professorqu.modules.Flight;
import net.professorqu.modules.Module;

import java.util.HashMap;
import java.util.Map;

public class HackScreen extends Screen {
    private final Screen parent;

    private final Map<ButtonWidget, Class<? extends Module>> buttons = new HashMap<>();

    public HackScreen(Screen parent) {
        super(Text.of("ProfQu Hack"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.initWidgets();
    }

    private void initWidgets() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        buttons.put(adder.add(ButtonWidget.builder(
                Text.of("Flight is"),
                button -> ProfQuHackClient.getModule(Flight.class).toggle()
        ).build()), Flight.class);

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        buttons.forEach(((buttonWidget, aClass) -> {
            var string = aClass.getSimpleName().toString() + " is " + enabledString(aClass);
            buttonWidget.setMessage(Text.of(string));
        }));

        super.render(context, mouseX, mouseY, delta);
    }

    private static String enabledString(Class<? extends Module> module) {
        return ProfQuHackClient.getModule(module).isEnabled() ? "enabled" : "disabled";
    }
}
