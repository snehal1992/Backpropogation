import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Graph {
	List<Vertex> v; // vertices of graph
    int size; // number of verices in the graph
    boolean directed;  // true if graph is directed, false otherwise
    Graph(int size) {
	this.size = size;
	this.v = new ArrayList<>(size + 1);
	this.v.add(0, null);  // Vertex at index 0 is not used
	this.directed = true;  // default is undirected graph
	// create an array of Vertex objects
	for (int i = 1; i <= size; i++)
	    this.v.add(i, new Vertex(i));
    }
    Vertex getVertex(int n) {
	return this.v.get(n);
    }
    void addEdge(Vertex from, Vertex to, double weight) {
	Edge e = new Edge(from, to, weight);
	if(this.directed) {
	    from.adj.add(e);
            to.revAdj.add(e);
	} else {
	    from.adj.add(e);
	    to.adj.add(e);
	}
    }
    public Iterator<Vertex> iterator() {
	Iterator<Vertex> it = this.v.iterator();
	it.next();  // Index 0 is not used.  Skip it.
	return it;
    }
    public void computeOutput(List<Vertex> top , int inputNodes , int outputNodes , int hiddenNodes ) {
	  int total = inputNodes + outputNodes + hiddenNodes;
	  double net = 0.0;
	  for(int i = 0; i < top.size(); i++) {
        if( i >= total ) {
        	Vertex v = this.v.get(top.get(i).name);
        	for(Edge e: v.revAdj) {
        		net += e.otherEnd(v).x * e.w;
        	}
            net = 1.0 /(1.0 + Math.exp(-net));
            v.x = net;
            v.net = net;
        } 
	  } 
    }
    public Graph backwardPass(List<Vertex> revTop, int inputNodes , int outputNodes , int hiddenNodes , double learningRate , List<Double> t) {
      Iterator<Vertex> it = revTop.iterator();
      for(int i = 0; i < outputNodes; i++) {
        Vertex v = this.v.get(it.next().name);
        v.error = v.x * (1 - v.x) * (t.get(i) - v.x);
        //System.out.println(v.name+" "+v.error);
        for(Edge e: v.revAdj) {
          e.w = e.w + learningRate * v.error * e.otherEnd(v).x ;
          
        }
      }
      double er = 0.0;
      for(int i = 0; i < hiddenNodes; i++) {
      	Vertex v = it.next();
      	for(Edge e1: v.adj) {
          er += e1.w * e1.otherEnd(v).error;
      	}
      	v.error = v.x * (1 - v.x) * er;
        for(Edge e: v.revAdj) {
          e.w = e.w + learningRate * v.error * e.otherEnd(v).x ;
        }
      } 
      return this ;
    }
    public  Graph createGraph( int inputNodes , int hiddenNodes , int outputNodes) {
    	
      for (int i = 1; i <= inputNodes; i++) {
    	    this.getVertex(i).input=true;
	    for(int j = inputNodes+1; j <= hiddenNodes+inputNodes; j++) {
		  this.addEdge(this.getVertex(i), this.getVertex(j), 0.05);
        }
      }
      // add bias for hidden nodes 
      int tempInput = inputNodes+1;
      for(int i = hiddenNodes+inputNodes+1; i <= 2*hiddenNodes+inputNodes; i++) {
    	    this.addEdge(this.getVertex(i) ,this.getVertex(tempInput),0.05);
    	    tempInput++;
      }
      
      for(int i = inputNodes+1; i<= hiddenNodes+inputNodes; i++) {
	    for(int j = inputNodes + 2*hiddenNodes + 1; j <= inputNodes + 2*hiddenNodes + outputNodes; j++) {
		  this.addEdge(this.getVertex(i), this.getVertex(j), 0.05);
	    }
      }
      
      // add bias for target
      int tempOutput = inputNodes+ 2*hiddenNodes + 1;
      for(int i = 2*hiddenNodes+inputNodes+outputNodes+1; i <= 2*hiddenNodes+inputNodes+2*outputNodes; i++) {
    	    this.addEdge(this.getVertex(i) ,this.getVertex(tempOutput),0.05);
    	    //System.out.println(i + " " +tempOutput);
    	    tempOutput++;
      }
	return this;
    }
    
}
