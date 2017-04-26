package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import General.file_out_put;
import General.random;
import Main.AEofCycle;
//import Main.CycleAndNodepair;
//import Main.CycleOutput;
//import Main.WorkCross;
import Network.Layer;
import Network.Link;
import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;
import Subgraph.LinearRoute;
import groupwork.RouteSearching;
import groupwork.SearchConstraint;

public class Main {

	static Layer mylayer = new Layer(null, 0, null);
	static HashMap<String, String> nodepair_serialNumber = new HashMap<String, String>();
	static HashMap<String, LinearRoute> nodepair_workroute = new HashMap<String, LinearRoute>();
	static HashMap<String, Integer> FFprotectCross = new HashMap<String, Integer>();
	static HashMap<String, Integer> nodepair_intNumber = new HashMap<String, Integer>();
	static HashMap<String, Integer> nodepair_demand = new HashMap<String, Integer>();
	static ArrayList<Nodepair> Nodepairlist = new ArrayList<Nodepair>();

	public static void main(String[] args) {

		file_out_put filewrite = new file_out_put();
		HashMap<String, Double> AeList = new HashMap<String, Double>();
		AeList = AEofCycle.AeList;
		String filename = "G:/Topology/5.csv";
		// String filename="E:/ZYX/Topology/5.csv";
		mylayer.readTopology(filename);
		mylayer.generateNodepairs();
		ArrayList<Cycle> cyclelist = new ArrayList<Cycle>();
		ArrayList<Cycle> newcyclelist = new ArrayList<Cycle>();
		RouteSearching shortestpath = new RouteSearching();
		SearchConstraint constraint = new SearchConstraint(100000);

		// 找出所有的环
		// /*
		System.out.println("set C :=");
		HashMap<String, Node> map = mylayer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			shortestpath.findAllCycle(node, mylayer, constraint, cyclelist);

		}
		CycleOutput out = new CycleOutput();
		out.cycleoutput(cyclelist, "F:\\programFile\\5node\\Cycle.dat");

		// 环是否需要减少
		AEofCycle AE = new AEofCycle();
		AE.aeofcycle(cyclelist, mylayer);
		AeList = AEofCycle.AeList;
		for (int b = 0; b < cyclelist.size(); b++) {
			Cycle nowcycle = cyclelist.get(b);
			// out.cycleoutput(nowcycle,"F:\\programFile\\5node\\test.dat");
			double nowae = AeList.get(nowcycle.toString());
			// if(nowae>average){
			// System.out.print(average);
			if (nowae > 15) {
				newcyclelist.add(nowcycle);
			}
		}
		out.cycleoutput(newcyclelist, "F:\\programFile\\5node\\newCycle.dat");

		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "set  L :=");
		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "\r\n");
		// /* linkoncycle
		HashMap<String, Link> linkmap2 = mylayer.getLinklist();
		Iterator<String> linkiter2 = linkmap2.keySet().iterator();
		while (linkiter2.hasNext()) {
			Link link = (Link) (linkmap2.get(linkiter2.next()));
			if (link.getNodeA().getIndex() < link.getNodeB().getIndex()) {
				filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", link.getName());
				filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "\r\n");
			}

		}
		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "\r\n");

		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "param  LinkOnCycle :=");
		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "\r\n");
		// 注意：如果前面执行了减少环 则在底下的步骤需要将cyclelist变成newcyclelist
		// for(Cycle cycle:newcyclelist){
		for (Cycle cycle : cyclelist) {
			int oncycle = 0;
			HashMap<String, Link> linkmap = mylayer.getLinklist();
			Iterator<String> linkiter = linkmap.keySet().iterator();
			while (linkiter.hasNext()) {
				Link link = (Link) (linkmap.get(linkiter.next()));
				if (link.getNodeA().getIndex() < link.getNodeB().getIndex()) {
					Link newlink = mylayer.findlink(link.getNodeB(), link.getNodeA());
					if (cycle.getLinklist().contains(link)) {
						oncycle = 1;
					}
					if (cycle.getLinklist().contains(newlink)) {
						oncycle = 1;
					}
					filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", link.getName() + "    ");
					out.cycleoutput(cycle, "F:\\programFile\\5node\\OnCycle.dat");
					filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", "    ");
					// System.out.println(link.getName());
					filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", oncycle);
					// filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat",
					// "\r\n");
					oncycle = 0;
				}
			}
		}

		filewrite.filewrite("F:\\programFile\\5node\\OnCycle.dat", ";");
		// /*

		// 找出工作路由 并且为节点对随机分配工作容量
		int SerialNum = 0;
		int[] demand;
		int[] nodepair_num;
		int n, p = 0;

		random R = new random();
		demand = R.Num_random(20, 300);
		for (int add = 0; add < demand.length; add++) {
			demand[add] = demand[add] + 100;
		}
		// 随机产生5个数
		nodepair_num = R.Dif_random(3, mylayer.getNodepair_num());
		// 随机生成5个nodepair
		HashMap<String, Nodepair> map2 = mylayer.getNodepairlist();
		Iterator<String> iter2 = map2.keySet().iterator();
		while (iter2.hasNext()) {
			Nodepair nowNodePair = (Nodepair) (map2.get(iter2.next()));
			for (n = 0; n < nodepair_num.length; n++) {
				if (SerialNum == nodepair_num[n]) {
					Nodepairlist.add(nowNodePair);
					// System.out.println("节点对名字： "+nowNodePair.getName());
					nodepair_serialNumber.put(SerialNum + "0", nowNodePair.getName());
					nodepair_intNumber.put(nowNodePair.getName(), SerialNum);
					nodepair_demand.put(nowNodePair.getName(), demand[p]);
					filewrite.filewrite("F:\\programFile\\5node\\radomnodepair.dat", nowNodePair.getName());
					filewrite.filewrite("F:\\programFile\\5node\\radomnodepair.dat", "\r\n");
					p++;
				}
			}
			SerialNum++;
		}

		// System.out.println(nodepair_demand.get(nowNodePair.getName()));
		for (int m = 0; m < Nodepairlist.size(); m++) {
			Nodepair nowNodePair = Nodepairlist.get(m);
//			System.out.println("节点对：" + nowNodePair.getName());
			Node srcNode = nowNodePair.getSrcNode();
			Node desNode = nowNodePair.getDesNode();

			RouteSearching dijkstra = new RouteSearching();
			LinearRoute newRoute = new LinearRoute(null, 0, null, 0);
			dijkstra.Dijkstras(srcNode, desNode, mylayer, newRoute, constraint);// 找寻工作路径

			WorkFormat WF = new WorkFormat();
			float need = WF.workformat(nowNodePair, newRoute);
			filewrite.filewrite("F:\\programFile\\5node\\demand.dat", nowNodePair.getName() + "   ");
			filewrite.filewrite("F:\\programFile\\5node\\demand.dat", (int) need);
			// System.out.println(nowNodePair.getName());
			// newRoute.OutputRoute_node(newRoute);
			filewrite.filewrite("F:\\programFile\\5node\\WORKROUTE.dat", nowNodePair.getName() + "   ");
			newRoute.OutputRoute_node(newRoute, "F:\\programFile\\5node\\WORKROUTE.dat");
			nodepair_workroute.put(nowNodePair.getName(), newRoute);
			// System.out.println("执行：");
			NewNodepair nn = new NewNodepair();
			// nn.newnodepair(nowNodePair,newcyclelist,mylayer,nodepair_workroute);
			nn.newnodepair(nowNodePair, cyclelist, mylayer, nodepair_workroute);
		}
		// System.out.println("节点对的个数： "+Nodepairlist.size());

		// //工作路径重叠比较
		WorkCross PD = new WorkCross();
		PD.workcross();

		// 将环以节点对为基础分为两段 输出到文件
		CycleAndNodepair CN = new CycleAndNodepair();
		// CN.cycleandnodepair(mylayer, newcyclelist);
		CN.cycleandnodepair(mylayer, cyclelist);
		// */
		System.out.println("finish");

	}

}
