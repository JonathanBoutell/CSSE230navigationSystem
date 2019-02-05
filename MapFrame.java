import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MapFrame {

	public static final int MAP_WIDTH = 1500;
	public static final int MAP_HEIGHT = 800;	
	
	public MapFrame() {
		JFrame frame = new JFrame();
		frame.setSize(MAP_WIDTH, MAP_HEIGHT);
		frame.setTitle("Navigation System");
		JPanel holder = new JPanel(new BorderLayout());
		JPanel selectionPanel = new JPanel(new GridLayout(0,6));
		
		JTextField startTextField = new JTextField(30);
		JTextField endTextField = new JTextField(30);
		String[] distOptionStrs = {"Minimize", "Maximize"};
		String[] diffOptionStrs = {"Green", "Blue", "Black", "Double Black"};
		JComboBox distanceOptions = new JComboBox(distOptionStrs);
		JComboBox difficultyOptions = new JComboBox(diffOptionStrs);
		distanceOptions.setSelectedIndex(0);
		difficultyOptions.setSelectedIndex(1);
		JCheckBox emergencyPhoneSelect = new JCheckBox("Find Nearest Emergency Phone");
		JCheckBox skiLiftSelect = new JCheckBox("Allow Ski Lifts");
		
		holder.setBackground(new Color(200,229,255));
		selectionPanel.setBackground(new Color(200,229,255));
		emergencyPhoneSelect.setBackground(new Color(200,229,255));
		skiLiftSelect.setBackground(new Color(200,229,255));
		
		selectionPanel.add(new JLabel("Start Point"));
		selectionPanel.add(new JLabel("End Point"));
		selectionPanel.add(new JLabel("Minimize or Maximize Distance"));
		selectionPanel.add(new JLabel("Maximum Allowable Difficulty"));
		selectionPanel.add(new JLabel(""));
		selectionPanel.add(new JLabel(""));
		
		selectionPanel.add(startTextField);
		selectionPanel.add(endTextField);
		selectionPanel.add(distanceOptions);
		selectionPanel.add(difficultyOptions);
		selectionPanel.add(emergencyPhoneSelect);
		selectionPanel.add(skiLiftSelect);
		
		frame.add(holder);
		holder.add(selectionPanel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
