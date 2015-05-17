/*
This is the component class for dislpaying Dijkstra's algorithm
by Weston Jackson
wjj2106
*/

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;

public class mapComponent extends JPanel{
	private File theFile;							//coordinates
	private Scanner in;
	private int width;								//dimensions
	private int height;
	private HashMap<String, Vertex> cityList;		//cities
	private ArrayList<Vertex> theList;				//Vertices
	private ArrayList<Vertex> path;					//optimal path
	
	
	public mapComponent(File f, Dijkstra d, int w, int h) throws FileNotFoundException{
	
		theFile=f;
		in = new Scanner(theFile);
		width=w;
		height=h;
		cityList = d.getMap();
		theList = d.getList();
		read();
	}
	
	//read in the coordinates
	public void read(){
		while(in.hasNext()){
			String city = in.next();
			Vertex v;
			
			//only add cities that are in citypairs.txt
			if(cityList.containsKey(city)){
				v = cityList.get(city);
				int x = in.nextInt();
				int y = in.nextInt();
				//must account for frame dimensions
				v.setX(50 +  x/4);
				v.setY(-50 + height - y /4);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		//display the cities at correct coordinates
		for(int i=0;i<theList.size();i++){
			Vertex v = theList.get(i);
			g2.drawString(v.getName(),v.getX()-15,v.getY()-5);
			g2.drawOval(v.getX(),v.getY(),4,4);
			ArrayList<Vertex> adjList = v.getList();
			
			//draw paths
			for(int j=0; j<adjList.size();j++){
				Vertex w = adjList.get(j);
				g2.drawLine(v.getX(),v.getY(),w.getX(),w.getY());
			}
		}
		
		//display the optimal path if necessary
		if(path != null){
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(3));
			for(int i = 0; i<path.size()-1;i++){
				Vertex v = path.get(i);
				Vertex w = path.get(i+1);
				g2.drawLine(v.getX(),v.getY(),w.getX(),w.getY());
			}
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.black);
		}
	}
	
	//get optimal path
	public void addPath(ArrayList<Vertex> p){
		path = p;
	}

}
