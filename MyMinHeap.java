/*
This is the updated minheap for Dijkstras/Kruskal's algorithm
*/


import java.util.HashMap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//          or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
* Implements a binary heap.
* Note that all "matching" is based on the compareTo method.
* @author Mark Allen Weiss
*/
public class MyMinHeap<AnyType extends Comparable<? super AnyType>>
{
/**
* Construct the binary heap.
*/
public MyMinHeap( )
{
   this( DEFAULT_CAPACITY );
}

/**
* Construct the binary heap.
* @param capacity the capacity of the binary heap.
*/
@SuppressWarnings("unchecked")
public MyMinHeap( int capacity )
{
   //add hash map to the minheap
   //it will find objects in the minheap array for decreaseKey
   map = new HashMap<AnyType,Integer>();
   currentSize = 0;
   array = (AnyType[]) new Comparable[ capacity + 1 ];
}

/**
* Construct the binary heap given an array of items.
*/
@SuppressWarnings("unchecked")
public MyMinHeap( AnyType [ ] items )
{
	   map = new HashMap<AnyType,Integer>();
       currentSize = items.length;
       array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];

       int i = 1;
       for( AnyType item : items ){
       	   map.put(item,i);
           array[ i++ ] = item;			//update hashMap
        }
       buildHeap( );
}

/**
* Insert into the priority queue, maintaining heap order.
* Duplicates are allowed.
* @param x the item to insert.
*/
public void insert( AnyType x)
{
	 
   if( currentSize == array.length - 1 )
       enlargeArray( array.length * 2 + 1 );

       // Percolate up
   int hole = ++currentSize;
   
   percolateUp(x, hole);
}

//create percolateUp method, updates hashMap and heap
private void percolateUp(AnyType x, int hole){
	 for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 ){
 			map.remove(array[hole/2]);
     	    map.put(array[hole/2], hole);
  			array[ hole ] = array[ hole / 2 ];
   }
   array[ hole ] = x;
   map.remove(array[hole]);
   map.put(array[hole], hole);
}

//method that updates the minheap after a value is changed
public void decreaseKey(AnyType x, AnyType y){
	  int place = map.get(x);		//get location of value
	  map.remove(x);				//remove old value
	  array[place] = y;				//update value
	  map.put(y,place);				//add new value to map
	  percolateUp(y, place);		//adjust its placement
	  
}

@SuppressWarnings("unchecked")
private void enlargeArray( int newSize )
{
       AnyType [] old = array;
       array = (AnyType []) new Comparable[ newSize ];
       for( int i = 0; i < old.length; i++ )
           array[ i ] = old[ i ];        
}

/**
* Find the smallest item in the priority queue.
* @return the smallest item, or throw an UnderflowException if empty.
*/
public AnyType findMin( )
{
   if( isEmpty( ) )
       throw new UnderflowException( );
   return array[ 1 ];
}

/**
* Remove the smallest item from the priority queue.
* @return the smallest item, or throw an UnderflowException if empty.
*/
public AnyType deleteMin( )
{
   if( isEmpty( ) )
       throw new UnderflowException( );
   
   AnyType minItem = findMin( );
   int current = currentSize;
   array[ 1 ] = array[ currentSize-- ];
   
   
   map.remove(minItem);					//update hashMap
   map.remove(array[current]);
   map.put(array[current], 1);
   
   percolateDown( 1 );

   return minItem;
}

/**
* Establish heap order property from an arbitrary
* arrangement of items. Runs in linear time.
*/
private void buildHeap( )
{
   for( int i = currentSize / 2; i > 0; i-- )
       percolateDown( i );
}

/**
* Test if the priority queue is logically empty.
* @return true if empty, false otherwise.
*/
public boolean isEmpty( )
{
   return currentSize == 0;
}

/**
* Make the priority queue logically empty.
*/
public void makeEmpty( )
{
   currentSize = 0;
}

public void printTree(){
	for(int i=1;i<=map.size();i++){
		System.out.println(i + " " + map.get(array[i]));
		System.out.println(array[i]);
	}
}

private HashMap<AnyType,Integer> map;
private static final int DEFAULT_CAPACITY = 10;
private int currentSize;      // Number of elements in heap
private AnyType [ ] array; // The heap array

/**
* Internal method to percolate down in the heap.
* @param hole the index at which the percolate begins.
*/

//updates hashMap as well
private void percolateDown( int hole )
{
   int child;
   AnyType tmp = array[ hole ];

   for( ; hole * 2 <= currentSize; hole = child )
   {
       child = hole * 2;
       if( child != currentSize &&
               array[ child + 1 ].compareTo( array[ child ] ) < 0 )
           child++;
       if( array[ child ].compareTo( tmp ) < 0 ){
      	 map.remove(array[child]);
      	 map.put(array[child], hole);
           array[ hole ] = array[ child ];
       }
       else
           break;
   }
   array[ hole ] = tmp;
   map.remove(array[hole]);
   map.put(array[hole], hole);
}
}
