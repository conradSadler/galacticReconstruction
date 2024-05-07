/*
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class contains all the data members to create a vertex/colony object
 */

import java.util.Arrays;

public class Vertex_CS 
{
	private int[] wealth;  //stores the vertex/colony's wealth
	private EdgeList_CS edges;  //stores the edges that connect to it, both active and un-active
	private int id;  //stores the identifier

	public Vertex_CS() 
	{
		this.wealth = new int[1];
		this.id = 0;
		this.edges = new EdgeList_CS();
	}
	public Vertex_CS(int id,int wealth) 
	{
		this.wealth = new int[1];
		this.wealth[0] = wealth;
		this.id = id;
		this.edges = new EdgeList_CS();
	}
	public int[] getWealth() 
	{
		return wealth;
	}
	public void setWealth(int[] wealth) 
	{
		this.wealth = wealth;
	}
	public void setWealth(int wealth) 
	{
		this.wealth[0] = wealth;
	}
	public EdgeList_CS getEdges() 
	{
		return edges;
	}
	public void setEdges(EdgeList_CS edges) 
	{
		this.edges = edges;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String toString() 
	{
		return "Vertex_CS [wealth=" + Arrays.toString(wealth) + ", edges=" + edges + ", id=" + id + "]";
	}
}
