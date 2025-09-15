import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main4A {

  public static void main(String args[]){

    try{
      File file= new File(args[0]);
      
    //If we choose the representation by adjacency matrix
      Scanner sc = new Scanner(file);      
      GraphM4A graphM = new GraphM4A(sc); 
      if (graphM.getType()==0) { //undirected
    	  int[] degree = graphM.degree();
      System.out.println("(Matrix) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");  
      Tools4A.printArray(degree);
      }
      else { //directed
    	  TwoArrays4A pair=graphM.degrees();
    	  int[] indegree =pair.in(); //the result of graphM.degrees() is a pair of arrays, indegree and outdegree
          int[] outdegree =pair.out();
          System.out.println("(Matrix)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(indegree);
          System.out.println("(Matrix)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(outdegree);
          
    	  }	  
      
   // If we choose the representation by adjacency lists
      sc = new Scanner(file);
      GraphL4A graphL = new GraphL4A(sc); 
      if (graphL.getType()==0&&graphL.getWeighted()==0) { //undirected and unweighted
    	  int[] degree = graphL.degree();
    	  System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");  
          Tools4A.printArray(degree);
      }
      if (graphL.getType()==0&&graphL.getWeighted()==1) { //undirected and weighted
    	  int[] degree = graphL.degreeW();
    	  System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");  
          Tools4A.printArray(degree);
      }
      if (graphL.getType()==1&&graphL.getWeighted()==0){ //directed and unweighted
    	  TwoArrays4A pair=graphL.degrees();
    	  int[] indegree = pair.in(); 
          int[] outdegree = pair.out();
          System.out.println("(List) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(indegree);
          System.out.println("(List) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(outdegree);
    	  }	  
      if (graphL.getType()==1&&graphL.getWeighted()==1){ //directed and unweighted
    	  TwoArrays4A pair=graphL.degreesW();
    	  int[] indegree = pair.in(); 
          int[] outdegree = pair.out();
          System.out.println("(List)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(indegree);
          System.out.println("(List)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(outdegree);
    	  }	  
      sc.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
