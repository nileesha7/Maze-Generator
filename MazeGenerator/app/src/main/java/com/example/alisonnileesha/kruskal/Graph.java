package com.example.alisonnileesha.kruskal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Graph {
	int wallsDown;
	int graphSize;
	HashMap<Integer, Node> nodes;
	HashMap<Integer, ArrayList<Node>>sets;
	ArrayList<Edge> edgeList;

	HashMap<Integer, ArrayList<Integer>>adjacencyList;
	Random random=new Random();


	public Graph(int N){
		wallsDown=0;
		graphSize=N;
		nodes=new HashMap<>();
		sets=new HashMap<>();
		edgeList=new ArrayList<>();
		for(int i=0; i<N; i++){
			ArrayList<Node> newList=new ArrayList<>();
			Node newNode=new Node(i+1);
			newList.add(newNode);
			nodes.put(i+1, newNode);
			sets.put(i+1,newList);
			newNode.generateNeighbors(i+1, N);
		}
		generateGraph();
		generateAdjacencyList();
	}
	public HashMap<Integer, ArrayList<Integer>> getAdjacencyList(){
		return adjacencyList;
	}

	private void generateAdjacencyList() {
		adjacencyList=new HashMap<>();
		for(int i=0; i<graphSize; i++){
			ArrayList<Integer> newList=new ArrayList<>();
			adjacencyList.put(i+1, newList);
		}
		for(Edge e:edgeList){
			int ni=e.node1.id;
			int nj=e.node2.id;

			adjacencyList.get(ni).add(nj);
			adjacencyList.get(nj).add(ni);

		}
	}
	public ArrayList<Edge> getEdgeList(){
		return edgeList;
	}
	private void generateGraph(){
		while(wallsDown<graphSize-1){//traversal of all walls
			int current=random.nextInt(graphSize)+1; //get random node
			//System.out.println("Current: "+current);
			int neighbor=findRandomNeighbor(nodes.get(current));//retrieve neighbor of node
			//System.out.println("Neighbor: "+neighbor);

			if(neighbor!=0){
				if(!twoSetsAreEqual(current, neighbor)){
					mergeSets(current, neighbor);
					breakWalls(current, neighbor);

					wallsDown++;
				}
			}
		}

	}

	private void breakWalls(int current, int neighbor) {
		//add an edge
		Edge newEdge= new Edge(current, neighbor);
		edgeList.add(newEdge);
	}

	private void mergeSets(int current, int neighbor) {
		int highValue=Math.max(current, neighbor);
		int lowValue = Math.min(current, neighbor);
		int highKey=0;
		int lowKey=0;
		ArrayList<Node>highValueList = null, lowValueList=null;
		ArrayList<Node>mergedList=new ArrayList<>();

		for(Entry<Integer, ArrayList<Node>> entry : sets.entrySet()){
			if(entry.getValue().contains(nodes.get(highValue))){
				highValueList=entry.getValue();
				highKey=entry.getKey();
			}
			if(entry.getValue().contains(nodes.get(lowValue))){
				lowValueList=entry.getValue();
				lowKey=entry.getKey();
			}
		}
		mergedList.addAll(highValueList);
		mergedList.addAll(lowValueList);

		sets.put(lowKey, mergedList);
		sets.remove(highKey);
	}

	private boolean twoSetsAreEqual(int current, int neighbor) {
		//Iterate all the values in the hashmap with arraylists of length more than 1
		Node currNode=nodes.get(current);
		Node neighNode=nodes.get(neighbor);
		for(Entry<Integer, ArrayList<Node>> entry : sets.entrySet()){
			//if(!entry.getValue().isEmpty()){
			//System.out.println("current: "+entry.getValue().contains(currNode) +", neighbor: "+entry.getValue().contains(neighNode));
			//return (entry.getValue().contains(current) && entry.getValue().contains(neighbor));
			if(entry.getValue().contains(currNode) && entry.getValue().contains(neighNode)){
				return true;
			}
			//}

		}

		return false;
	}

	private int findRandomNeighbor(Node node) {
		//get the neighbor Array of node
		int [] neighbor = node.getNeighborArray();
		//get a  neighbor
		int i=0;
		while(i<15){ //Ask Nileesha why 15
			i=random.nextInt(4);
			if(neighbor[i]!=-1 ){
				return neighbor[i]; //returns neighbor
			}
			i++;
		}
		return 0;
	}



}