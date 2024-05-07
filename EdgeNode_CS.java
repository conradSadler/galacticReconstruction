/*
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class is one of two classes that implement a linked list for edges/gates. This class holds everything needed for a edge node in the linked list.
 */

import java.util.Arrays;

public class EdgeNode_CS
{
	private EdgeNode_CS nextNode;  //the next EdgeNode
	private Edge_CS node;  //the EdgeNode's data
	private boolean[] activeEdge;  //has the edge been built and hence is active?
	private int id;  //stores the identifier
	
	public EdgeNode_CS() 
	{
		this.nextNode = null;
		this.node = null;
		this.activeEdge = new boolean[1];
		this.id = -1;
	}
	public EdgeNode_CS(Edge_CS node,int id) 
	{
		this.node = node;
		this.nextNode = null;
		this.activeEdge = new boolean[1];
		this.id = id;
	}
	/**
	 * This method creates a new EdgeNode_CS object from the parameter EdgeNode_CS except that the vertexTo and vertexFrom are swapped
	 * @param copyNode - the EdgeNode_CS to replicate
	 * @return - a EdgeNode_CS that in all aspects resembles the parameter EdgeNode_CS but the vertexTo and vertexFrom are swapped
	 */
	public EdgeNode_CS copySwap(EdgeNode_CS copyNode) 
	{
		EdgeNode_CS copiedNode = new EdgeNode_CS();
		copiedNode.node = new Edge_CS();
		copiedNode.id = copyNode.id;
		copiedNode.node.setId(copyNode.id);
		copiedNode.node.setWeight(copyNode.node.getWeight());
		copiedNode.node.setVertexFrom(copyNode.node.getVertexTo());
		copiedNode.node.setVertexTo(copyNode.node.getVertexFrom());
		copiedNode.activeEdge = copyNode.activeEdge;
		return copiedNode;
	}
	public EdgeNode_CS getNextNode() 
	{
		return nextNode;
	}
	public void setNextNode(EdgeNode_CS nextNode) 
	{
		this.nextNode = nextNode;
	}
	public Edge_CS getNode() 
	{
		return node;
	}
	public void setNode(Edge_CS node) 
	{
		this.node = node;
	}
	public boolean isActiveEdge() 
	{
		return activeEdge[0];
	}
	public void setActiveEdge(boolean activeEdge) 
	{
		this.activeEdge[0] = activeEdge;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public boolean[] getActiveEdge() 
	{
		return activeEdge;
	}
	public void setActiveEdge(boolean[] activeEdge) 
	{
		this.activeEdge = activeEdge;
	}
	public String toString() 
	{
		return "EdgeNode_CS [nextNode=" + nextNode + ", node=" + node + ", activeEdge=" + Arrays.toString(activeEdge)
				+ ", id=" + id + "]";
	}
}
