package Main;

import java.util.ArrayList;
import java.util.HashMap;

import General.file_out_put;
import Network.Layer;
import Network.Nodepair;
import Subgraph.LinearRoute;

public class WorkCross {
	Layer mylayer=Main.mylayer;
	HashMap<String, LinearRoute> nodepair_workroute=Main.nodepair_workroute;
	HashMap<String, String> nodepair_serialNumber=Main.nodepair_serialNumber;
	ArrayList<Nodepair> Nodepairlist=Main.Nodepairlist;
	
	static HashMap<String, Integer> workcrossResult=new  HashMap<String, Integer>();
	public  void workcross(){
		
		file_out_put filewrite=new file_out_put();
		
		for(int n=0;n<Nodepairlist.size();n++)
		{
			for(int m=0;m<Nodepairlist.size();m++)
			{
				if(m==n) continue;
				String nodepair1Name=Nodepairlist.get(n).getName();
				String nodepair2Name=Nodepairlist.get(m).getName();
				
//				String nodepair1Name=nodepair_serialNumber.get(n+"0");
//				String nodepair2Name=nodepair_serialNumber.get(m+"0");
//				System.out.println("*********"+nodepair1Name+"     "+nodepair2Name);
				LinearRoute route1=nodepair_workroute.get(nodepair1Name);
				LinearRoute route2=nodepair_workroute.get(nodepair2Name);
//				System.out.println("route1 = ");
//				route1.OutputRoute_node(route1);
//				System.out.println("route2 = ");
//				route1.OutputRoute_node(route2);
				NodelistCompare nc=new NodelistCompare();
				int a=nc.nodelistcompare(route1.getNodelist(), route2.getNodelist());
				workcrossResult.put(nodepair1Name+nodepair2Name, a);
				
				
//				filewrite.filewrite("F:\\programFile\\5nodeWorkCross.dat",nodepair1Name+"     ");
//				filewrite.filewrite("F:\\programFile\\5nodeWorkCross.dat",nodepair2Name+"     ");
//				filewrite.filewrite("F:\\programFile\\5nodeWorkCross.dat",a*10/10);
//				filewrite.filewrite("F:\\programFile\\5nodeWorkCross.dat", "\r\n");
//				System.out.println(nodepair1Name+"     "+nodepair2Name+"     "+a);
			}
			
		}
//		System.out.println("workcrossResult:  "+workcrossResult.size());


	}



}
