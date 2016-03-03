package com.example.alisonnileesha.kruskal;
import java.util.ArrayList;
import java.util.HashMap;

public class TesterMain {
	private static boolean [][] verticalLines;
	private static boolean [][] horizontalLines;
	public static void start() {
		int numberOfNodes=64;

		Graph g= new Graph(numberOfNodes);
		ArrayList<Edge>edgeList=g.getEdgeList();
		for(Edge e:edgeList){
			System.out.println("<"+e.node1.id+", "+e.node2.id+">");
		}
		generateLineArrays(numberOfNodes, g);
	}

	private static void generateLineArrays(int numberOfNodes, Graph g) {

		Double d=Math.sqrt(numberOfNodes);
		int n=d.intValue();

		verticalLines=new boolean[n][n-1];
		horizontalLines= new boolean[n-1][n];

		HashMap<Integer, ArrayList<Integer>> adjacencyList=g.getAdjacencyList();

		for(int i=1; i<=numberOfNodes; i++){
			boolean rightNeighbor=adjacencyList.get(i).contains(i+1);
			boolean bottomNeighbor=adjacencyList.get(i).contains(i+n);

			if(!rightNeighbor &&i%n!=0){
				//if(i%n!=0){
				int row=i/n;
				int col=(i%n)-1;

				verticalLines[row][col]=true;
				//}

			}
			if(!bottomNeighbor){
				if(i+n<=numberOfNodes){
					int row=i/n;
					int col=(i%n)-1;

					if(i%n==0){
						row--;
						col=n-1;
					}

					horizontalLines[row][col] = true;
				}

			}
		}
		printLineArrays(verticalLines, horizontalLines);


	}

	private static void printLineArrays(boolean verticalLines[][], boolean horizontalLines[][]) {
		System.out.println("\n"+"Vertical Line Array:");
		for(int i=0; i<verticalLines.length;i++){
			for(int j=0; j<verticalLines[i].length;j++){
				System.out.print(verticalLines[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("\n"+"Horizontal Line Array:");
		for(int i=0; i<horizontalLines.length;i++){
			for(int j=0; j<horizontalLines[i].length;j++){
				System.out.print(horizontalLines[i][j]+" ");
			}
			System.out.println();
		}
	}

	public boolean[][] getVerticalLines(){
		return verticalLines;
	}

	public boolean[][] getHorizontalLines(){
		return horizontalLines;
	}

}