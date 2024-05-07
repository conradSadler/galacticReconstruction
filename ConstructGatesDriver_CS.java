import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
/*
 * Name: ConRad Sadler
 * Date: 04/27/2024
 * This class implements all other classes to build the edges/gates connecting vertices/colonies
 */
public class ConstructGatesDriver_CS
{
	static Scanner scannerInput;
	
	public static void main(String args[]) throws FileNotFoundException 
	{
		String fileName = "galax.txt";  //name of the input File
		File dataFile = new File(fileName);  //creating a file object with the file name
		
		if(dataFile.exists()== false) 
		{
			System.out.println(fileName+" does not exist");
			int unsuccessfulTermination = 1;  //The system is existing the program at a unsuccessful termination because the number is not 0.
			System.exit(unsuccessfulTermination);
		}
		scannerInput = new Scanner(dataFile); //creating a scanner that will scan the file object
		HashMap<Integer,EdgeNode_CS> galacticGateQuickRef = new HashMap<Integer,EdgeNode_CS>();  //HashMap for quickly getting specified EdgeNodes
		String[] fileLineToArray = scannerInput.nextLine().strip().split(" ");  //getting the number of colonies and number of proposals from the first line of file input
		int proposals = Integer.parseInt(fileLineToArray[1]);  //holds the number of proposals
		fileLineToArray = scannerInput.nextLine().strip().split(" ");  //getting the wealths of all the colonies from the second line of file input
		Vertex_CS[] allReadInVertices = new Vertex_CS[fileLineToArray.length];  //where all read in vertices will be stored
		for(int i = 0; i<fileLineToArray.length; i++) 
		{
			allReadInVertices[i] = new Vertex_CS(i+1,Integer.parseInt(fileLineToArray[i]));  //creating new vertex and adding it to the HashMap (colonies will have a identifier starting at 1 to ...)
		}
		EdgeNode_CS[] proposedGates = new EdgeNode_CS[proposals];  //store all the proposed gates/edges
		Vertex_CS vertexTo = null;  //store the edge's destination vertex
		Vertex_CS vertexFrom = null;  //store the edge's starting vertex
		int costToBuild = -1;  //store the cost to build the edge/gate
		
		for(int j = 0; j < proposedGates.length; j++) 
		{
			fileLineToArray = scannerInput.nextLine().strip().split(" ");  //getting the vertexTo, vertexFrom, and cost from file.in
			vertexFrom = allReadInVertices[(Integer.parseInt(fileLineToArray[0])-1)%allReadInVertices.length];  //getting the vertexFrom, from the Vertex_CS array
			vertexTo = allReadInVertices[(Integer.parseInt(fileLineToArray[1])-1)%allReadInVertices.length];  //getting the vertexTO, from the Vertex_CS array
			

			if(vertexTo != null && vertexFrom != null) 
			{
				costToBuild = Integer.parseInt(fileLineToArray[2]);  //setting the cost of the edge/gate
				Edge_CS potentialGate = new Edge_CS(vertexFrom,vertexTo,costToBuild,j);  //creating gate object with vertexFrom, vertexTo, and j
				EdgeNode_CS PotentialGateNode = new EdgeNode_CS(potentialGate,j);  //creating gate node from potentialGate
				galacticGateQuickRef.put(j, PotentialGateNode);  //adding gateNode to galaxy HashMap
				proposedGates[j] = PotentialGateNode; //adding PotentialGateNode to EdgeNode_CS array
				vertexFrom.getEdges().add(PotentialGateNode);  //adding PotentialGateNode to the starting vertex edges linked list
				vertexTo.getEdges().add(PotentialGateNode.copySwap(PotentialGateNode));  //adding a copy of PotentialGateNode to the destination vertex edges linked list with reversed vertexFrom and vertexTo pointers
			}
		}
		scannerInput.close();  //end of reading in from file
		System.out.println("Enter 'p' For Priority Build Or 'o' For Optimized Build");
		Scanner scannerInput = new Scanner(System.in);  //initializing new scanner to get user input
		
		if(scannerInput.nextLine().toLowerCase().toCharArray()[0] == 'p') 
		{
			System.out.println("Priority Build:\n");
			priorityBuild(proposedGates);
		}
		else 
		{
			ShortPath_CS pathInformation = optimizedBuild(allReadInVertices[0],galacticGateQuickRef,null);  //getting the ShortPath_CS object which will tell what vertices are in the graph that the starting vertex is in
			System.out.println("\nHow Certain Do You Want To Be That The Resulting Path Is The Shortest/Cheapest?");
			System.out.print("Enter Between "+(int)(100/pathInformation.getNodesInGraph().keySet().size())+"% And 100% Certainty: ");
			int amountToCheck = scannerInput.nextInt();  //getting the percentage of vertices to check from user input
			
			if(amountToCheck<=(int)(100/pathInformation.getNodesInGraph().keySet().size()))  //if the amountToCheck is, at, or below minimum, output the initial pathInformation cost and path
			{
				System.out.println("Optimized Build:\n");
				System.out.println(pathInformation.getGatesBuilt());
				System.out.println("Total Building Cost: "+pathInformation.getTotalCost());
			}
			else 
			{
				int cheapestCost = pathInformation.getTotalCost();  //initializing the total cost to the first cost created from the first call to optimizedBuild()
				String cheapestCostBuildLog = pathInformation.getGatesBuilt();  //initializing the gates built string to the first string created from the first call to optimizedBuild()
				int checkedAmount = 1;  //LCV starts at one because, one vertex was all ready checked when pathInformation was created
				Object[] keysInSet = pathInformation.getNodesInGraph().keySet().toArray();
				amountToCheck = (int)((amountToCheck*.01) * keysInSet.length);  //calculating the number of vertices to check from percentage
				
				while(checkedAmount < amountToCheck) 
				{
					if((int)keysInSet[checkedAmount] != allReadInVertices[0].getId()) 
					{
						pathInformation = optimizedBuild(pathInformation.getNodesInGraph().get((int)keysInSet[checkedAmount]).getVertex(),galacticGateQuickRef,pathInformation);  //getting the shortest path from the starting vertex
						
						if(pathInformation.getTotalCost() < cheapestCost)  //a better path has been found, updating the cheapestCost and cheapestCostBuildLog
						{
							cheapestCost = pathInformation.getTotalCost();
							cheapestCostBuildLog = pathInformation.getGatesBuilt();
						}
					}
					checkedAmount++;
				}
				System.out.println("\nOptimized Build:\n");
				System.out.println(cheapestCostBuildLog);
				System.out.println("Total Building Cost: "+cheapestCost);
			}
		}
		scannerInput.close();  //end of reading in user input
	}
	/**
	 * graphSearch() will perform a breadth-first search or Dijkstra's shortest path depending on the parameters passed in.
	 * @param start - The vertex to start from
	 * @param end - (optional) The vertex at which the method will terminate if found
	 * @param findShortestPathToVertices - if true, the method will perform Dijkstra's algorithm, else the method will perform a breadth-first search
	 * @return HashMap<Integer,CostOptimization_CS> 
	 * - If the method performed a breadth-first search without terminating on the end 
	 * 	 parameter, the Hash map will have one stored item @ key 1. This CostOptimization object @ key 1 will have a cost set to -3.
	 * - If the method performed a breadth-first search and terminated on the end 
	 * 	 parameter, the Hash map will have one stored item @ key 1. This CostOptimization object @ key 1 will have a cost set to -2.
	 * - If the method performed Dijkstra's algorithm, the HashMap will be populated with one CostOptimization object per vertex in the graph.
	 */
	public static HashMap<Integer,CostOptimization_CS> graphSearch(Vertex_CS start,Vertex_CS end,boolean findShortestPathToVertices) 
	{
		int pioneerTop = 0;  //the top of the queue
		int pioneerBack = 0;  //the back of the queue
		int discoveredBack = 0;  //the back of the discovered array
		Vertex_CS[] pioneerQueue = new Vertex_CS[1];  //for un-checked vertices
		pioneerQueue[0] = start;  //adding the start vertex to the pioneer queue
		Vertex_CS[] discovered = new Vertex_CS[1];  //for discovered vertices
		HashMap<Integer,CostOptimization_CS> vertexRecord = new HashMap<Integer,CostOptimization_CS>();  //for CostOptimization objects used for optimized build
		
		if(findShortestPathToVertices == true)
		{
			vertexRecord.put(start.getId(), new CostOptimization_CS(start,0));  //creating a CostOptimization object for the start vertex with a cost/distance of 0, because what is closer to the starting vertex than the starting vertex?
		}
		while(pioneerTop <= pioneerBack)  //while the top of the queue has not crossed the back of the queue
		{
			Vertex_CS currentVertex = pioneerQueue[pioneerTop];  //Dequeuing a vertex
			pioneerTop++; //moving the top right one;
			
			if(discoveredBack >= discovered.length) //resizing array if needed
			{
				discovered = Arrays.copyOf(discovered,(discoveredBack+1)*2);
			}
			
			discovered[discoveredBack] = currentVertex;  //adding currentVertex to the discovered list
			discoveredBack++;  //moving back right one
			
			EdgeList_CS edgesOfCurrentVertex = currentVertex.getEdges();  //getting the edge linked list that belongs to currentVertex
			EdgeNode_CS currentNode = edgesOfCurrentVertex.getHead();  //getting the head of the edge linked list
			CostOptimization_CS vertexToCostOpt;  //Temporarily stores a CostOptimization_CS object for comparison in the loop below
			int cumulativeCost;  //if finding the shortest path then this will store the current nodes cumulative cost to get to the starting vertex added with a connecting edge's cost
			boolean inDiscoveredList;  //if a searched for vertex is in the discovered list
			boolean inPioneerQueue;  //if a searched for vertex is in the pioneer list
			
			while(currentNode != null) 
			{
				if(findShortestPathToVertices == true || currentNode.getActiveEdge()[0] == true)  //if the method is executing a shortest path search, then all edges should be assessed. Else, only active edges need to be checked
				{
					if(pioneerBack+1 >= pioneerQueue.length)  //resizing array if needed
					{
						pioneerQueue = Arrays.copyOf(pioneerQueue,(pioneerBack+1)*2);
					}
					
					inDiscoveredList = false;  //is vertexTo in the discovered list?
					inPioneerQueue = false;  //it vertexTo in the pioneer list?
					
					for(int i = 0; i < discoveredBack;i++)  //checking the discovered list
					{
						if(discovered[i].getId() == currentNode.getNode().getVertexTo().getId())
						{
							inDiscoveredList = true;
						}
					}

					for(int i = 0; i <= pioneerBack;i++)  //checking the pioneerQueue
					{
						if(pioneerQueue[i].getId() == currentNode.getNode().getVertexTo().getId()) 
						{
							inPioneerQueue = true;
						}
					}
					if(inDiscoveredList == false && inPioneerQueue == false)
					{
						if(findShortestPathToVertices == true)
						{
									/*
									 * If it is a newly discovered vertex, and the method is doing a shortest path search, then a new CostOptimization object needs to be 
									 * created for the new vertex with a cost of the currentVertex's CostOptimization cost added to the cost/weight of the edge(currentNode.node) 
									 * that connects the currentVertex to the newly discovered vertex.
									 */
							vertexRecord.put(currentNode.getNode().getVertexTo().getId(), new CostOptimization_CS(currentNode.getNode().getVertexTo(),(vertexRecord.get(currentVertex.getId()).getCost()+currentNode.getNode().getWeight()),currentNode.getNode().getVertexFrom().getId(),currentNode.getId()));
						}
						pioneerBack++;  //moving the back of the pioneer right one
						pioneerQueue[pioneerBack] = currentNode.getNode().getVertexTo();  //adding to the back of the pioneer queue
					}
					else 
					{
						if(findShortestPathToVertices == true) 
						{
									/*
									 * Adding currentVertex's CostOptimization cost to the cost/weight of the edge(currentNode.node) 
									 * that connects the currentVertex to currentNode.node.vertexTo vertex.
									 */
							cumulativeCost = vertexRecord.get(currentVertex.getId()).getCost() + currentNode.getNode().getWeight(); 
							vertexToCostOpt = vertexRecord.get(currentNode.getNode().getVertexTo().getId());  //CostOptimization object for the vertexTo (currentNode.node.vertexTo) (also thought of as the destination vertex)
							
							if(cumulativeCost < vertexToCostOpt.getCost()) 
							{
										/*
										 * A shorter path has been found, so update the CostOptimization object for vertexTo (currentNode.node.vertexTo) (also thought of as the destination vertex)
										 */
								vertexToCostOpt.setCost(cumulativeCost);  //updating cost
								vertexToCostOpt.setGateNodeNumber(currentNode.getId());  //updating the closest gate on the shorallReadInVertices path to the starting vertex
								vertexToCostOpt.setPreviousVertexId(currentVertex.getId());  //updating the previousVertexID
							}
						}
					}
				}
				currentNode = currentNode.getNextNode();  //moving to next node
			}
			if(findShortestPathToVertices != true) 
			{
				for(Vertex_CS vertex : discovered)  //checking for the end vertex in the discovered list
				{
					if(vertex != null && vertex.getId() == end.getId()) 
					{
						vertexRecord.put(1, new CostOptimization_CS(-2));
						return vertexRecord;  //terminating on the end vertex with the CostOptimization object @ key 1 having a cost of -2
					}
				}
			}
		}
		if (findShortestPathToVertices != true)  //if the method is performing a breadth-first search and did not terminate on the end vertex
		{
			
			vertexRecord.put(1, new CostOptimization_CS(-3));
			return vertexRecord; //returning with the CostOptimization object @ key 1 having a cost of -3
		}
		return vertexRecord;
	}
	/**
	 * priorityBuild() assess if certain edges/gates should be built, are impossible to be built ,or are unnecessary to be built.
	 * The method assess these gates/edges in the order they are provided in the @param proposedGates.
	 * @param proposedGates - An Array of edge objects that were read in from file.in
	 * @return - none
	 */
	public static void priorityBuild(EdgeNode_CS[] proposedGates) 
	{
		for(int i = 0; i < proposedGates.length; i++) 
		{
			Edge_CS proposedGate = proposedGates[i].getNode();  //the current gate
			Vertex_CS destination = proposedGate.getVertexTo();  //where the proposed gate ends
			Vertex_CS startVertex = proposedGate.getVertexFrom();  //where the proposed gate begins
			System.out.println("Start Colony: "+startVertex.getId()+" With a Wealth Of: "+startVertex.getWealth()[0] +" Destination Colony: "+destination.getId()+" With a Wealth Of: "+destination.getWealth()[0]);
			if(graphSearch(startVertex,destination,false).get(1).getCost() == -3)  //if there is no current way to get from the startVertex to the destination Vertex
			{
				if(startVertex.getWealth()[0] >= proposedGate.getWeight() && destination.getWealth()[0] >= proposedGate.getWeight())  //if both colonies can afford to build the gate
				{
					startVertex.setWealth(startVertex.getWealth()[0] - proposedGate.getWeight());  //deducting the cost to build the gate/edge
					destination.setWealth(destination.getWealth()[0] - proposedGate.getWeight());  //deducting the cost to build the gate/edge
					startVertex.setWealth(startVertex.getWealth()[0] + destination.getWealth()[0]);  //combining both the vertices wealth
					destination.setWealth(startVertex.getWealth());  //combining both the vertices wealth
					proposedGates[i].setActiveEdge(true);  //setting the edge/gate to active
					System.out.println("BUILT\n");
				}
				else 
				{
					System.out.println("IMPOSSIBLE\n");
				}
			}
			else 
			{
				System.out.println("UNNECESSARY\n");
			}
		}
	}
	/**
	 * optimizedBuild() builds the shortest routes to connect all the colonies in the graph from a starting vertex.
	 * @param startVertex - where the method will start building out from
	 * @param galacticGateQuickRef - HashMap for quickly getting specified EdgeNodes
	 * @return - ShortPath_CS object that stores the totalCost of the build,the gates/edges built, and the optimizedVertexRecord HashMap from the first call to the method(used for getting all vertices in the given graph)
	 */
	public static ShortPath_CS optimizedBuild(Vertex_CS startVertex,HashMap<Integer,EdgeNode_CS> galacticGateQuickRef,ShortPath_CS recordPath) 
	{
		HashMap<Integer,CostOptimization_CS> optimizedVertexRecord = graphSearch(startVertex,null,true);  //getting the optimizedVertexRecord from calling graphSearch with parameters for a shortest path search
		HashMap<Integer,Boolean> activeGates = new HashMap<Integer,Boolean>();  //tracks what gates were activated while calculating the shortest path for a starting vertex
		CostOptimization_CS colonyToConnect = null;  //the colony that will be connected to the startVertex
		EdgeNode_CS connectorGate = null;  //the gate that leads to the next closest vertex to the startVertex
		String bestCostOutput = "";  //stores the gates built for the current path. Printed out later. 
		Vertex_CS currentVertexToConnect;  //stores the vertex that the edge/gate connects to
		Vertex_CS previousVertexToConnect;  //stores the vertex that the edge/gate comes from
		int totalCost = 0;  //total cost of the building project
		for(int i : optimizedVertexRecord.keySet())  // i loops through all the vertices because the vertices were given identifiers in sequential, ascending order from (1 to ...)
		{
			colonyToConnect = optimizedVertexRecord.get(i);  //getting the CostOptimization_CS object record of the colonyToConnect	
			connectorGate = galacticGateQuickRef.get(colonyToConnect.getGateNodeNumber());  //getting the gate/edge that was recorded in optimizedVertexRecord for the colonyToConnect
			
			while(colonyToConnect.getVertex().getId() != startVertex.getId()) //until a path has been made from the colonyToConnect back to the start vertex the loop will continue
			{
				if(activeGates.get(connectorGate.getId()) == null)  //don't want to try and activate a active gate/edge
				{
					activeGates.put(connectorGate.getId(),true);
							/*
							 * getting the vertices on each side of the edge/gate
							 */
					currentVertexToConnect = connectorGate.getNode().getVertexTo();
					previousVertexToConnect = connectorGate.getNode().getVertexFrom();
					totalCost+=connectorGate.getNode().getWeight();
							/*
							 * activating the gate/edge and recording that
							 */
					bestCostOutput+=("Gate "+connectorGate.getId()+ ", Between "+currentVertexToConnect.getId()+" And "+previousVertexToConnect.getId()+" Has Been Built At A Cost Of "+connectorGate.getNode().getWeight()+"\n");
					
				}
				colonyToConnect = optimizedVertexRecord.get(colonyToConnect.getPreviousVertexId());  //getting the next closest vertex to the startVertex
				connectorGate = galacticGateQuickRef.get(colonyToConnect.getGateNodeNumber());  //getting the gate/edge that was recorded in galacticGateQuickRef for the colonyToConnect
			}
			
		}
		if(recordPath == null)  //the ShortPath_CS object is intended to store all vertices in the graph,totalCost for the first startingVertex, and record the gates built for the current path
		{
			return new ShortPath_CS(totalCost,optimizedVertexRecord, bestCostOutput);  //creating and returning the only ShortPath_CS object that will be recycled in following calls to this method if required
		}
		else  //update the ShortPath_CS object with the most recent findings(i.e. the new totalCost and bestCostOutput)
		{
			recordPath.setGatesBuilt(bestCostOutput);  //changing the gates built string to match the most recent gates/edges built
			recordPath.setTotalCost(totalCost);  //changing the total cost of building the gates/edges to the most recent totalCost
			return recordPath;
		}
	}
	/*
	 * Problems:
	 * 
	 * - I originally had most data members as protected, and once I figured out that protected data members would not be acceptable in their current use case, I had 
	 * 	 to switch all of them into protected data members. This seemingly simple task broke my code and in-order to fix it quickly, I did some "hop-scotching",
	 * 	 where I would take classes of the past saved version and use those classes I knew worked to isolate my problem down to one class. Then after isolating the
	 * 	 problem to the specific class I was able to identify the method causing the null pointer exception.
	 */
}
