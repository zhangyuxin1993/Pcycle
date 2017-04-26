package Subgraph;

import java.util.ArrayList;

import General.Constant;
import General.file_out_put;
//import General.file_out_put;
import Network.Layer;
import Network.Link;
import Network.Node;


public class LinearRoute extends Subgraph {
    private int type=Constant.WORKING;
    private int routingtime=1;
    private int Occpy_W=Constant.maxium;
    private double LightPathProbability=0;
    private int signalStrength=0;
    
    
    
    
    
	
	public int getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}
	public double getLightPathProbability() {
		return LightPathProbability;
	}
	public void setLightPathProbability(double lightPathProbability) {
		LightPathProbability = lightPathProbability;
	}
	public int getOccpy_W() {
		return Occpy_W;
	}
	public void setOccpy_W(int occpy_W) {
		Occpy_W = occpy_W;
	}
	public int getRoutingtime() {
		return routingtime;
	}
	public void setRoutingtime(int routingtime) {
		this.routingtime = routingtime;
	}
	/*public ArrayList<Node> getNode_of_route() {
		return Node_of_route;
	}
	public void setNode_of_route(ArrayList<Node> node_of_route) {
		Node_of_route = node_of_route;
	}
	public ArrayList<String> getRoute() {
		return route;
	}
	public void setRoute(ArrayList<String> route) {
		this.route = route;
	}

	*/
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public LinearRoute(String name, int index, String comments,int type) {
		super(name, index, comments);
		// TODO Auto-generated constructor stub
	    this.type=type;
	    
	}
	public int Equal_link(LinearRoute newroute,Link link){
		     ArrayList<Link>linklist=newroute.getLinklist();
		     Link currentlink=null;
		     for(Link link1:linklist){
		    	 if(link.getName().endsWith(link1.getName()))return 1;
		     }
		     return 0;
	}
	public void OutputRoute_link(LinearRoute newroute){
		     ArrayList<Link>linklist=newroute.getLinklist();
		     Link currentlink=null;
		     for(Link link:linklist){
		    	
		    	 System.out.println(link.getNodeA().getName()+"------"+link.getNodeB().getName()+link.getCost());
		    	 
		     }
	}
	public void OutputRoute_node(LinearRoute newroute,String write_name){
		file_out_put file=new file_out_put();
		if(newroute.getNodelist().size()==0){
			System.out.println("no path to the desnode");
			file.filewrite(write_name,"no path to the desnode");
		}
		else{
			  Node currentnode=null;
		      for(Node node:newroute.getNodelist()){
		    	       currentnode=node;
			           System.out.println(node.getName());
			           file.filewrite(write_name,node.getName()+"  ");
			           
			           
		      }
		     
		}
		
	}
	public void OutputRoute_node(LinearRoute newroute){
		if(newroute.getNodelist().size()==0){
			System.out.println("no path to the desnode");
		}
		else{
			  Node currentnode=null;
			  int i=0;
		      for(Node node:newroute.getNodelist()){
		    	    currentnode=node;  
		    	       if(i==0)
		    	    	   System.out.print(node.getName());   
		    	       
		    	       else
		    	       System.out.print("->"+node.getName());
		    	       i++;
		      }
		      System.out.println();
//			  Node currentnode=null;
//		      for(Node node:newroute.getNodelist()){
//		    	       currentnode=node;  
//		    	       System.out.println(node.getName());
//		      }
		      //this.src_to_des_cost=currentnode.getCost_from_src();
		}
		
	}
	public void Plus_cost(LinearRoute route,Layer layer,double math){
		Node currentnode=route.getNodelist().get(0);
		for(Node node:route.getNodelist()){
			if(!node.getName().equals(currentnode.getName())){
			   double cost=layer.findlink(currentnode, node).getCost();
			  // System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
			  // System.out.println(layer.findlink(node, currentnode).getName()+"-"+layer.findlink(node, currentnode).getCost());
			  // System.out.println("============加cost=============");
			   layer.findlink(currentnode, node).setCost(cost+math);
			   layer.findlink(node, currentnode).setCost(cost+math);
			  // System.out.println(math);
			  System.out.println(layer.findlink(node, currentnode).getName()+"-"+layer.findlink(node, currentnode).getCost());
			  // System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
			   //System.out.println("===============================");
			   currentnode=node;
			   
		}
		}
	}
		public void Plus_cost(LinearRoute route,Layer layer){
			Node currentnode=route.getNodelist().get(0);
			for(Node node:route.getNodelist()){
				if(!node.getName().equals(currentnode.getName())){
				   double cost=layer.findlink(currentnode, node).getCost();
				   double newcost=cost+layer.findlink(currentnode, node).getInitialcost();
				   layer.findlink(currentnode, node).setCost(newcost);
				   layer.findlink(node, currentnode).setCost(newcost);
				  // System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
				    
				   currentnode=node;
			}
			}
	}
        public void Weight_assagin(LinearRoute route,Layer layer){
        	Node currentnode=route.getNodelist().get(0);
			for(Node node:route.getNodelist()){
				if(!node.getName().equals(currentnode.getName())){
				   int w=layer.findlink(currentnode, node).getW();
				   if(w>0){
				   layer.findlink(currentnode, node).setW(w-1);
				   layer.findlink(node, currentnode).setW(w-1);
				   //System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getW());
				   currentnode=node;
				   }
				}
			}
        }
        
        public void Weight_release(LinearRoute route,Layer layer){
        	Node currentnode=route.getNodelist().get(0);
			for(Node node:route.getNodelist()){
				if(!node.getName().equals(currentnode.getName())){
				   int w=layer.findlink(currentnode, node).getW();				  
				   layer.findlink(currentnode, node).setW(w+1);
				   layer.findlink(node, currentnode).setW(w+1);
				  // System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getW());
				   currentnode=node;
			}
			}
        }
        
        public int Weight_Assagin_WC(Layer layer,int number){
        	//Node currentnode=route.getNodelist().get(0);
        	//找到共同空闲的波长索引
        	int index=this.Free_All_Index(layer, number);
        	//System.out.println("分配的波长"+index);
        	Node currentnode=this.getNodelist().get(0);
		    for(Node node:this.getNodelist()){
		    	if(!node.getName().equals(currentnode.getName())){
		    		//关闭链路上的波长
		    		
		    		layer.findlink(currentnode, node).getWC().get(index).setStatus(1);
					layer.findlink(node, currentnode).getWC().get(index).setStatus(1);
					currentnode=node;
		    	}
		    }
		    //System.out.println("===========================");
		    return index;
        }
        public void Weight_Release_WC(Layer layer,int index){
        	Node currentnode=this.getNodelist().get(0);
			for(Node node:this.getNodelist()){
				if(!node.getName().equals(currentnode.getName())){
				   				  
					layer.findlink(currentnode, node).getWC().get(index).setStatus(0);
					layer.findlink(node, currentnode).getWC().get(index).setStatus(0);
				  // System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getW());
				   currentnode=node;
				}
			}
        }
        public int Free_All_Index(Layer layer,int number){
			
        	int i=0;
			while(i<number){
				int key=0;
				Node currentnode=this.getNodelist().get(0);//找到第一个节点
				for(Node node:this.getNodelist()){
					 if(!node.getName().equals(currentnode.getName())){
						 //如果没有占用
						 if(layer.findlink(currentnode, node).getWC().get(i).getStatus()==0){
                  		   currentnode=node;					                    		   
                  	   }
						 //占用
						 else{
	                    		   currentnode=node;	
	                    		   key=1;
	                    	   }
						 
						 }
					 }
				 //该索引下有被占用
				 if(key==1){
            		 i++;
            	 }
				//该索引下没被占用
				 else{
					
					 break;
				}
			}
			System.out.println("分配的波长"+i);
        	return i;
        	
        }
		public void Plus_cost(LinearRoute route,Layer layer,String write_name){
			Node currentnode=route.getNodelist().get(0);
			file_out_put file=new file_out_put();
			for(Node node:route.getNodelist()){
				if(!node.getName().equals(currentnode.getName())){
				   double cost=layer.findlink(currentnode, node).getCost();
				   double newcost=cost+layer.findlink(currentnode, node).getInitialcost();
				   layer.findlink(currentnode, node).setCost(newcost);
				   layer.findlink(node, currentnode).setCost(newcost);
				 //  System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
				   file.filewrite(write_name, layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
				   currentnode=node;
			}
			}
	}
	public void mauns_cost(LinearRoute route,Layer layer,double math){
		Node currentnode=route.getNodelist().get(0);
		for(Node node:route.getNodelist()){
			if(!node.getName().equals(currentnode.getName())){
			layer.findlink(currentnode, node).setCost(layer.findlink(currentnode, node).getCost()-math);
			layer.findlink(node, currentnode).setCost(layer.findlink(node, currentnode).getCost()-math);
			System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
			//System.out.println(layer.findlink(node, currentnode).getName()+"-"+layer.findlink(node, currentnode).getCost());
			//System.out.println("================================");
			currentnode=node;
		}
	}
	}
	public void mauns_cost(LinearRoute route,Layer layer){
		Node currentnode=route.getNodelist().get(0);
		for(Node node:route.getNodelist()){
			if(!node.getName().equals(currentnode.getName())){
			double cost1=layer.findlink(currentnode, node).getCost();
			double cost2=layer.findlink(node, currentnode).getCost();
			double newcost1=cost1-layer.findlink(currentnode, node).getInitialcost();
			double newcost2=cost2-layer.findlink(node, currentnode).getInitialcost();
			layer.findlink(currentnode, node).setCost(newcost1);
			layer.findlink(node, currentnode).setCost(newcost2);
			System.out.println(layer.findlink(currentnode, node).getName()+"-"+layer.findlink(currentnode, node).getCost());
			currentnode=node;
		}
		}
	}
	public int equal(LinearRoute route){
		for(int i=0;i<this.getNodelist().size();i++){
			  if(!this.getNodelist().get(i).getName().equals(route.getNodelist().get(i).getName()))return 1;
				
		}
	       return 0;
	}
	public void path_allcost(LinearRoute route){
		 
                 double src_des=0;
	        	 for(Link link:route.getLinklist()){
	    	     src_des+=link.getCost();
	        	 }
	        	 System.out.println(src_des);
	}
	
	/**
	 * 计算route的signalStrength
	 */
	 public void setSignalStrength(){
     	
		int signalStrength=this.getSignalStrength();
     	Node currentnode=this.getNodelist().get(0);
		    for(Node node:this.getNodelist()){
		    	if(!node.getName().equals(currentnode.getName())){
		    		//判断是否是中继池节点
		    		//Status==0 非中继池节点 signalstrength-1
		    		if(node.getRegenStatus()==0){
		    			signalStrength--;
		    		}
		    		//Status==1 中继池节点 signalstrength恢复
		    		else{
		    			signalStrength=this.getSignalStrength();
		    		}
		    	}
		    }
		    
		    this.setSignalStrength(signalStrength);
		   
     }
}
