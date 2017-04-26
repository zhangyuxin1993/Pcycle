package groupwork;

import General.Constant;
import General.file_out_put;
//import MainFunction.CycleAndNodepair;
import Main.CycleOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//import OFDM.Corefunction;
//import OFDM.Lightpathnode;
//import OFDM.VariableServiceUnit;


import Network.Layer;
import Network.Link;
import Network.Node;
import Subgraph.Cycle;
import Subgraph.LinearRoute;
//import graphalgorithms.SearchConstraint;

public class RouteSearching {
	
	
	public void Dijkstras(Node srcNode, Node destNode, Layer layer, LinearRoute newRoute, SearchConstraint constraint){
		ArrayList<Node> visitedNodeList = new ArrayList<Node>();
		visitedNodeList.clear();

		HashMap<String, Node> nodelist = layer.getNodelist();
		Iterator<String> iternode = nodelist.keySet().iterator();
		while (iternode.hasNext()) {
			Node nownode=(Node) (nodelist.get(iternode.next()));
			System.out.println("节点名： "+nownode.getName());
			 for(int m=0;m<nownode.getNeinodelist().size();m++){
				 System.out.println(nownode.getNeinodelist().get(m).getName());
			 }
			
		}
		
		//initialize all the node states
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
		    Node node = (Node)(map.get(iter.next()));
		    node.setStatus(Constant.UNVISITED);
		    node.setParentnode(null);
		    node.setCost_from_src(10000000);
		    node.setHop_from_src(10000000);
		} 
		
		
		//Initialization 
		Node currentNode = srcNode;
		currentNode.setCost_from_src(0);
		currentNode.setHop_from_src(0);
		currentNode.setStatus(Constant.VISITEDTWICE);
		
		if(constraint == null){
			for(Node node : currentNode.getNeinodelist()){
				if(node.getStatus() == Constant.UNVISITED){
					Link link = layer.findlink(currentNode,node);			
					node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
					node.setHop_from_src(currentNode.getHop_from_src()+1);
					node.setStatus(Constant.VISITED);
					node.setParentnode(currentNode);
					visitedNodeList.add(node);
				}			
			}
		}
		else {
			for(Node node : currentNode.getNeinodelist()){
				for(int b=0;b<currentNode.getNeinodelist().size();b++){
						System.out.println(currentNode.getNeinodelist().get(b).getName());
					}
				System.out.println("当前节点名字："+currentNode.getName());
				System.out.println("相邻节点名字："+node.getName());
				if(!constraint.getExcludednodelist().contains(node)){
					if(node.getStatus() == Constant.UNVISITED){
						Link link = layer.findlink(currentNode,node);	
						if(!constraint.getExcludedlinklist().contains(link)){
//							System.out.println("链路： "+link.getName());
							node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
							node.setHop_from_src(currentNode.getHop_from_src()+1);
							node.setStatus(Constant.VISITED);
							node.setParentnode(currentNode);
							visitedNodeList.add(node);
						}					
					}
				}							
			}
			
		}
		
		//find the node with the lowest cost from the visited node list
		currentNode = this.getLowestCostNode(visitedNodeList);
		if(currentNode !=null){
			while(!currentNode.equals(destNode)){	
			
				//set the current node double visited
				currentNode.setStatus(Constant.VISITEDTWICE);
				//remove the node from the visited node list
				visitedNodeList.remove(currentNode);			
				
				//navigate the neighboring nodes of the current node
				if(constraint == null){
					for(Node node : currentNode.getNeinodelist()){
						if(node.getStatus() == Constant.UNVISITED){ //if the neighbor node is not visited
							Link link = layer.findlink(currentNode,node);
							node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
							node.setHop_from_src(currentNode.getHop_from_src()+1);
							node.setStatus(Constant.VISITED);
							node.setParentnode(currentNode);
							visitedNodeList.add(node);
						}
						else if(node.getStatus() == Constant.VISITED){ //if the neighbor node is first visited
							Link link = layer.findlink(currentNode,node);
							if(node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()){
								//update the node status
								node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
								node.setParentnode(currentNode);						
							}
						}
					}
				}
				else{
					for(Node node : currentNode.getNeinodelist()){
						if(!constraint.getExcludednodelist().contains(node)){
							if(node.getStatus() == Constant.UNVISITED){ //if the neighbor node is not visited
								
								Link link = layer.findlink(currentNode,node);
								if(!constraint.getExcludedlinklist().contains(link)){
									node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
									node.setHop_from_src(currentNode.getHop_from_src()+1);
									node.setStatus(Constant.VISITED);
									node.setParentnode(currentNode);
									visitedNodeList.add(node);
								}						
							}
							else if(node.getStatus() == Constant.VISITED){ //if the neighbor node is first visited
								String name;
								if(currentNode.getIndex()<node.getIndex())
									name = currentNode.getName()+"-"+node.getName();
								else
									name = node.getName()+"-"+currentNode.getName();
								Link link = layer.getLinklist().get(name);
								if(!constraint.getExcludedlinklist().contains(link)){
									if(node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()){
										//update the node status
										node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
										node.setHop_from_src(currentNode.getHop_from_src()+1);
										node.setParentnode(currentNode);						
									}
								}
								
							}
						}
						
					}
					
				}
				
				//find the node with the lowest cost from the visited node list
				currentNode = this.getLowestCostNode(visitedNodeList);
				if(currentNode == null)
					break;
			}
		}
		
		
		//clear the route
		newRoute.getNodelist().clear();
		newRoute.getLinklist().clear();
		newRoute.getObjectlist().clear();
		//add the visited nodes into the node list
		currentNode = destNode;
		if(destNode.getParentnode()!=null){
			newRoute.getNodelist().add(0, currentNode);
			newRoute.getObjectlist().add(0, currentNode);
			
			while(currentNode != srcNode){
				Link link = layer.findlink(currentNode, currentNode.getParentnode());
				newRoute.getObjectlist().add(0, link);
				newRoute.getLinklist().add(0, link);
				
				currentNode = currentNode.getParentnode();
				newRoute.getNodelist().add(0, currentNode);
				newRoute.getObjectlist().add(0, currentNode);
			}
		}			
	}
	
	//find a node from the visited node list that is closest to the src node
	private Node getLowestCostNode(ArrayList<Node> visitedNodeList){
		
		Node currentnode = null;
		double current_cost_to_desc = 100000000;
		for(Node node : visitedNodeList){
			if(node.getCost_from_src() < current_cost_to_desc){
				currentnode = node;
				current_cost_to_desc = node.getCost_from_src();				
			}			
		}
		return currentnode;		
	}
	
	/**
	 * find K-disjoint shortest path routes
	 */
//	public int Kshortest(Node srcNode, Node destNode, Layer layer, int k, ArrayList<LinearRoute> routelist) {
//		
//		routelist.clear();
//		SearchConstraint constraint = new SearchConstraint(1000000, 100000);
//		
//		int num_found = 0; //number of found route
//		while(true){
//			LinearRoute newRoute = new LinearRoute("",	0, "", Constant.WORKING);
//			this.Dijkstras(srcNode, destNode, layer, newRoute, constraint);
//			if(newRoute.getLinklist().size()>0){
//				routelist.add(newRoute);
//				constraint.getExcludedlinklist().addAll(newRoute.getLinklist());
//				num_found++;
//				if(num_found == k)
//					break;
//			}
//			else
//				break;
//		}
//		return num_found; // the number of found routes
//	}
	public void Kshortest(Node srcNode, Node destNode, Layer layer, int k, ArrayList<LinearRoute> routelist) {
		
		routelist.clear();
		SearchConstraint constraint = new SearchConstraint(1000000, 100000);
		
		int num_found = 0; //number of found route
		while(true){
			LinearRoute newRoute = new LinearRoute("",	0, "",0);
			this.Dijkstras(srcNode, destNode, layer, newRoute, constraint);
			if(newRoute.getLinklist().size()>0){
				routelist.add(newRoute);
				//newRoute.OutputRoute_node(newRoute);
				constraint.getExcludedlinklist().addAll(newRoute.getLinklist());
				num_found++;
				if(num_found == k)
					break;
			}
			else
				break;
		}
		//return num_found; // the number of found routes
		
	}
	/**
	 * find all routes between a pair of nodes
	 */
	public int findAllRoute(Node nodeA, Node nodeB, Layer layer, SearchConstraint constraint,int k, ArrayList<LinearRoute> routelist) {
		int num_route = 0;
		
		//ArrayList<Route> routeList = new ArrayList<Route>();
		int hoplimit = 100000;
		if(constraint != null)
			hoplimit = constraint.getMax_hop();
					
			
		ArrayList<Message> messageList = new ArrayList<Message>(); //the list of messages that exist in the current network
		
		Message newMessage = new Message(nodeA);
				
		messageList.add(newMessage);
		
		while(!messageList.isEmpty()){
			//get the header message
			Message currentmessage = messageList.remove(0);
			Node currentNode = currentmessage.getCurrentNode();
			
			if(currentNode == nodeB){
				//find one route
				LinearRoute newroute = new LinearRoute("", 0, "", Constant.WORKING);
				newroute.getNodelist().addAll(currentmessage.getVisitedNodelist());
				routelist.add(newroute);
				newroute.ConvertfromNodeListtoLinkandObjectList();	
				if(routelist.size()==k)
					break;
			}
			else{
				if(currentmessage.getVisitedNodelist().size()-1 < hoplimit)
					for(Node neinode : currentNode.getNeinodelist()){
						if(!currentmessage.getVisitedNodelist().contains(neinode)){
							
							newMessage = new Message(neinode);
							newMessage.getVisitedNodelist().addAll(0, currentmessage.getVisitedNodelist());
							messageList.add(newMessage);
							
						}							
					}				
			}
			
			
		}
	
		return routelist.size();
		
	}
	/**
	 * find a cycle of cycles start from a certain nodes
	 */
	public void findAllCycle(Node node, Layer layer, SearchConstraint constraint, ArrayList<Cycle> cyclelist) {
		
		int hoplimit = 100000;
		if(constraint != null)
			hoplimit = constraint.getMax_hop();
		
		//ArrayList<Cycle> cyclelist = new ArrayList<Cycle>();
		
		ArrayList<Message> messageList = new ArrayList<Message>(); //the list of messages that exist in the current network
		
		Message newMessage = new Message(node);
				
		messageList.add(newMessage);
		
		//int hop_count = 0;
		
		while(!messageList.isEmpty()){
			//get the header message
			Message currentmessage = messageList.remove(0);
			Node currentNode = currentmessage.getCurrentNode();
			
			if(currentNode == node && currentmessage.getVisitedNodelist().size() > 3){
				//find one route
				Cycle newCycle = new Cycle("",0,"");
				newCycle.getNodelist().addAll(currentmessage.getVisitedNodelist());
				//if the new cycle has existed
				Boolean existing = false;
				for(Cycle cycle : cyclelist)
					if(newCycle.isEqual(cycle)){
						existing = true;
						break;
					}
				/*
				 * 以下为附加
				 */
//				/*
				int find=0;
				int add=0;
//				 for(int newcyclenode1=0;newcyclenode1<newCycle.getNodelist().size()-1;newcyclenode1++){
				 int newcyclenode1=0;
				 Node newcycleSnode1=newCycle.getNodelist().get(newcyclenode1);
				 Node newcycleSnode2=newCycle.getNodelist().get(newcyclenode1+1); 
					 
				for(int c=0;c<cyclelist.size();c++)			
				{
					find=0;
					Cycle cycle=cyclelist.get(c);
//					System.out.println(cyclelist.size());
//					CycleOutput out =new CycleOutput();
//					System.out.println("newcycle ");
//					out.cycleoutput(newCycle, "E:\\programFile\\Cycle.dat");
//					System.out.println("cycle  ");
					
//					out.cycleoutput(cycle, "E:\\programFile\\Cycle.dat");
				 if(newCycle.getNodelist().size()==cycle.getNodelist().size()){
					 for(int cyclenode1=0;cyclenode1<cycle.getNodelist().size()-1;cyclenode1++){	
						//取出cycle中的两个节点	
							 Node cycleSnode1=cycle.getNodelist().get(cyclenode1);
							 Node cycleSnode2=cycle.getNodelist().get(cyclenode1+1);							
//							 System.out.println("newcycle上的链路"+newcycleSnode1.getName()+"  "+newcycleSnode2.getName());
//							 System.out.println("cycle上的链路"+cycleSnode1.getName()+"  "+cycleSnode2.getName());
			
					if((cycleSnode1.equals(newcycleSnode1)&&cycleSnode2.equals(newcycleSnode2))||(cycleSnode2.equals(newcycleSnode1)&&cycleSnode1.equals(newcycleSnode2))){				 
						 newcyclenode1++;
						if(newcyclenode1<newCycle.getNodelist().size()-1){
								 newcycleSnode1=newCycle.getNodelist().get(newcyclenode1);
								newcycleSnode2=newCycle.getNodelist().get(newcyclenode1+1);
								
								cyclenode1=-1;
						 }
//						 System.out.println(newcyclenode1);
						if(newcyclenode1==newCycle.getNodelist().size()-1) 
							{existing = true;break;}
							
							}	 								
							 }
		 
				 }
				 
				}
							
						
//	*/
				
				if(!existing)
				{

					cyclelist.add(newCycle);
					newCycle.ConvertfromNodeListtoLinkandObjectList();
				}
			}
			else{
				if(currentmessage.getVisitedNodelist().size()-1 < hoplimit)
					for(Node neinode : currentNode.getNeinodelist()){
						if(constraint != null && !constraint.getExcludednodelist().contains(neinode)){
							if(!currentmessage.getVisitedNodelist().contains(neinode)){	
								newMessage = new Message(neinode);
								newMessage.getVisitedNodelist().addAll(0, currentmessage.getVisitedNodelist());
								messageList.add(newMessage);						
							}	
							else if(neinode == node && currentmessage.getVisitedNodelist().size() > 2){
								newMessage = new Message(neinode);
								newMessage.getVisitedNodelist().addAll(0, currentmessage.getVisitedNodelist());
								messageList.add(newMessage);
							}
						}						
					}				
			}
			
			
		}
		
			
		
	}
	/********************************************
	 * Traveling salesman problem
	 ********************************************/
	public void NearestNeighbor(Node srcNode, ArrayList<Node> nodelist, ArrayList<Link> linklist, LinearRoute route){
		/**
		 * initialize all the node status
		 */
		for(Node node : nodelist)
			node.setStatus(Constant.UNVISITED);
		Node currentnode = srcNode;
		Node nextNode = null;
		Link currentlink = null;
		double min_cost = 1000000;
		currentnode.setStatus(Constant.VISITED);
		route.getNodelist().add(currentnode);
		route.getObjectlist().add(currentnode);
		
		while(true){
			min_cost = 1000000;
			nextNode = null;
			currentlink = null;
			for(Node node : currentnode.getNeinodelist()){
				if(node.getStatus()==Constant.UNVISITED){
					Link link = this.findLink(currentnode, node, linklist);
					if(link.getCost()<min_cost){
						min_cost = link.getCost();
						currentlink = link;
						nextNode = node;
					}
				}								
			}
			currentnode = nextNode;
			currentnode.setStatus(Constant.VISITED);
			route.getNodelist().add(currentnode);
			route.getLinklist().add(currentlink);
			route.getObjectlist().add(currentlink);
			route.getObjectlist().add(currentnode);
			//check if all the nodes are visited
			if(this.isAllvisted(nodelist))
				break;			
		}	
	}
	
	/**
	 * find a link from a link list
	 */
	public Link findLink(Node nodeA, Node nodeB, ArrayList<Link> linklist){
		for(Link link : linklist){
			if(link.getNodeA().equals(nodeA)&&link.getNodeB().equals(nodeB))
				return link;
			if(link.getNodeA().equals(nodeB)&&link.getNodeB().equals(nodeA))
				return link;
		}
		return null;
	}
	/**
	 * checke if all the nodes are visited
	 */
	public boolean isAllvisted(ArrayList<Node> nodelist){
		boolean allvisted = true;
		for(Node node : nodelist){
			if(node.getStatus() == Constant.UNVISITED){
				allvisted = false;
				break;
			}			
		}
		return allvisted;
	}

	

}
