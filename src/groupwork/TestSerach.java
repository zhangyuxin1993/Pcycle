package groupwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import General.Constant;
import Network.*;
import Subgraph.*;

public class TestSerach {
		public void Dijkstras(Node srcNode,Node desNode,Layer layer,LinearRoute newRoute){
			  
			    ArrayList<Node>visitedNodeList=new ArrayList<Node>();	
			    //file_out_put file=new file_out_put();
	            visitedNodeList.clear();
	            //��ʼ��
	            HashMap<String,Node>map=layer.getNodelist();
	            Iterator<String>iter=map.keySet().iterator();
	            while(iter.hasNext()){
	         	   Node node=(Node)(map.get(iter.next()));
	         	   node.setStatus(Constant.UNVISITED);
	         	   node.setParentnode(null);
	         	   node.setCost_from_src(Constant.maxium);//���������
	         	   node.setHop_from_src(Constant.maxium);
	            }
	            //�����ڽڵ�
	            Node currentNode=srcNode;//��ǰ�ڵ�
	            currentNode.setCost_from_src(0);
	            currentNode.setHop_from_src(0);
	            currentNode.setStatus(Constant.VISITED);
	            //System.out.println("�ڵ�·��  "+ currentNode.getName());
	            //file.filewrite("e:/t2.txt" , "�ڵ�·��  "+ currentNode.getName());
	            for(Node node:currentNode.getNeinodelist())//�����ڽڵ� 
	            {
	         	           if(node.getStatus()==Constant.UNVISITED){
	         			   Link link=layer.findlink(currentNode, node);
	         			   node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
	         			   node.setHop_from_src(currentNode.getHop_from_src()+1);
	         			   node.setParentnode(currentNode);
	         		   }
	         	   }
	            currentNode=this.getLowestCostNode(layer);//�ҵ���̵Ľڵ�
	            //System.out.println("�ڵ�·��  "+ currentNode.getName());
	            //file.filewrite("e:/t2.txt" , "�ڵ�·���� "+ currentNode.getName());
	            if(currentNode!=null)//�ҵ������·���ڵ㲻Ϊ��
	            {
	         	   while(!currentNode.equals(desNode)){
	         	       for(Node node:currentNode.getNeinodelist())
	         	       //����
	         	    	   {
	         	    		 if(node.getStatus()==Constant.UNVISITED){
	         	    			   Link link=layer.findlink(currentNode, node);
	         	                   if(node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()){
	         	    			          node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
	         	    			          node.setHop_from_src(currentNode.getHop_from_src()+1);
	         	    			          System.out.println(node.getCost_from_src());
	         	    			          node.setParentnode(currentNode);
	                 			         // System.out.println("�ڵ�·��  "+ currentNode.getName());
	                 			         //file.filewrite("e:/t2.txt" , "�ڵ�·��  "+ currentNode.getName());
	         	                 }
	         	    		   }
	         	       }
	         	        currentNode =this.getLowestCostNode(layer);
	         	        //System.out.println("�ڵ�·��  "+ currentNode.getName());
	         	       //file.filewrite("e:/t2.txt" , "�ڵ�·��  "+ currentNode.getName());
	         	        if(currentNode==null)break;
	         	   }
	            }
	       newRoute.getNodelist().clear();
	       newRoute.getLinklist().clear();
	       currentNode=desNode;
	       if(desNode.getParentnode()!=null){
	     	  newRoute.getNodelist().add(0, currentNode);
	     	  while(currentNode!=srcNode){
	     		  Link link=layer.findlink(currentNode,currentNode.getParentnode());
	     		  newRoute.getLinklist().add(0, link);
	     		  currentNode=currentNode.getParentnode();
	     		  newRoute.getNodelist().add(0, currentNode);
	     	  }
	       }
		}
		public Node getLowestCostNode(Layer layer){
			
			Node currentnode = null;
			double current_cost_to_desc =Constant.maxium;
		    HashMap<String,Node>map=layer.getNodelist();
            Iterator<String>iter=map.keySet().iterator();
            while(iter.hasNext()){
         	   Node node=(Node)(map.get(iter.next()));
                if(node.getStatus()==Constant.UNVISITED){
				      if(node.getCost_from_src() < current_cost_to_desc){
					             currentnode = node;
					             current_cost_to_desc = node.getCost_from_src();
					            // System.out.println(node.getName()+"-"+node.getCost_from_src());
					             
				}			
			}
                /*else{
                	 System.out.println(node.getName()+"-"+node.getCost_from_src());
                }
	      */}
            currentnode.setStatus(Constant.VISITED);
			return currentnode;		
		}
  public void Dijkstras(String name1,String name2,Layer layer,LinearRoute newRoute){
	        Node srcNode=layer.findNode(name1, layer);
	        Node desNode=layer.findNode(name2, layer);
	        //file_out_put file=new file_out_put();
            ArrayList<Node>visitedNodeList=new ArrayList<Node>();	
            visitedNodeList.clear();
            
            //��ʼ��
            HashMap<String,Node>map=layer.getNodelist();
            Iterator<String>iter=map.keySet().iterator();
            while(iter.hasNext()){
         	   Node node=(Node)(map.get(iter.next()));
         	   node.setStatus(Constant.UNVISITED);
         	   node.setParentnode(null);
         	   node.setCost_from_src(Constant.maxium);//���������
         	   node.setHop_from_src(Constant.maxium);
            }
            //�����ڽڵ�
            Node currentNode=srcNode;//��ǰ�ڵ�
            currentNode.setCost_from_src(0);
            currentNode.setHop_from_src(0);
            currentNode.setStatus(Constant.VISITED);
            //System.out.println("�ڵ�·��  "+ currentNode.getName());
            //file.filewrite("e:/t2.txt" , "�ڵ�·��  "+ currentNode.getName());
            for(Node node:currentNode.getNeinodelist())//�����ڽڵ� 
            {
         	           if(node.getStatus()==Constant.UNVISITED){
         			   Link link=layer.findlink(currentNode, node);
         			   node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
         			   node.setHop_from_src(currentNode.getHop_from_src()+1);
         			   node.setParentnode(currentNode);
         		   }
         	   }
            if(currentNode.getNeinodelist().size()!=0){
                 currentNode=this.getLowestCostNode(layer);//�ҵ���̵Ľڵ�
                 //System.out.println("�ڵ�·�� "+ currentNode.getName());
                 //file.filewrite("e:/t2.txt" , "�ڵ�·��  "+ currentNode.getName());
                 if(currentNode!=null)//�ҵ������·���ڵ㲻Ϊ��
                {
         	          while(!currentNode.equals(desNode)){
         		          if(currentNode.getNeinodelist().size()==0){
         			              currentNode=null;
         			              break;
         		            }
         		          else{
         	                     for(Node node:currentNode.getNeinodelist())
         	       //��         	     
         	                    	 {
         	    		              if(node.getStatus()==Constant.UNVISITED){
         	    			                 Link link=layer.findlink(currentNode, node);
         	                                 if(node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()){
         	    			                 node.setCost_from_src(currentNode.getCost_from_src()+link.getCost());
         	    			                 node.setHop_from_src(currentNode.getHop_from_src()+1);
         	    			                 //System.out.println(node.getCost_from_src());
         	    			                //file.filewrite("e:/t2.txt" ,node.getCost_from_src() );
         	    			                 node.setParentnode(currentNode);
         	                                 }
         	    		               }
         	                    	 }
         		          
         	                          currentNode =this.getLowestCostNode(layer);
         	                          //System.out.println("�ڵ�·��  "+ currentNode.getName());
         	                         // file.filewrite("e:/t2.txt","�ڵ�·��  "+ currentNode.getName());
         	                          if(currentNode==null)break;
         		   }
         	   }
            }
            }//�������·��
       newRoute.getNodelist().clear();
       newRoute.getLinklist().clear();
       currentNode=desNode;
       if(desNode.getParentnode()!=null){
     	  newRoute.getNodelist().add(0, currentNode);
     	  while(currentNode!=srcNode){
     		  Link link=layer.findlink(currentNode.getParentnode(),currentNode);
     		  newRoute.getLinklist().add(0, link);
     		  currentNode=currentNode.getParentnode();
     		  newRoute.getNodelist().add(0, currentNode);
     	  }
       }
	}
  

}
