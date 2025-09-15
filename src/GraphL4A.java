
import java.util.Scanner;

public class GraphL4A {

	private int n;
	private int type; //0 if undirected, 1 if directed
	private int weighted; // 0 if unweighted, 1 otherwise
	private WeightedNode4A[] adjlistW; //one of adjlistW and adjlist is null, depending on the type of the graph
	private Node4A[] adjlist;

	public GraphL4A(Scanner sc){
	    String[] firstline = sc.nextLine().split(" ");
	    this.n = Integer.parseInt(firstline[0]);
	    System.out.println(this.n);
	    if (firstline[1].equals("undirected"))
	    	this.type = 0;
	    else
	    	this.type=1;
	    System.out.println("Type= "+this.type);
	    if (firstline[2].equals("unweighted"))
	    	this.weighted = 0;
	    else
	    	this.weighted=1;
	    System.out.println("Weighted= "+this.weighted);
	    if (this.weighted==0) {
	    	this.adjlist=new Node4A[this.n];
	    	for (int i=0;i<this.n;i++)
	    		adjlist[i]=null;
	        adjlistW=null;
	    }
	    else {
	    	this.adjlistW=new WeightedNode4A[this.n];
	    	for (int i=0;i<this.n;i++)
	    		adjlistW[i]=null;
	        adjlist=null;
	    }
	    	
	    
	  	for(int k=0;k<this.n;k++){
	  		String[] line = sc.nextLine().split(" : ");
	  		int i = Integer.parseInt(line[0]); //the vertex "source"
	  		if (weighted==0) {
	  			if ((line.length>1) && (line[1].charAt(0)!=' ')) {
	  				String[] successors= line[1].split(", "); 
	  				for (int h=0;h<successors.length;h++) {
	  					Node4A node=new Node4A(Integer.parseInt(successors[h])-1,null);
	  					node.setNext(adjlist[i-1]);
	  					adjlist[i-1]=node;
	  				}
	  			}
	  		}
	  		else {
	  			line = line[1].split(" // "); 
	  			if ((line.length==2)&&(line[1].charAt(0)!=' ')){// if there really are somme successors, then we must have something different from " " after "// "
	  				  	String[] successors= line[0].split(", ");
	  			  	    String[] theirweights= line[1].split(", ");
	  				  	for (int h=0;h<successors.length;h++) {
	  				  	  	WeightedNode4A nodeW = new WeightedNode4A(Integer.parseInt(successors[h])-1,null,Float.parseFloat(theirweights[h]));
	  				  	  	nodeW.setNext(adjlistW[i-1]);
	  				  	  	adjlistW[i-1]=nodeW;
	  				  	}
	  			
	  			}
	  		}
	  	}
	  	
	  }
	    
	   	  	
	
	//method to be applied only when type=0 and weighted=0
	public int[] degree(){
		int[] tmp=new int[this.n];
	    for(int i=0;i<this.n;i++) 
	    	tmp[i]=0;
	    	for(int i=0;i<this.n;i++) {
	    		Node4A p=adjlist[i];
	    		while (p!=null) {
	    			tmp[i]=tmp[i]+1;
	    			p=p.getNext();
	    		}
	    	}
	    	return(tmp);
	  }
	
	//method to be applied only when type=0 and weighted=1
		public int[] degreeW(){
			int[] tmp=new int[this.n];
		    for(int i=0;i<this.n;i++) 
		    	tmp[i]=0;
		    	for(int i=0;i<this.n;i++) {
		    		WeightedNode4A p=adjlistW[i];
		    		while (p!=null) {
		    			tmp[i]=tmp[i]+1;
		    			p=p.getNext();
		    		}
		    	}
		    	return(tmp);
		  }
	

	//method to be applied only when type=1 and weighted=0
	  public TwoArrays4A degrees(){
		  int[] tmp1=new int[this.n]; //indegrees
		  int[] tmp2=new int[this.n]; //outdegrees
		  for(int i=0;i<this.n;i++) { 
		    tmp1[i]=0;
		    tmp2[i]=0;
		  }
    	for(int i=0;i<this.n;i++) {
    		Node4A p=adjlist[i];
    		while (p!=null) {
    			tmp2[i]=tmp2[i]+1;
    			tmp1[p.getVal()]=tmp1[p.getVal()]+1;
    			p=p.getNext();
    		}
    	}	
	    return(new TwoArrays4A(tmp1,tmp2));        
	  }
		
	//method to be applied only when type=1 and weighted=1
	  public TwoArrays4A degreesW(){
		  int[] tmp1=new int[this.n]; //indegrees
		  int[] tmp2=new int[this.n]; //outdegrees
		  for(int i=0;i<this.n;i++) { 
		    tmp1[i]=0;
		    tmp2[i]=0;
		  }
    	for(int i=0;i<this.n;i++) {
    		WeightedNode4A p=adjlistW[i];
    		while (p!=null) {
    			tmp2[i]=tmp2[i]+1;
    			tmp1[p.getVal()]=tmp1[p.getVal()]+1;
    			p=p.getNext();
    		}
    	}	
	    return(new TwoArrays4A(tmp1,tmp2));        
	  }
		
	  
	  
	  public int getType() {
			return this.type;
		}
	  public int getWeighted() {
			return this.weighted;
		}
	

	  public Node4A[] getTranspose(){
		Node4A[] newtab = new Node4A[adjlist.length];
		for (int i=0;i<adjlist.length;i++){
			for (Node4A current = adjlist[i]; current != null; current = current.getNext()) {
				int v = current.getVal();
				Node4A newNode = new Node4A(i, newtab[v]);
				newtab[v] = newNode;
			}
		}
		return newtab;
	  }	
}
		
	

	 