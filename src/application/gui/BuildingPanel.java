package application.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.io.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;

public class BuildingPanel extends JPanel implements ActionListener {

    private final int WINDOWX = 570;
    private final int WINDOWY = 360;
    
    static final int NTABLES = 5;
    
    private Image bufferImage;
    private Dimension bufferSize;
    
    String name;
    private List<Gui> guis = new ArrayList<Gui>();

    public BuildingPanel(String buildName) {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setLayout(null);
        bufferSize = this.getSize();
        name = buildName;
    	Timer timer = new Timer(20, this );
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
    	
        Graphics2D g2 = (Graphics2D)g;
        //Clear the screen by painting a rectangle the size of the frame
        //g2.setColor(getBackground());
        if(name.toLowerCase().contains("restaurant"))
        	g2.setColor(Color.YELLOW);
        else
        	g2.setColor(Color.LIGHT_GRAY);
        
        g2.fillRect(0, 0, WINDOWX, WINDOWY );	
        g2.setColor(Color.RED);
        g.drawString(name, 10, 10);
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
        
    }

    public void addGui(Gui gui) {
        guis.add(gui);
        
    }
    
    public String toString() {
    	return name;
    }
}
