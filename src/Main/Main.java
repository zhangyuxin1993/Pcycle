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
		String filename = "G:/Topology/NSFNET.csv";
		// String filename="E:/ZYX/Topology/5.csv";
		mylayer.readTopology(filename);
		mylayer.generateNodepairs();
		ArrayList<Cycle> cyclelist = new ArrayList<Cycle>();
		ArrayList<Cycle> newcyclelist = new ArrayList<Cycle>();
		RouteSearching shortestpath = new RouteSearching();
		SearchConstraint constraint = new SearchConstraint(100000);

		String filename1="F:\\programFile\\5node\\Cycle.dat";
		String filename2="F:\\programFile\\5node\\newCycle.dat";
		String filename3="F:\\programFile\\5node\\OnCycle.dat";
		String filename4="F:\\programFile\\5node\\WORKROUTE.dat";
		
		filewrite.filewrite("F:\\programFile\\5node\\nodepair.dat", "set D:=");
		filewrite.filewrite("F:\\programFile\\5node\\nodepair.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\Cycle.dat", "set C:=");
		filewrite.filewrite("F:\\programFile\\5node\\Cycle.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\newCycle.dat", "set C:=");
		filewrite.filewrite("F:\\programFile\\5node\\newCycle.dat", "\r\n");
		
		filewrite.filewrite("F:\\programFile\\5node\\trafficDemand.dat", "param  WorkingDemand :=");
		filewrite.filewrite("F:\\programFile\\5node\\trafficDemand.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\FFCorss.dat", "param  FirstFirstShare:=");
		filewrite.filewrite("F:\\programFile\\5node\\FFCorss.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\FSCorss.dat", "param  FirstSecondShare:=");
		filewrite.filewrite("F:\\programFile\\5node\\FSCorss.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\SSCorss.dat", "param  SecondSecondShare:=");
		filewrite.filewrite("F:\\programFile\\5node\\SSCorss.dat", "\r\n");
		
		
		// �ҳ����еĻ�
		HashMap<String, Node> map = mylayer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			shortestpath.findAllCycle(node, mylayer, constraint, cyclelist);

		}
		CycleOutput out = new CycleOutput();
		out.cycleoutput(cyclelist, filename1);

		// ���Ƿ���Ҫ����
		/*
		AEofCycle AE = new AEofCycle();
		AE.aeofcycle(cyclelist, mylayer);
		AeList = AEofCycle.AeList;
		for (int b = 0; b < cyclelist.size(); b++) {
			Cycle nowcycle = cyclelist.get(b);
			// out.cycleoutput(nowcycle,"F:\\programFile\\5node\\test.dat");
			double nowae = AeList.get(nowcycle.toString());
			// if(nowae>average){
			// System.out.print(average);
			if (nowae > 0) {
				newcyclelist.add(nowcycle);
			}
		}
		out.cycleoutput(newcyclelist, filename2);
		*///��ڵ㲻��Ҫ���ٻ�
		
		filewrite.filewrite(filename3, "set  L :=");
		filewrite.filewrite(filename3, "\r\n");
		// /* linkoncycle
//		HashMap<String, Link> linkmap2 = mylayer.getLinklist();
//		Iterator<String> linkiter2 = linkmap2.keySet().iterator();
//		while (linkiter2.hasNext()) {
//			Link link = (Link) (linkmap2.get(linkiter2.next()));
//			if (link.getNodeA().getIndex() < link.getNodeB().getIndex()) {
//				filewrite.filewrite(filename3, link.getName());
//				filewrite.filewrite(filename3, "\r\n");
//			}
//
//		}
		filewrite.filewrite(filename3, ";");
		filewrite.filewrite(filename3, "\r\n");

		filewrite.filewrite(filename3, "param  LinkOnCycle :=");
		filewrite.filewrite(filename3, "\r\n");
		// ע�⣺���ǰ��ִ���˼��ٻ� ���ڵ��µĲ�����Ҫ��cyclelist���newcyclelist 3��
		// for(Cycle cycle:newcyclelist){
//		for (Cycle cycle : cyclelist) {
//			int oncycle = 0;
//			HashMap<String, Link> linkmap = mylayer.getLinklist();
//			Iterator<String> linkiter = linkmap.keySet().iterator();
//			while (linkiter.hasNext()) {
//				Link link = (Link) (linkmap.get(linkiter.next()));
//				if (link.getNodeA().getIndex() < link.getNodeB().getIndex()) {
//					Link newlink = mylayer.findlink(link.getNodeB(), link.getNodeA());
//					if (cycle.getLinklist().contains(link)) {
//						oncycle = 1;
//					}
//					if (cycle.getLinklist().contains(newlink)) {
//						oncycle = 1;
//					}
//					filewrite.filewrite(filename3, link.getName() + "    ");
//					out.cycleoutput(cycle, filename3);
//					filewrite.filewrite(filename3, "    ");
//					// System.out.println(link.getName());
//					filewrite.filewrite(filename3, oncycle);
//					// filewrite.filewrite(filename3,
//					// "\r\n");
//					oncycle = 0;
//				}
//			}
//		}
//
//		filewrite.filewrite(filename3, ";");
		// /*

		// �ҳ�����·�� ����Ϊ�ڵ��������乤������
		int SerialNum = 0;
		int[] nodepair_num;
		int n, p = 0;

		random R = new random();
		// �������5����
		nodepair_num = R.Dif_random(91, mylayer.getNodepair_num());//���Ʋ����Ľڵ����
		// �������5��nodepair
		HashMap<String, Nodepair> map2 = mylayer.getNodepairlist();
		Iterator<String> iter2 = map2.keySet().iterator();
		while (iter2.hasNext()) {
			Nodepair nowNodePair = (Nodepair) (map2.get(iter2.next()));
			for (n = 0; n < nodepair_num.length; n++) {
				if (SerialNum == nodepair_num[n]) {
					Nodepairlist.add(nowNodePair);
					// System.out.println("�ڵ�����֣� "+nowNodePair.getName());
					nodepair_serialNumber.put(SerialNum + "0", nowNodePair.getName());
					nodepair_intNumber.put(nowNodePair.getName(), SerialNum);
					p++;
				}
			}
			SerialNum++;
		}

		// System.out.println(nodepair_demand.get(nowNodePair.getName()));
		for (int m = 0; m < Nodepairlist.size(); m++) {
			Nodepair nowNodePair = Nodepairlist.get(m);
//			System.out.println("�ڵ�ԣ�" + nowNodePair.getName());
			Node srcNode = nowNodePair.getSrcNode();
			Node desNode = nowNodePair.getDesNode();

			RouteSearching dijkstra = new RouteSearching();
			LinearRoute newRoute = new LinearRoute(null, 0, null, 0);
			dijkstra.Dijkstras(srcNode, desNode, mylayer, newRoute, constraint);// ��Ѱ����·��

			filewrite.filewrite(filename4, nowNodePair.getName() + "   ");
			newRoute.OutputRoute_node(newRoute, filename4);
			filewrite.filewrite(filename4, "\r\n");
			nodepair_workroute.put(nowNodePair.getName(), newRoute);
			// System.out.println("ִ�У�");
			NewNodepair nn = new NewNodepair();
			// nn.newnodepair(nowNodePair,newcyclelist,mylayer,nodepair_workroute);
			nn.newnodepair(nowNodePair, cyclelist, mylayer, nodepair_workroute);
		}
		// System.out.println("�ڵ�Եĸ����� "+Nodepairlist.size());
/*
		// //����·���ص��Ƚ�
		WorkCross PD = new WorkCross();
		PD.workcross();

		// �����Խڵ��Ϊ������Ϊ���� ������ļ�
		CycleAndNodepair CN = new CycleAndNodepair();
		// CN.cycleandnodepair(mylayer, newcyclelist);
		CN.cycleandnodepair(mylayer, cyclelist);
 */
		System.out.println("finish");

		filewrite.filewrite("F:\\programFile\\5node\\Cycle.dat", "param  LargeConstant :=");
		filewrite.filewrite("F:\\programFile\\5node\\Cycle.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\Cycle.dat", "10000 ;");
		filewrite.filewrite("F:\\programFile\\5node\\nodepair.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\Cycle.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\trafficDemand.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\FFCorss.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\FSCorss.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\SSCorss.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\newCycle.dat", ";");

	}

}
