package application.gui.appView.controlPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import application.*;
import application.gui.*;
import application.gui.appView.*;
import application.gui.trace.PrintControlPanel;

public class ControlPanel extends JPanel {
	JPanel panel4 = new JPanel();
	JPanel panel5 = new JPanel();
	//Add Panel
	private AddPanel addP;
	private EditPanel editP;
	private DashboardPanel dashboardP;
	
	//Trace Panel
	private PrintPanel printPanel;
	private JPanel printPanelTab;
	
	private ImageIcon picture;
	private JPanel tempPanel = new JPanel();
	private JTabbedPane ControlPane = new JTabbedPane();
	private Application app;
	private ApplicationPanel appPanel;
	
	public ControlPanel(ApplicationPanel appPanel, Application app){
		setLayout(new GridLayout(1,1));
		this.app = app;
		this.appPanel = appPanel;
		addP = new AddPanel(this, app);
		editP = new EditPanel(this, app, appPanel);
		dashboardP = new DashboardPanel(app);
		
		printPanel = new PrintPanel();
		printPanelTab = printPanel;
		
		
		ControlPane.addTab("Dashboard", dashboardP);
		ControlPane.addTab("Add Person", addP);
		ControlPane.addTab("Edit Panel", editP);
		ControlPane.addTab("Select Building", panel4);
		ControlPane.addTab("Trace Panel", printPanelTab);
		
		
		add(ControlPane);
	
	}	
	
	public ApplicationPanel getAppPanel(){
		return appPanel;
	}
	
	public EditPanel getEditPanel(){
		return editP;
	}
}
