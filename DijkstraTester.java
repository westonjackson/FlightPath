/*
This is the tester class for Dijkstra's algorithm
by Weston Jackson
wjj2106
*/

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DijkstraTester{
	public static void main(String args[]){
	try{
	
	//import files
	File cityPairs = new File("citypairs.txt");
	File cityxy = new File("cityxy.txt");
	Dijkstra d = new Dijkstra(cityPairs);
	
	//get string of cities for JComboBox
	String[] cities = new String[d.size()];
	cities = d.getCities();
	
	@SuppressWarnings("unchecked")
	JComboBox start = new JComboBox(cities);
	@SuppressWarnings("unchecked")
	JComboBox end = new JComboBox(cities);
	
	JTextArea distance = new JTextArea("TOTAL DISTANCE");
	
	//create dimensions for component
	int width = 700;
	int height = 500;
	mapComponent map = new mapComponent(cityxy, d, width, height);
	map.setPreferredSize(new Dimension(width,height));
	JScrollPane mapPane = new JScrollPane(map);
	
	//create button to make path
	JButton find = new JButton("Get path");
	find.addActionListener(new DijkstraListener(start, end, map, d, distance));
	
	JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout());
	panel.add(start);
	panel.add(end);
	panel.add(find);
	panel.add(distance);
	
	JFrame frame = new JFrame("Dijkstra!");
	frame.setLayout(new BorderLayout());
	frame.add(mapPane, BorderLayout.NORTH);
	frame.add(panel, BorderLayout.SOUTH);
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

	
	catch(FileNotFoundException e){
		System.out.println("Incorrect file name(s).");
	}
	
	catch(ArrayIndexOutOfBoundsException e){
		System.out.println("Two files are required as command line arguments");
	}
	
	}
	
	//Action listener subclass for Dijkstra buttons
	public static class DijkstraListener implements ActionListener{
		
		private JComboBox start;
		private JComboBox end;
		private mapComponent map;
		private Dijkstra d;
		private JTextArea distance;
		
		//Initialize variables
		public DijkstraListener(JComboBox s, JComboBox e, mapComponent m, Dijkstra d, JTextArea dist)
		{
			start = s;
			end = e;
			map = m;
			this.d = d;
			distance = dist;
		}
		
		
		public void actionPerformed(ActionEvent ae){
			
			//Displays shortest path
			if(ae.getActionCommand().equals("Get path")){
			
				//get start and end city
				String startCity = (String) start.getSelectedItem();
				String endCity = (String) end.getSelectedItem();
				
				//only display path if going to a different city
				if(!startCity.equals(endCity)){
					d.findPath(startCity);
					d.setPath(endCity);
					ArrayList<Vertex> path = d.getPath();
					
					map.addPath(path);	//paint the component
					map.repaint();
					
					//display the distance
					String theDistance = String.valueOf(d.getDistance(endCity));
					distance.setText(theDistance.substring(0,6) + " Miles");
				}
				
				//If same city, show no distance needs to be traveled
				else{
					distance.setText("00.00 Miles");
					ArrayList<Vertex> path = null;
					map.addPath(path);
					map.repaint();
				}
			}
		}
	
	}
	
}
