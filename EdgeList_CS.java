/*
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class is one of two classes used to implement a edges/gates linked list.
 */
public class EdgeList_CS 
{
	private EdgeNode_CS head;  //head of linked list
	
	/**
	 * add() adds the parameter to the linked list while maintaining ascending order according to the edge weight. O(n) time complexity
	 * @param edge - the edge node to add to the linked list
	 * @return - none
	 */
	public void add(EdgeNode_CS edge) 
	{
		if(this.head == null)  //adding node to empty linked list
		{
			this.head = edge;
		}
		else 
		{
			EdgeNode_CS currentNode = this.head;  //LCV
			boolean placed = false;  //will remain false unit the edge is added to the linked list, LCV
			
			while(currentNode != null && placed == false) 
			{
						/*
						 *  if at the end of the linked list add the edge, or if the next edge node's weight is 
						 *  greater, add the edge node before the next edge node with greater weight.
						 */
				if(currentNode.getNextNode() == null || currentNode.getNextNode().getNode().getWeight() > edge.getNode().getWeight())
				{
					edge.setNextNode(currentNode.getNextNode());
					currentNode.setNextNode(edge);
					placed = true;
				}
				else 
				{
					currentNode = currentNode.getNextNode();
				}
			}
		}
	}
	/**
	 * remove() removes the specified edge node from the linked list, if it is in the linked list. O(n) time complexity
	 * @param searchFor - uses an edge object to search for the edge node to remove
	 * @return - returns true if the searched for node was removed, returns false otherwise
	 */
	public boolean remove(Edge_CS searchFor) 
	{
		EdgeNode_CS previousNode = this.head;  //tracks one node behind currentNode
		
		if(previousNode.getId() == searchFor.getId())  //if the edge to remove is the head
		{
			this.head = previousNode.getNextNode();  //setting the head to the successor of the current head
			return true;
		}
		EdgeNode_CS currentNode = this.head.getNextNode();  //LCV
		
		while(currentNode != null) 
		{
			if(currentNode.getId() == searchFor.getId()) 
			{
				previousNode.setNextNode(currentNode.getNextNode());  //setting the previous node's next node to current node's next node
				return true;
			}
			previousNode = currentNode;  //moving to the next node
			currentNode = currentNode.getNextNode();  //moving to the next node
		}
		return false;
	}
	public EdgeList_CS() 
	{
		this.head = null;
	}
	public EdgeList_CS(EdgeNode_CS head) 
	{
		this.head = head;
	}
	public EdgeNode_CS getHead() {
		return head;
	}
	public void setHead(EdgeNode_CS head) {
		this.head = head;
	}
	public String toString() 
	{
		return "EdgeList_CS [head=" + head + "]";
	}
	
	
}
