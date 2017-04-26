package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import General.file_out_put;
import Network.Layer;
import Network.Link;
import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;
import Subgraph.LinearRoute;
import groupwork.RouteSearching;
import groupwork.SearchConstraint;

public class NewNodepair {

	public  void newnodepair(Nodepair nodepair,ArrayList<Cycle> cyclelist,Layer layer,HashMap<String, LinearRoute> nodepair_workroute)
	{
//		System.out.println("ִ�У�");
		int have=0;
		LinearRoute newRoute=new LinearRoute(null, 0, null, 0);
//		System.out.println(cyclelist.size());
		file_out_put filewrite=new file_out_put();
		int first=0,second=0,firstcount=0,secondcount=0;
		Node srcnode=nodepair.getSrcNode();
		Node desnode=nodepair.getDesNode();
		int display=0;
		
		for(int c=0;c<cyclelist.size();c++){
			first=0;second=0;firstcount=0;secondcount=0;
			Cycle cycle=cyclelist.get(c);

//			/*
			CycleOutput out =new CycleOutput();
//			out.cycleoutput(cycle, "F:\\programFile\\5node\\Cycle.dat");
			if(cycle.getNodelist().contains(srcnode)&&cycle.getNodelist().contains(desnode)){
				for(int n=0;n<cycle.getNodelist().size();n++){
//					System.out.println(cycle.getNodelist().get(n).getName());
				if((srcnode.equals(cycle.getNodelist().get(0))&&desnode.equals(cycle.getNodelist().get(1)))||(srcnode.equals(cycle.getNodelist().get(1))&&desnode.equals(cycle.getNodelist().get(0)))  ){
						break;
					}
//				System.out.println("����ڵ������  "+nodepair.getName());
//				System.out.println(cycle.getNodelist().get(cycle.getNodelist().size()-1).getName());
//				System.out.println(cycle.getNodelist().get(cycle.getNodelist().size()-2).getName());
				if((srcnode.equals(cycle.getNodelist().get(cycle.getNodelist().size()-1))&&desnode.equals(cycle.getNodelist().get(cycle.getNodelist().size()-2)))||(srcnode.equals(cycle.getNodelist().get(cycle.getNodelist().size()-2))&&desnode.equals(cycle.getNodelist().get(cycle.getNodelist().size()-1)))){
					break;
				}
				if(srcnode.equals(cycle.getNodelist().get(n))){
					firstcount=1;
					 first=n;
				}
				if(desnode.equals(cycle.getNodelist().get(n))){
					secondcount=1;
					 second=n;
				}
				if(firstcount==1&&secondcount==1&&Math.abs(first-second)!=1){
					//Ҫ�ж��ܹ��������Ļ��Ƿ��빤��·���ཻ
					//*************
					int noprotect=0;
					for(int m=0;m<cycle.getNodelist().size()-1;m++)//cycle�ϵ���·����
					{
						Node NodeA=new Node(cycle.getNodelist().get(m).getName(), 0, null, layer, 0, 0);
						Node NodeB=new Node(cycle.getNodelist().get(m+1).getName(), 0, null, layer, 0, 0);
						Link link1=layer.findlink(NodeA, NodeB);//link1�ǻ��ϵ���·
//						 System.out.println(link1.getName());//TEST
						ArrayList<Link>linklist=nodepair_workroute.get(nodepair.getName()).getLinklist();
						for(Link nowlink:linklist)//linklist�����·���ϵ�linklist
						 {
//						    System.out.println(nowlink.getName());//test
					if(((nowlink.getNodeA().getName().equals(link1.getNodeA().getName()))&&(nowlink.getNodeB().getName().equals(link1.getNodeB().getName())))||((nowlink.getNodeA().getName().equals(link1.getNodeB().getName()))&&(nowlink.getNodeB().getName().equals(link1.getNodeA().getName()))))
						  		{ 
									noprotect=1;
						  			break;
						  		}	
						
					}
						  	if(noprotect==1) break;
					}
					
					//*****************
					if(noprotect==0&&have==0){
						 display=1;
//						 System.out.println("attention!!!!");
						filewrite.filewrite("F:\\programFile\\5node\\ProtectCycle.dat", "set ProtectCycle["+nodepair.getName()+"] :=");
						filewrite.filewrite("F:\\programFile\\5node\\ProtectCycle.dat", "\r\n");	
						filewrite.filewrite("F:\\programFile\\5node\\nodepair.dat", nodepair.getName());	
						filewrite.filewrite("F:\\programFile\\5node\\trafficDemand.dat", nodepair.getName());	
						
						filewrite.filewrite("F:\\programFile\\5node\\nodepair.dat", "\r\n");	
						
						
					}
					if(noprotect==0)//��û�г�ͻ������ڵ�� �� ������·��
					{
						have=1;
						out.cycleoutput(cycle, "F:\\programFile\\5node\\ProtectCycle.dat");
						filewrite.filewrite("F:\\programFile\\5node\\ProtectCycle.dat", "\r\n");	

//					System.out.println("����ڵ������  "+nodepair.getName());
//					System.out.println("λ�ã�"+first+"   "+second);
					break;
					}
				}
			}			
			}
//			*/
		}
		if(display==1){
			display=0;
			filewrite.filewrite("F:\\programFile\\5node\\ProtectCycle.dat", ";");	
		    filewrite.filewrite("F:\\programFile\\5node\\ProtectCycle.dat", "\r\n");	
		}
	}
}
