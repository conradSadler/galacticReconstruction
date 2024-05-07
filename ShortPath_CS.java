import java.util.HashMap;
/**
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class was created because the optimized path method in constructionGates needed to return variables of multiple types(Integer,String, and some way to know what vertices were in the graph).
 */
public class ShortPath_CS 
{
	private int totalCost;  //stores the total optimized cost
	private HashMap<Integer,CostOptimization_CS> nodesInGraph;  //stores the HashMap created in the optimized path method in constructionGates. This allows access to the vertices in the graph
	private String gatesBuilt;  //stores the gates that are built for output later
	
	public ShortPath_CS() 
	{
		this.totalCost = -1;
		this.nodesInGraph = null;
		this.gatesBuilt = null;
	}
	public  ShortPath_CS(int totalCost, HashMap<Integer,CostOptimization_CS> nodesInGraph, String gatesBuilt) 
	{
		this.totalCost = totalCost;
		this.nodesInGraph = nodesInGraph;
		this.gatesBuilt = gatesBuilt;
	}
	public  ShortPath_CS(int totalCost, String gatesBuilt) 
	{
		this.totalCost = totalCost;
		this.nodesInGraph = null;
		this.gatesBuilt = gatesBuilt;
	}
	public int getTotalCost() 
	{
		return totalCost;
	}
	public void setTotalCost(int totalCost) 
	{
		this.totalCost = totalCost;
	}
	public HashMap<Integer,CostOptimization_CS> getNodesInGraph() 
	{
		return nodesInGraph;
	}
	public void setNodesInGraph(HashMap<Integer,CostOptimization_CS> nodesInGraph) 
	{
		this.nodesInGraph = nodesInGraph;
	}
	public String getGatesBuilt() 
	{
		return gatesBuilt;
	}
	public void setGatesBuilt(String gatesBuilt) 
	{
		this.gatesBuilt = gatesBuilt;
	}
	public String toString() 
	{
		return "ShortPath_CS [totalCost=" + totalCost + ", nodesInGraph=" + nodesInGraph + ", gatesBuilt=" + gatesBuilt
				+ "]";
	}
}
