package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Network.Layer;
import Network.Node;
import Network.Nodepair;
import Subgraph.LinearRoute;
import groupwork.RouteSearching;
import groupwork.SearchConstraint;

public class NodelistCompare {
	//看两个路径有没有相交
	public  int nodelistcompare(ArrayList<Node> nodelist1,ArrayList<Node> nodelist2)
	{

		int D_value=0;
		for(int n=0;n<=nodelist2.size()-2;n++)
		{
			if(nodelist1.contains(nodelist2.get(n))&&nodelist1.contains(nodelist2.get(n+1)))
			{
				D_value=Math.abs(nodelist1.indexOf(nodelist2.get(n))-nodelist1.indexOf(nodelist2.get(n+1)));
				if(D_value==1)
				{
					break;
				}
				else
				{
					D_value=0;
				}
			}
		}
		return D_value;
		
	
	}

	public static void main(String[] args) {
		
		Layer mylayer=new Layer(null, 0, null);
		String filename="F:/Topology/6.csv";
		mylayer.readTopology(filename);
		mylayer.generateNodepairs();
		SearchConstraint constraint = new SearchConstraint(100000);
		HashMap<String, String> nodepair_serialNumber=new HashMap<String, String>();
		HashMap<String, LinearRoute> nodepair_workroute=new HashMap<String, LinearRoute>();
		
		//将节点对与工作路径储存
		int SerialNum=0;
		
		HashMap<String, Nodepair> map2 = mylayer.getNodepairlist();
		Iterator<String> iter2 = map2.keySet().iterator();
		while (iter2.hasNext()) {
			Nodepair nowNodePair = (Nodepair) (map2.get(iter2.next()));
			nodepair_serialNumber.put(SerialNum+"0",nowNodePair.getName());
			
//			System.out.println("节点对编号：  "+SerialNum+"   "+nodepair_serialNumber.get(SerialNum+"0"));
			SerialNum++;
			Node srcNode=nowNodePair.getSrcNode();
			Node desNode=nowNodePair.getDesNode();
			RouteSearching dijkstra=new RouteSearching();
			LinearRoute newRoute=new LinearRoute(null, 0, null, 0);
			dijkstra.Dijkstras(srcNode, desNode, mylayer, newRoute, constraint);
		  
//			System.out.println("路径：");
//			newRoute.OutputRoute_node(newRoute);			
			nodepair_workroute.put(nowNodePair.getName(), newRoute);

		}
	
		for(int n=0;n<=mylayer.getNodepairlist().size()-2;n++)
		{
			String nodepair1Name=nodepair_serialNumber.get(n+"0");
			String nodepair2Name=nodepair_serialNumber.get(n+1+"0");
//			System.out.println("*********"+nodepair1Name+"     "+nodepair2Name);
			LinearRoute route1=nodepair_workroute.get(nodepair1Name);
			LinearRoute route2=nodepair_workroute.get(nodepair2Name);
			route1.OutputRoute_node(route1);
			route1.OutputRoute_node(route2);
			NodelistCompare nc=new NodelistCompare();
			int a=nc.nodelistcompare(route1.getNodelist(), route2.getNodelist());
			
//			System.out.println(nodepair1Name+"     "+nodepair2Name+"     "+a);
		}
		
	}

}
