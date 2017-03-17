import java.util.Collections;
import java.util.LinkedList;
import java.util.*;

public class Backpropogation {
  public void backpropogation(ArrayList<ArrayList<Double>> training , double learningRate , int inputNodes , 
    int outputNodes , int hiddenNodes) {
    int totalNodes = inputNodes + 2*outputNodes + 2*hiddenNodes;
    int rowLength  = training.size();
    List<Vertex> top ;
    List<Double > classLabel ;
    double t;
	Graph g = new Graph(totalNodes);
    g = g.createGraph(inputNodes, hiddenNodes, outputNodes);
    top = DFS(g);
    //for every training example or number of iterations 
    int eighty = (int) (8/10.0* rowLength);
    for(int j = 0; j < eighty ; j ++){
      for(int i = 1; i <= inputNodes; i++) {
      	g.getVertex(i).x = training.get(j).get(i-1);
      }
      t = training.get(j).get(inputNodes);
      //System.out.println(t);
      classLabel = binary(t,outputNodes);
      Collections.reverse(classLabel);
      //System.out.println(classLabel);
      //System.out.println(top);
      g.computeOutput(top , inputNodes , outputNodes , hiddenNodes) ;
      Collections.reverse(top);
      g = g.backwardPass(top , inputNodes , outputNodes , hiddenNodes , learningRate, classLabel);
      Collections.reverse(top);
      //System.out.println("change"+g.v.get(10).net+" "+g.v.get(22).net +" "+ g.v.get(21).net + " " +g.v.get(20).net);
    }
    System.out.println("Hidden Layer Weights");
    for(int i = 1; i <= inputNodes; i++) {
    	  System.out.print("Neuron "+i+" ");
    	  for(Edge e : g.v.get(i).adj) {
    		  System.out.print(e.w+",");
    	  }
    	  System.out.println();
    }
    System.out.println("Output  Layer Weights");
    for(int i = inputNodes+1; i <= inputNodes+hiddenNodes; i++) {
    	  System.out.print("Neuron "+(i-inputNodes)+" ");
  	  for(Edge e : g.v.get(i).adj) {
		  System.out.print(e.w+",");
	  }
	  System.out.println();
    }
    System.out.println("Predicted Class");
    int prediction =0;
    int c =0 ;
    int tot =0;
    for(int j = eighty; j  < rowLength; j++){
        for(int i = 1; i <= inputNodes; i++) {
        	g.getVertex(i).x = training.get(j).get(i-1);
        }
        g.computeOutput(top, inputNodes, outputNodes, hiddenNodes);
        for(int k=0 ; k< outputNodes; k++){
        if(g.v.get(top.get(top.size()-1-k).name).net < 0.5){
        	  prediction += Math.pow(2, outputNodes-k-1) *0;
        } else {
        	prediction += Math.pow(2, outputNodes-k-1) *1;
        	//System.out.println(k);
        }
        }
        System.out.println(prediction);
        if(prediction == training.get(j).get(inputNodes)){
        	  c++;
        	  tot++;
        } else {
        	  tot++;
        }
        prediction = 0;
    }
    System.out.println("accuracy="+(double)c/tot*100);
  }  
  List<Vertex> DFS(Graph g) {
  	List<Vertex>  list = new LinkedList<>();
  	for (Vertex u : g.v){
  		if(u!=null){
         u.seen=false;
         u.p=null;
  		}
  	}
  	for(Vertex u :g.v){
  		if(u!=null) {
  		if(!u.seen){
  			DFSVisit(u,list);
  		}
  	  }	
  	}
    return list ;
  }
   void DFSVisit(Vertex u , List<Vertex> list) {
  	u.seen=true;
  	for(Edge e : u.adj){
  		Vertex v=e.otherEnd(u);
  		if(!v.seen){
  			v.p=u;
  			DFSVisit(v,list); 
  		}
  	}
  	list.add(0, u);
  }
  List<Double> binary(double value , int outputNodes) {
	  List<Double> f = new ArrayList<Double>();
		  int z = (int) value ;
		  int m = Integer.parseInt(Integer.toBinaryString(z));
		  while (m >= 0) {
		      f.add((double)(m % 10));
		      m = m / 10;
		      if(m==0){ break;}
		  }
		  if(f.size()<outputNodes){
			  int k = outputNodes - f.size();
			  for(int i = 0 ; i < k; i++)
			  f.add(0.0);
		  }
		  return f;
	  }
}
