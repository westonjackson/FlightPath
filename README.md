# FlightPath
Program to find optimal flight path between cities

Run DijkstraTester.java, it requires two command line arguments. First, citypairs.dat and then cityxy.txt. These are the files I used to test my program.

DijkstraTester.java - This is the tester class. It creates the GUI which contains the mapComponent as well as all the buttons. It uses JComboBox's to take in the start and end cities for Dijkstra's algorithm. This class also contains the inner DijkstraListener class which begins the shortest path algorithm. This inner class uses an if/else statement to determine if the algorithm even needs to be calculated (if the start city = the end city, this is unnecessary). Then, if needed, the shortest path between the cities is found using  the Dijkstra class.

Dijkstra.java - This is the class that implements dijkstra's algorithm. It reads in a file of cities and distances in creating the inner graph. One of the most important fields for this class is the "cityList" hashMap. This field allows a city's name, in the form of a string, to then map to a vertex in the inner graph. The other important fields are "thePath" which represents the vertices and "path" which represents the optimal path.

Vertex.java - The vertex class contains fields for both Kruskal's and Dijkstra's algorithm. Because this class could be used for both programs, making it a separate class worked better than using it as an inner class. Finally, the class contains accessor and mutator methods that will allow the vertex fields to be updated and accessed (adjusted for the frame dimensions). The class also has the paint component method which paints the vertices as well as the shortest path (if the path is present). The makeGraph method constructs the inner graph and creates the vertices. The findPath method implements dijkstras algorithm using the updated minheap. The setPath method finds the distance to a destination city.

mapComponent.java - This is the class that paints the graph as well as the shortest path on the GUI. It reads the cityxy.txt file and initially graphs the coordinates. The paintComponent method paints the vertices on the main GUI, and then if a shortest path has been specified, it will paint the shortest path. The most important fields in this class are "cityList" which allows the class to convert city names into vertices, "theList" which is the list of vertices, and "path" which is the optimal path.

MyMinHeap.java - This is the edited minheap. The biggest change to the minheap was that I added in a hashMap. The hashMap maps items to their locations in the minheap array. This allows the locations of items to be accessed quickly, so that a decreaseKey operation can be done in log(n) time. Most the changes I made included updating the hash map in all of the methods, as well as adding in the DecreaseKey method, which accesses an object's location from the hashMap, updates it, and then adds it back into the tree. 
  
UnderflowException.java - This class throws an exception in the minheap.
