package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import General.file_out_put;
import Network.Layer;
import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;
import Subgraph.LinearRoute;
import groupwork.RouteSearching;
import groupwork.SearchConstraint;

public class WorkFirstCross {
	HashMap<String, LinearRoute> nodepair_workroute=Main.nodepair_workroute;
	HashMap<String, String> nodepair_serialNumber=Main.nodepair_serialNumber;
	static HashMap<String, Integer> WorkFirstCrossResult=new HashMap<String, Integer>() ;
	static HashMap<String, Integer> WorkSecondCrossResult=new HashMap<String, Integer>() ;
		public  void workfirstcross(Nodepair nownodepair,ArrayList<Node> nodelist,Cycle inputcycle,int m,ArrayList<Node> nodelist2)
	{
	

		int a=0;
		Iterator<String> iter = nodepair_serialNumber.keySet().iterator();//遍历节点对
			while (iter.hasNext()) 
			{
				String comnodepair=(String) (nodepair_serialNumber.get(iter.next()));
				if(comnodepair.equals(nownodepair.getName())) continue;	
			
				else
				{
					NodelistCompare NC=new NodelistCompare();
					a=NC.nodelistcompare(nodepair_workroute.get(comnodepair).getNodelist(),nodelist);
				//nodelist是保护  get的是工作
				}
				
//				System.out.print(comnodepair+"   "+nownodepair.getName()+"   ");
				CycleOutput out=new CycleOutput();
//				if (m==1)  filename="F:\\programFile\\5node\\WorkFirstCross.dat";
//				if(m==2)   filename="F:\\programFile\\5node\\WorkSecondCross.dat";

//				filewrite.filewrite(filename, comnodepair+"   "+nownodepair.getName()+"   ");
//				out.cycleoutput(inputcycle, filename);
//				filewrite.filewrite(filename,"   ");
//				  if(m==2){//判断第二保护时 先输出第一保护作为一个参数
//					
//					  for(int i=0;i<nodelist2.size()-1;i++)
//				      {
//						filewrite.filewrite(filename, nodelist2.get(i).getName()+"-");
//				      }
//					  filewrite.filewrite(filename, nodelist2.get(nodelist2.size()-1).getName()+"  ");
//				  }
				  
//				  for(int i=0;i<nodelist.size()-1;i++)
//			      {
//					filewrite.filewrite(filename, nodelist.get(i).getName()+"-");
//			      }
//				  filewrite.filewrite(filename, nodelist.get(nodelist.size()-1).getName()+"  ");
				
//				  filewrite.filewrite(filename, a);
//				  filewrite.filewrite(filename, "\r\n");
				  
				 if(m==1){
					 WorkFirstCrossResult.put(comnodepair+nownodepair.getName()+inputcycle.toString()+nodelist.toString(), a);
				 }
				 if(m==2){
					 WorkSecondCrossResult.put(comnodepair+nownodepair.getName()+inputcycle.toString()+nodelist2.toString()+nodelist.toString(), a);
				 }
				a=0;
			}
			  
	}

}
