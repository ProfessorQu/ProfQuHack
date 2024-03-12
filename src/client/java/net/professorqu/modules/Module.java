package net.professorqu.modules;

public abstract class Module {
    private boolean enabled = false;

    public void toggle() {
        enabled = !enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void tick() { }
}
