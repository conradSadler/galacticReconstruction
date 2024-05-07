/*
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class is used to store important information used to calculate the shortest path from a starting vertex with Dijkstra's algorithm.
 */
public class CostOptimization_CS 
{
	private int cost;  //stores the summed cost to get the "xyz" vertex from the starting vertex
	private Vertex_CS vertex;  //stores "xyz" vertex's identifier
	private int previousVertexId;  //stores the vertex visited before "xyz" vertex
	private int gateNodeNumber;  //stores the nearest gate identifier that is used to complete the shortest path
	
	public CostOptimization_CS() 
	{
		this.cost = Integer.MAX_VALUE;
		this.vertex = null;
		this.previousVertexId = -1;
		this.gateNodeNumber = -1;
	}
	public CostOptimization_CS(int cost) 
	{
		this.cost = cost;
		this.vertex = null;
		this.previousVertexId = -1;
		this.gateNodeNumber = -1;
	}
	public CostOptimization_CS(Vertex_CS vertex) 
	{
		this.cost = Integer.MAX_VALUE;
		this.vertex = vertex;
		this.previousVertexId = -1;
		this.gateNodeNumber = -1;
	}
	public CostOptimization_CS(Vertex_CS vertex,int cost) 
	{
		this.cost = cost;
		this.vertex = vertex;
		this.previousVertexId = -1;
		this.gateNodeNumber = -1;
	}
	public CostOptimization_CS(Vertex_CS vertex,int cost,int previousVertexId,int gateNodeNumber) 
	{
		this.cost = cost;
		this.vertex = vertex;
		this.previousVertexId = previousVertexId;
		this.gateNodeNumber = gateNodeNumber;
	}
	public int getCost() 
	{
		return cost;
	}

	public void setCost(int cost) 
	{
		this.cost = cost;
	}

	public Vertex_CS getVertexId() 
	{
		return vertex;
	}

	public void setVertexId(Vertex_CS vertexId) 
	{
		this.vertex = vertexId;
	}

	public int getPreviousVertexId() 
	{
		return previousVertexId;
	}

	public void setPreviousVertexId(int previousVertexId) 
	{
		this.previousVertexId = previousVertexId;
	}
	public int getGateNodeNumber() 
	{
		return gateNodeNumber;
	}

	public void setGateNodeNumber(int gateNodeNumber) 
	{
		this.gateNodeNumber = gateNodeNumber;
	}
	public Vertex_CS getVertex() 
	{
		return vertex;
	}
	public void setVertex(Vertex_CS vertex) 
	{
		this.vertex = vertex;
	}
	public String toString() 
	{
		return "CostOptimization_CS [cost=" + cost + ", vertex=" + vertex + ", previousVertexId=" + previousVertexId
				+ ", gateNodeNumber=" + gateNodeNumber + "]";
	}
}
