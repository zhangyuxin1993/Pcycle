package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import General.file_out_put;
import groupwork.RouteSearching;
import groupwork.SearchConstraint;
import Network.Layer;
import Network.Link;
import Network.Node;
import Subgraph.Cycle;
import Subgraph.LinearRoute;

public class AEofCycle {
	static HashMap<String, Double> AeList=new HashMap<String, Double>();
	public  double aeofcycle(ArrayList<Cycle> cyclelist,Layer layer)
	{
		SearchConstraint constraint = new SearchConstraint(100000);
		double sum=0;
		
		for(int n=0;n<cyclelist.size();n++){
		ArrayList<Link> linklist=new ArrayList<Link>();
		int linknum=0,straddlinglinknum=0;
		double length=0;
		CycleOutput out =new CycleOutput();
		
		 Cycle nowcycle=cyclelist.get(n);
//		 System.out.println("新的环");
//		 out.cycleoutput(nowcycle,"E:\\programFile\\test.dat");	
		 //计算出环上链路数以及环的长度
		 for(int m=0;m<nowcycle.getLinklist().size()-1;m++){
			   linklist.clear();
				Link link=nowcycle.getLinklist().get(m);	
//				System.out.println("linkname： "+link.getName());
				length=length+link.getLength();
				linknum++;
				
		 }
		 //		 filewrite.filewrite("E:\\programFile\\linknum.dat", "\r\n");
//		 out.cycleoutput(nowcycle,"E:\\programFile\\linknum.dat");	
//		 filewrite.filewrite("E:\\programFile\\linknum.dat", "      "+linknum);
		 //计算跨接链路数
//		  System.out.println("环的节点：  ");
//			for(int b=0;b<nowcycle.getNodelist().size();b++){
//				System.out.print(nowcycle.getNodelist().get(b).getName()+"  ");
//			}
		 for(int b=0;b<nowcycle.getNodelist().size()-2;b++){
				Node A=nowcycle.getNodelist().get(b);
//			 System.out.print("节点A: "+A.getName());
				 for(int c=b+1;c<nowcycle.getNodelist().size()-1;c++){
						Node B=nowcycle.getNodelist().get(c);
//					 System.out.print("节点B: "+B.getName()+"   ");
//					 System.out.println();
					 //寻找route的源节点找到
		 RouteSearching RS=new RouteSearching();
		 ArrayList<LinearRoute> routelist=new ArrayList<LinearRoute>();
		 RS.findAllRoute(A, B, layer, constraint, 100, routelist);// route找到了
		 
		 //以下为route与环相比较
		 for(int routeout=0;routeout<routelist.size();routeout++){
			 LinearRoute route=routelist.get(routeout);
//			 route.OutputRoute_node(route);
			 
			 NodelistCompare NC=new NodelistCompare();
			 int cross=NC.nodelistcompare(route.getNodelist(), nowcycle.getNodelist());
//			 System.out.println("with cycle"+cross);
			 if(cross==1) continue;//切换不同的route
			 //route之间相比较 
//			 System.out.println("linklist里面的link");
//			 for(int linkout=0;linkout<linklist.size() ;linkout++){
//				 System.out.println(linklist.get(linkout).getName());
//			 }
//			 System.out.println("加入前的size"+linklist.size());
				 
			 for(int linkadd=0;linkadd<route.getLinklist().size() ;linkadd++){
				 Link addlink=route.getLinklist().get(linkadd);
				 Node src=addlink.getNodeA();
				 Node des=addlink.getNodeB();
				 //改变其顺序
				 if(src.getIndex()>des.getIndex()){
					 addlink=layer.findlink(des, src);
				 }
//				 System.out.println("要加进去的link"+addlink.getName());
				 if(!linklist.contains(addlink))
				 {
					 linklist.add(addlink);
				 }
//				 System.out.println("加入后的size"+linklist.size());
			 }
							 
//				 System.out.println("跨接路径");
//				 route.OutputRoute_node(route);		
				 straddlinglinknum=linklist.size();
				 
//				 System.out.println("跨接  ："+straddlinglinknum);
			 }	
		 
		
		}
			
		 }
		 
//		 System.out.println("环上链路： "+linknum+"跨接链路数： "+straddlinglinknum);
		 double AE=(linknum+straddlinglinknum*2)/length*1000;
		 AeList.put(nowcycle.toString(), AE);
//		 System.out.println(AE);
		 sum=sum+AE;
		 
		}
		 double average=0;
		 average=sum/cyclelist.size();
//		 System.out.println(" average ="+average);
//		 file_out_put filewrite=new file_out_put();
//		filewrite.filewrite("E:\\programFile\\AVERAGE.dat",  );

		 return average;
		 
	}
}
