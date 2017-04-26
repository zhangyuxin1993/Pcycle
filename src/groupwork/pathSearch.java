package groupwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import General.Constant;
import Network.*;
import Subgraph.*;

public class pathSearch {
	
	public Node getLowestCostNode(ArrayList<Node> visitedNodeList){
		Node currentNode=null;
		double current_cost_to_desc=Constant.maxium;
		for(Node node:visitedNodeList){
			if(node.getCost_from_src()<current_cost_to_desc){
				currentNode=node;
				current_cost_to_desc=node.getCost_from_src();
			}
		}
		return currentNode;
		
	}
	public void Dijkstras(String name1,String name2,Layer layer,LinearRoute newRoute,SearchConstraint constraint){
        Node srcNode=layer.findNode(name1, layer);
        Node desNode=layer.findNode(name2, layer);
		ArrayList<Node>visitedNodeList=new ArrayList<Node>();	
        visitedNodeList.clear();
        
        //≥ı ºªØ
        HashMap<String,Node>map=layer.getNodelist();
        Iterator<String>iter=map.keySet().iterator();
        while(iter.hasNext()){
     	   Node node=(Node)(map.get(iter.next()));
     	   node.setStatus(Constant.UNVISITED);
     	   node.setParentnode(null);
     	   node.setCost_from_src(Constant.maxium);
     	   node.setHop_from_src(Constant.maxium);
        }
        Node currentNode=srcNode;
        currentNode.setCost_from_src(0);
        currentNode.setHop_from_src(0);
        currentNode.setStatus(Constant.VISITEDTWICE);
        if(constraint==null){
     	   for(Node node:currentNode.getNeinodelist()){
     		   if(node.getStatus()==Constant.UNVISITED){
     			   Link link=layer.findlink(currentNode, node);
     			   node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
     			   node.setHop_from_src(currentNode.getHop_from_src()+1);
     			   node.setStatus(Constant.VISITED);
     			   node.setParentnode(currentNode);
     			   visitedNodeList.add(node);
     		   }
     	   }
        }
        else{
     	   for(Node node:currentNode.getNeinodelist()){
     		   if(!constraint.getExcludednodelist().contains(node)){
     			   if(node.getStatus()==Constant.UNVISITED){
     				   Link link=layer.findlink(currentNode, node);
     				  if(!constraint.getExcludedlinklist().contains(link)){
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
        currentNode=getLowestCostNode(visitedNodeList);
        if(currentNode!=null){
     	   while(currentNode.equals(desNode)){
     		   currentNode.setStatus(Constant.VISITEDTWICE);
     	       visitedNodeList.remove(currentNode);
     	       if(constraint==null){
     	    	   for(Node node:currentNode.getNeinodelist()){
     	    		   if(node.getStatus()==Constant.UNVISITED){
     	    			   Link link=layer.findlink(currentNode, node);
     	    			   node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
     	    			   node.setHop_from_src(currentNode.getHop_from_src()+1);
     	    			   node.setStatus(Constant.VISITED);
     	    			   node.setParentnode(currentNode);
             			   visitedNodeList.add(node);
     	    		   }
     	    		   else if(node.getStatus()==Constant.VISITED){
     	    			   Link link=layer.findlink(currentNode, node);
     	    			   if(node.getCost_from_src()>currentNode.getCost_from_src()+link.getCost()){
     	    			   node.setCost_from_src( currentNode.getCost_from_src()+link.getCost());
     	    			   node.setHop_from_src(currentNode.getHop_from_src()+1);
     	    		       node.setParentnode(currentNode);  
     	    		       }
     	    		   }
     	    	    }
     	       }
     	       else{
     	    	   for(Node node:currentNode.getNeinodelist()){
     	    		   if(!constraint.getExcludednodelist().contains(node)){
     	    			   if(node.getStatus()==Constant.UNVISITED){
     	    				   Link link=layer.findlink(currentNode, node);
     	    				   if(!constraint.getExcludedlinklist().contains(link)){
     	    					   node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
     	    					   node.setHop_from_src(currentNode.getHop_from_src()+1);
     	    					   node.setStatus(Constant.VISITED);
     	    					   node.setParentnode(currentNode);
     	    					   visitedNodeList.add(node);
     	    				   }
     	    			   }
     	    				   else if(node.getStatus()==Constant.VISITED){
     	    					  Link link=layer.findlink(currentNode, node);
     	    					  if(!constraint.getExcludedlinklist().contains(link)){
     	    						  if(node.getCost_from_src()>currentNode.getCost_from_src()+link.getCost()){
     	    						  node.setCost_from_src( currentNode.getCost_from_src()+link.getCost());
     	    					      node.setHop_from_src(currentNode.getHop_from_src()+1);
     	    						  node.setParentnode(currentNode);
     	    					  }
     	    			   }
     	    		   }
     	    	   }
     	       }
     	   }
     	   currentNode =this.getLowestCostNode(visitedNodeList);
     	   if(currentNode==null);
     	   break;
        }
    }
   newRoute.getNodelist().clear();
   newRoute.getLinklist().clear();
   newRoute.getObjetc().clear();
   currentNode=desNode;
   if(desNode.getParentnode()!=null){
 	  newRoute.getNodelist().add(0, currentNode);
 	  newRoute.getObjetc().add(0, currentNode);
 	  
 	  while(currentNode!=srcNode){
 		  Link link=layer.findlink(currentNode,currentNode.getParentnode());
 		  newRoute.getObjetc().add(0, link);
 		  newRoute.getLinklist().add(0, link);
 		  
 		  currentNode=currentNode.getParentnode();
 		  newRoute.getNodelist().add(0, currentNode);
     	  newRoute.getObjetc().add(0, currentNode);
 	  }
   }
}

}
