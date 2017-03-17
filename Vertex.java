/**
 * Class to represent a vertex of a graph
 * @author rbk
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Vertex implements Iterable<Edge>, Comparable<Vertex> {
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    Vertex p; // parent vertex
    double net ;
    double error;
    double x;
    boolean input ;
    Vertex(int n) {
     net = 0;
     error = 0;
     x = 1;
	 name = n;
	 seen = false;
	 adj = new ArrayList<Edge>();
	 revAdj = new ArrayList<Edge>();   /* only for directed graphs */
	 input = false ;
    }
    public Iterator<Edge> iterator() { return adj.iterator(); }
    public String toString() {
	return Integer.toString(name);
    }
	@Override
	public int compareTo(Vertex arg0) {
		return 1;
	}
	
}
