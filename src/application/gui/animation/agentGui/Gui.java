package application.gui.animation.agentGui;

import java.awt.*;

import application.Phonebook;

public interface Gui {

    public void updatePosition();
    public void draw(Graphics2D g);
    public boolean isPresent();
}
