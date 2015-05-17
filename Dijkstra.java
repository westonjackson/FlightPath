/*
This is the class that implements Dijkstra's algorithm
by Weston Jackson
wjj2106
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dijkstra {
	private ArrayList<Vertex> theList;			//The vertices
	private ArrayList<Vertex> path;				//The optimal path
	private File infile;
	private Scanner in;
	private HashMap<String, Vertex> cityList;	//vertex, city-name map
	
	public Dijkstra (File f) throws FileNotFoundException{
		infile = f;
		in = new Scanner(infile);
		cityList = new HashMap<String, Vertex>();
		theList = new ArrayList<Vertex>();
		path = new ArrayList<Vertex>();
		makeGraph();
	}
	
	//Makes an array of city name strings (for the JComboBox)
	public String[] getCities(){
		String[] cityNames = new String[cityList.size()];
		Set<String> c = cityList.keySet();
		Iterator<String> cIterator = c.iterator();
		for(int i=0;i<c.size();i++){
			cityNames[i]=cIterator.next();
		}
		return cityNames;
	}
	
	//returns # of cities
	public int size(){
		return cityList.size();
	}
	
	//returns hashMap of city names and vertices
	public HashMap<String, Vertex> getMap(){
		return cityList;
	}
	
	//returns the optimal path
	public ArrayList<Vertex> getList(){
		return theList;
	}
	
	//creates the graph using the file information
	public void makeGraph(){
		while(in.hasNextLine()){
			String start="";		//import first city
			if(in.hasNext())
				start = in.next();
			else
				break;
				
			String end="";			//import second city
			if(in.hasNext())
				end = in.next();
			else
				break;
				
			double distance=0.0;		//import distance
			if(in.hasNextDouble())
				distance = in.nextDouble();
			else
				break;
			
			//see if start city is already in hashmap
			if(cityList.containsKey(start)){
				Vertex v1 = cityList.get(start);
				
				//if end city is not in map, add it
				if(!cityList.containsKey(end)){ 
					Vertex x = new Vertex(end);
					cityList.put(end, x);
				}
				Vertex v2 = cityList.get(end);
				
				//add a path to BOTH adjacency lists
				v1.addList(v2,distance);
				v2.addList(v1,distance);
			}
			
			//if start city is not in hashmap, add it
			else if(!cityList.containsKey(start)){
				Vertex v1 = new Vertex(start);
				
				//if end city is not in map, add it
				if(!cityList.containsKey(end)){
					Vertex x = new Vertex(end);
					cityList.put(end, x);
				}
				Vertex v2 = cityList.get(end);
				
				//add a path to BOTH adjacency lists
				v1.addList(v2,distance);
				v2.addList(v1,distance);
				
				//add start city to hashmap
				cityList.put(start, v1);
			}	
		}
		
		Collection<Vertex> vertices = cityList.values();
		Iterator<Vertex> it = vertices.iterator();
		
		for(int i = 0; i<vertices.size();i++){
			//create the list of vertices from the hashmap
			theList.add(it.next());
		}
		
	}
	
	//dijkstras algorithm
	public void findPath(String city){
		MyMinHeap<Vertex> heap = new MyMinHeap<Vertex>();
		//reset vertices
		for(int i = 0; i<theList.size();i++){
			Vertex v = theList.get(i);
			v.setKnown(false);
			v.setDist(Double.POSITIVE_INFINITY);
			v.setPath(null);
			heap.insert(v);
		}
		
		//decrease key of starting vertex
		Vertex start = cityList.get(city);
		Vertex x = start;
		start.setDist(0);
		heap.decreaseKey(x, start);
		
		
		for(;;){
			Vertex v = heap.deleteMin();				//get closest vertex
			v.setKnown(true);
			for(int i=0; i<v.getList().size();i++){
				Vertex w = v.getList().get(i);			//get adjacent vertices
				if(!w.getKnown()){
					double cost = v.getDistances().get(i);
					if(cost + v.getDist() < w.getDist()){		//compare costs
						Vertex old = w;
						w.setDist(v.getDist() + cost);
						w.setPath(v);
						heap.decreaseKey(old, w);
					}
					
					//new vertex is found
					else if(w.getDist()==Double.POSITIVE_INFINITY){
						Vertex old = w;
						w.setDist(cost+v.getDist());
						w.setPath(v);
						heap.decreaseKey(old, w);
					}
				} 
			}
			
			if(heap.isEmpty())
				break;
		}
	}
	
	//Use recursion to get shortest path to city
	public void setPath(String city){
		path.clear();
		Vertex v = cityList.get(city);
		setPath(v);
	}
	
	//find path shortest recursively
	private void setPath(Vertex v){
		if(v.getPath() != null){
			setPath(v.getPath());
			path.add(v);
		}
		else
			path.add(v);
	}
	
	//returns the path distance 
	public double getDistance(String city){
		return getDistance(cityList.get(city));
	}
	
	public double getDistance(Vertex v){
		return v.getDist();
	}
	
	//returns the path as an arrayList of vertices 
	public ArrayList<Vertex> getPath(){
		return path;
	}
}
