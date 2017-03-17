
/**
 * Class that represents an edge of a Graph
 * @author rbk
 *
 */

public class Edge {
    Vertex from; // head vertex
    Vertex to; // tail vertex
    double w;// weight of edge
    Edge(Vertex u, Vertex v, double w) {
	from = u;
	to = v;
	this.w = w;
    }

    public Vertex otherEnd(Vertex u) {
	assert from == u || to == u;
	// if the vertex u is the head of the arc, then return the tail else return the head
	if (from == u) {
	    return to;
	} else {
	    return from;
	} 
    }

    public String toString() {
	return from + " " + to + " " + w;
    }
}
