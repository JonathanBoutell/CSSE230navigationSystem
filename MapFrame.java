import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapFrame {	
	PathFinder pathFinder;
	MapComponent mapComponent = new MapComponent();
	
	public MapFrame() {
		// initialize the frame, panels, and nodes
		try {pathFinder = new PathFinder();} catch (FileNotFoundException e) {}
		mapComponent.addNodeList(pathFinder.getAllNodes());
		JFrame frame = new JFrame();
		frame.setTitle("Navigation System");
		JPanel holder = new JPanel(new BorderLayout());
		JPanel selectionPanel = new JPanel(new GridLayout(0,6));
		
		ArrayList<String> temp = new ArrayList<String>();
		for (String key : pathFinder.getNodes().keySet()) {
			temp.add(key);
		}
		temp.add("");
		Object[] mapNodes = temp.toArray();
		
		//initialize all buttons
		JComboBox startTextField = new JComboBox(mapNodes);
		startTextField.setSelectedItem("");
		JComboBox endTextField = new JComboBox(mapNodes);
		endTextField.setSelectedItem("");
		String[] distOptionStrs = {"Minimize", "Maximize"};
		String[] diffOptionStrs = {"Green", "Blue", "Black", "Double Black"};
		JComboBox distanceOptions = new JComboBox(distOptionStrs);
		JComboBox difficultyOptions = new JComboBox(diffOptionStrs);
		distanceOptions.setSelectedIndex(0);
		difficultyOptions.setSelectedIndex(1);
		JCheckBox firstAidSelect = new JCheckBox("Find Nearest First Aid Station");
		JCheckBox skiLiftSelect = new JCheckBox("Do Not Allow Ski Lifts");
		JButton getDirectionsButton = new JButton("Get Directions");
		getDirectionsButton.addActionListener(new DirectionsListener(startTextField,endTextField,distanceOptions,difficultyOptions,firstAidSelect,skiLiftSelect));
		mapComponent.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// find the closest node to the mouse click and select it as the start or end point
				double x = e.getX()/Main.SCALE_FACTOR;
				double y = e.getY()/Main.SCALE_FACTOR + 700;
				double closestDist = Integer.MAX_VALUE;
				MapNode node = null;
				for (MapNode n : pathFinder.getAllNodes()) {
					double dist = Math.sqrt(Math.pow(n.getDrawX()-x, 2) + Math.pow(n.getDrawY()-y, 2));
					if (dist < closestDist){
						closestDist = dist;
						node = n;
					}
				}
				if (startTextField.getSelectedItem() == "") {
					startTextField.setSelectedItem(node.getName());
				} else {
					endTextField.setSelectedItem(node.getName());
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		holder.setBackground(new Color(200,229,255));
		selectionPanel.setBackground(new Color(200,229,255));
		firstAidSelect.setBackground(new Color(200,229,255));
		skiLiftSelect.setBackground(new Color(200,229,255));
		
		//add buttons and labels
		selectionPanel.add(new JLabel("Start Point"));
		selectionPanel.add(new JLabel("End Point"));
		selectionPanel.add(new JLabel("Minimize or Maximize Distance"));
		selectionPanel.add(new JLabel("Maximum Allowable Difficulty"));
		selectionPanel.add(firstAidSelect);
		selectionPanel.add(new JLabel(""));
		
		selectionPanel.add(startTextField);
		selectionPanel.add(endTextField);
		selectionPanel.add(distanceOptions);
		selectionPanel.add(difficultyOptions);
		selectionPanel.add(skiLiftSelect);
		selectionPanel.add(getDirectionsButton);
		
		//add everything to the frame and make it visible
		frame.add(holder);
		holder.add(mapComponent,BorderLayout.CENTER);
		mapComponent.repaint();
		holder.add(selectionPanel, BorderLayout.NORTH);
		frame.setSize(Main.MAP_WIDTH, Main.MAP_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private class DirectionsListener implements ActionListener {
		JComboBox startTextField;
		JComboBox endTextField;
		JComboBox distanceOptions;
		JComboBox difficultyOptions;
		JCheckBox firstAidSelect;
		JCheckBox skiLiftSelect;
		Path path;
		
		public DirectionsListener(JComboBox startTextField, JComboBox endTextField, JComboBox distanceOptions,
				JComboBox difficultyOptions, JCheckBox firstAidSelect, JCheckBox skiLiftSelect) {
			this.startTextField = startTextField;
			this.endTextField = endTextField;
			this.distanceOptions = distanceOptions;
			this.difficultyOptions = difficultyOptions;
			this.firstAidSelect = firstAidSelect;
			this.skiLiftSelect = skiLiftSelect;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String start = (String) startTextField.getSelectedItem();
			String end = (String) endTextField.getSelectedItem();
			if (firstAidSelect.isSelected()) {
				path = pathFinder.findNearestFirstAidStation(start);
			} else {
				boolean allowSkiLift = !skiLiftSelect.isSelected();
				boolean findMaxDistance = (distanceOptions.getSelectedItem().equals("Minimize"))?false:true;
				double maxDifficulty = 0;
				switch (difficultyOptions.getSelectedItem().toString()) {
				case "Green":
					maxDifficulty = 1;
					break;
				case "Blue":
					maxDifficulty = 2;
					break;
				case "Black":
					maxDifficulty = 3;
					break;
				case "Double Black":
					maxDifficulty = 4;
				default:
					break;
				}
				path = pathFinder.runAStar(findMaxDistance, allowSkiLift, start, end, maxDifficulty);
			}
			mapComponent.setPath(path);
			mapComponent.repaint();
			new DirectionsFrame(path).display();
		}
	}
}
