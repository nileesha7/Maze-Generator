package com.example.alisonnileesha.kruskal;

import java.util.ArrayList;

public class Node {
	
	int id;
	ArrayList <Edge> connections;
	int [] neighbors;
	
	public Node(int nodeNum){
		id=nodeNum;
		connections=new ArrayList<Edge>();
		neighbors=new int[4];
	}

	public void generateNeighbors(int nodeNum, int totalNumOfNodes) {
		Double d=(Math.sqrt(totalNumOfNodes));
		int n=d.intValue();
		
		if(! (nodeNum==1 || nodeNum%n==1)){
			neighbors[0]=nodeNum-1; //left neighbor
		}else{
			neighbors[0]=-1;
		}
		if(! (nodeNum%n==0)){
			neighbors[1]=nodeNum+1;//right
		}else{
			neighbors[1]=-1;
		}
		
		if(! (nodeNum <=n)){
			neighbors[2]=nodeNum-n;//up
		}else{
			neighbors[2]=-1;
		}
		if(! (nodeNum > totalNumOfNodes-nodeNum)){
			neighbors[3]=nodeNum+n;//down
		}else{
			neighbors[3]=-1;
		}
		
		
	}
	
	public int[] getNeighborArray(){
		return neighbors;
	}

	
	
}
