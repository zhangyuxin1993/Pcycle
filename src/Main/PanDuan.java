package Main;

import java.util.ArrayList;
import java.util.HashMap;

import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;
import Subgraph.LinearRoute;

public class PanDuan {
	HashMap<String, LinearRoute> nodepair_workroute=Main.nodepair_workroute;
	HashMap<String, Integer> workcrossResult=WorkCross.workcrossResult;
	HashMap<String, Integer> nodepair_intNumber=Main.nodepair_intNumber;

	public  int panduan(Nodepair nodepair1,ArrayList<Node> P1,Nodepair nodepair2,ArrayList<Node> P2)
	{
	//开始庞大的比较事业
		int ww=0,w1p2=0,w2p1=0,p1p2=0,share=0;
		if(nodepair_intNumber.get(nodepair1.getName())<nodepair_intNumber.get(nodepair2.getName()))
		{
//			System.out.println("*****"+nodepair1.getName()+nodepair2.getName());
			ww=workcrossResult.get(nodepair1.getName()+nodepair2.getName());
		}
		else{
			ww=workcrossResult.get(nodepair2.getName()+nodepair1.getName());
		}
//		System.out.println(ww);
		NodelistCompare NC=new NodelistCompare();
		
		w1p2=NC.nodelistcompare(nodepair_workroute.get(nodepair1.getName()).getNodelist(), P2);
		w2p1=NC.nodelistcompare(nodepair_workroute.get(nodepair2.getName()).getNodelist(), P1);
		p1p2=NC.nodelistcompare(P1,P2);
//		System.out.println("w1p2="+w1p2+"w2p1="+w2p1+"p1p2="+p1p2);
		if((ww==1&&p1p2==1)||(w1p2==1&&w2p1==1)){
			share=1;
		}
//		System.out.println(share);
	return share;	
	
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
