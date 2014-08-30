import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

//import BFS.Node;


public class DFS {
	// parse an integer from string:
	private static int nextInt(int[] p, String s){  
		while(p[0]<s.length() && s.charAt(p[0]) ==' ') p[0]++;
		int d=0;
		while(p[0]<s.length() && s.charAt(p[0]) !=' ') {
			d = d*10 + s.charAt(p[0]) - 48;
			p[0]++;
		}
		return d;
	}
	
	private static class Node{
		int v; // vertex no.
		int status; // 0: unvisited; 1: has been visited
		Node parent;
		ArrayList<Node> nb; //neighbors
		Node(int vertex) {
			v = vertex;
			status = 0;
			parent = null;
			nb = new ArrayList<Node>();
		}			
	}
		
	static ArrayList<Node> vertices = new ArrayList<Node>();
	
	private static void createAdjacencyList(String s) {
		int[] p1= new int[1];
		p1[0]=1;  
		Node node = vertices.get(nextInt(p1, s)); 
		while(p1[0]<s.length()){
			node.nb.add(vertices.get(nextInt(p1, s))); 
		}
	}
	
	private static void dfs(Node node){				
		if(node.status ==0 ){
			System.out.print(" "+node.v);
			node.status = 1; // visited
		}
		
		for(Node child: node.nb){
			if(child.status == 1) continue;
			child.parent = node;
			dfs(child);
		}
		if(node.parent==null) return;
		else dfs(node.parent);
	}
	
	private static void prepareData(int n){				 
		//System.out.print("\nn="+n);		
		// generate all vertices labeled from 1 to n:
		for(int i=0; i<=n; i++){
			Node node = new Node(i);
			vertices.add(node);
		}
		String line;
		
		line= " 1 8 2 6";     createAdjacencyList(line); 
		line= " 2 8 6 1 7";   createAdjacencyList(line); 
		line= " 3 8 4 6 5";   createAdjacencyList(line); 
		line= " 4 9 8 3 5";	  createAdjacencyList(line); 
		line= " 5 3 4 6 7";	  createAdjacencyList(line); 
		line= " 6 1 7 3 5 2"; createAdjacencyList(line); 
		line= " 7 2 6 5";     createAdjacencyList(line); 
		line= " 8 9 4 1 2 3"; createAdjacencyList(line);   
		line= " 9 8 4";  	  createAdjacencyList(line);
		/*
		line= "1 2 6 8";     createAdjacencyList(line); 
		line= "2 8 6 1 7 9";   createAdjacencyList(line); 
		line= "3 8 4 6 5";   createAdjacencyList(line); 
		line= " 4 3 5";	  createAdjacencyList(line); 
		line= " 5 9 8 3 4 6 7";	  createAdjacencyList(line); 
		line= " 6 1 7 3 5 2"; createAdjacencyList(line); 
		line= " 7 2 6 5";     createAdjacencyList(line); 
		line= " 8 5 1 2 3"; createAdjacencyList(line);   
		line= " 9 2 5";  	  createAdjacencyList(line);*/	
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		/*
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int[] p1 = new int[1];
		int n = nextInt(p1, br.readLine());
		System.out.print("\nn="+n);
		
		// generate all vertices labeled from 1 to n:
		for(int i=0; i<=n; i++){
			Node node = new Node(i);
			vertices.add(node);
		}
		
		// generate the adjacency list:
		String line;
		int i;
		for(i=0; i<n; i++){
			line= br.readLine(); 
			createAdjacencyList(line); 
		}
		*/
		
		int n=9;
		prepareData(n);
		Random rnd = new Random();				
		dfs(vertices.get(rnd.nextInt(n)+1));
	}
}
/*
 9
 1 8 2 6
 2 8 6 1 7
 3 8 4 6 5
 4 9 8 3 5
 5 3 4 6 7
 6 1 7 3 5 2
 7 2 6 5
 8 9 4 1 2 3
 9 8 4
 
 9
 1 2 6 8
 2 8 6 1 7 9 
 3 8 4 6 5
 4 3 5
 5 9 8 3 4 6 7
 6 1 7 3 5 2
 7 2 6 5
 8 5 1 2 3
 9 2 5
 
 */
