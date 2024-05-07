/*
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class contains all the information needed to make a warp gate to connect two colonies(a edge to connect two vertices)
 */
public class Edge_CS 
{
	private Vertex_CS vertexFrom;  //stores the starting vertex
	private Vertex_CS vertexTo;  //stores the destination vertex
	private int weight;  //stores the cost to build the gate/edge
	private int id;  //stores the edge's identifier
	
	public Edge_CS() 
	{
		this.vertexFrom = null;
		this.vertexTo = null;
		this.weight = 0;
		this.id =-1;
	}
	public Edge_CS(Vertex_CS vertexFrom,Vertex_CS vertexTo,int weight,int id) 
	{
		this.vertexFrom = vertexFrom;
		this.vertexTo = vertexTo;
		this.weight = weight;
		this.id = id;
	}
	public Vertex_CS getVertexFrom() 
	{
		return vertexFrom;
	}
	public void setVertexFrom(Vertex_CS vertexFrom) 
	{
		this.vertexFrom = vertexFrom;
	}
	public Vertex_CS getVertexTo() 
	{
		return vertexTo;
	}
	public void setVertexTo(Vertex_CS vertexTo) 
	{
		this.vertexTo = vertexTo;
	}
	public int getWeight() 
	{
		return weight;
	}
	public void setWeight(int weight) 
	{
		this.weight = weight;
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
		return "Edge_CS [vertexFrom=" + vertexFrom + ", vertexTo=" + vertexTo + ", weight=" + weight + ", id=" + id
				+ "]";
	}
}
