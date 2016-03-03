package com.example.alisonnileesha.kruskal;

public class Edge {
	
	Node node1;
	Node node2;
	
	public Edge(int id1, int id2){
		node1=new Node(id1);
		node2 = new Node(id2);
	}
}
