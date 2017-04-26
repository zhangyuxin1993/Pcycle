package ReadFile;

import groupwork.SearchConstraint;
import groupwork.TestSerach;
import groupwork.pathSearch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import Network.*;
import Subgraph.LinearRoute;
import General.*;

public class ReadFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Layer layer = new Layer("worklayer", 0, "");
		file_out_put file = new file_out_put();

		Scanner scannerobject = new Scanner(System.in);

		// 输入文件名
		//System.out.println("*************输入写入文件的名字*************");
		//String write_name = scannerobject.next();

		System.out.println("*************输入打开文件的名字*************");
		String open_name = scannerobject.next();
		layer.readTopology(open_name);
		System.out.println("=============the relationship================");
		//file.filewrite(write_name,"=============the relationship================");		
		
        layer.generateNodepairs();
		HashMap<String, Nodepair> map1 = layer.getNodepairlist();
		Iterator<String> iter1 = map1.keySet().iterator();
		while (iter1.hasNext()) {
			Nodepair nodepair=(Nodepair) (map1.get(iter1.next()));
			Node srcNode=nodepair.getSrcNode();
			Node desNode=nodepair.getDesNode();
			LinearRoute newRoute=new LinearRoute("", 0, "", 0);
			//SearchConstraint constraint=null;
			TestSerach ShortestPath = new TestSerach ();
			ShortestPath.Dijkstras(srcNode.getName(), desNode.getName(), layer,newRoute);
			System.out.println("the shortest path of "+nodepair.getSrcNode().getName()+"-"+nodepair.getDesNode().getName());
			System.out.println(newRoute.getNodelist().size());
			//for(Link link : newRoute.getLinklist()){
				//System.out.println(link.getNodeA().getName()+ "-" + link.getNodeB().getName());			
				//}
			newRoute.OutputRoute_link(newRoute);
		}

		// layer初始化
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			System.out.println("the neinode of " + node.getName());
			//file.filewrite(write_name, "the neinode of " + node.getName());
			for (int j = 0; j < node.getNeinodelist().size(); j++) {
				System.out.println(node.getNeinodelist().get(j).getName());
				//file.filewrite(write_name, node.getNeinodelist().get(j).getName());
			}
		}

	}

}
