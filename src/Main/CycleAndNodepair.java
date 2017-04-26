package Main;

import java.util.ArrayList;
import java.util.HashMap;

import General.file_out_put;
import Main.CycleOutput;
//import Main.Format;
//import Main.ProtectCross;
//import Main.WorkFirstCross;
//import Main.mainfunction;
import Network.Layer;
import Network.Link;
import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;
import Subgraph.LinearRoute;

public class CycleAndNodepair {
	
	HashMap<String, ArrayList<Node>> nodepair_protectroute1=new HashMap<String, ArrayList<Node>>();
	HashMap<String, ArrayList<Node>> nodepair_protectroute2=new HashMap<String, ArrayList<Node>>();
	ArrayList<Nodepair> Nodepairlist=Main.Nodepairlist;
	ArrayList<Node> protect1=new ArrayList<Node>();
	ArrayList<Node> protect2=new ArrayList<Node>();
	HashMap<String, Integer> nodepair_intNumber=Main.nodepair_intNumber;
	
	public  void cycleandnodepair(Layer layer,ArrayList<Cycle> cyclelist)
	{
		ArrayList<String> count=new ArrayList<String>();
		HashMap<String, LinearRoute> nodepair_workroute=Main.nodepair_workroute;
		
		file_out_put filewrite=new file_out_put();
		int nodepairhave=0;
		for(int q=0;q<cyclelist.size();q++)
		{
			Cycle inputcycle=cyclelist.get(q);
			Nodepair protectNodepair=new Nodepair(null, 0, null, null, null, null);
			
			if(inputcycle.getNodelist().size()<=4) continue;
		
			for(int i=0;i<inputcycle.getNodelist().size()-3;i++)
			{ //环上起始点遍历
				
				for(int m=i+2;m<inputcycle.getNodelist().size();m++)//取出环上的跨界点 （nodepair）
				{  //环上终止点遍历
					if((i==0&&m==inputcycle.getNodelist().size()-2)||m==inputcycle.getNodelist().size()-1) continue;
					
					String srcnode=inputcycle.getNodelist().get(i).getName();//节点对的源节点
					String desnode=inputcycle.getNodelist().get(m).getName();//节点对的目的节点
					Node Srcnode=layer.findNode(inputcycle.getNodelist().get(i).getName(), layer);
					Node Desnode=layer.findNode(inputcycle.getNodelist().get(m).getName(), layer);
					
					if(Srcnode.getIndex()<Desnode.getIndex())
				   {
					   protectNodepair= layer.findNodepair(srcnode+"-"+desnode);  
				   }
					if(Srcnode.getIndex()>Desnode.getIndex())
				   {
					   protectNodepair= layer.findNodepair(desnode+"-"+srcnode); 
				   }
					
//					判断取出的节点在demand里面是否有
					for(int n=0;n<Nodepairlist.size();n++){
						Nodepair nodepair=Nodepairlist.get(n);
						if(nodepair.equals(protectNodepair)){
							nodepairhave=1;
							break;
						}
					}
					if(nodepairhave==0) continue;
					if(nodepairhave==1) nodepairhave=0;

//					首先判断工作路径与保护路径是否有冲突
					int noprotect=0;
					for(int n=0;n<inputcycle.getNodelist().size()-1;n++)//cycle上的链路遍历
					{
						Node NodeA=new Node(inputcycle.getNodelist().get(n).getName(), 0, null, layer, 0, 0);
						Node NodeB=new Node(inputcycle.getNodelist().get(n+1).getName(), 0, null, layer, 0, 0);
						Link link1=layer.findlink(NodeA, NodeB);//link1是环上的链路
						
//						System.out.println(link1.getName());//TEST
//						test
//						CycleOutput out=new CycleOutput();
//						out.cycleoutput(inputcycle,"F:\\programFile\\5node\\5node\\test.dat");
//						System.out.println("节点对的名字： "+protectNodepair.getName());
//						nodepair_workroute.get(protectNodepair.getName()).OutputRoute_node(nodepair_workroute.get(protectNodepair.getName()));//TEST
						
						ArrayList<Link>linklist=nodepair_workroute.get(protectNodepair.getName()).getLinklist();
						for(Link nowlink:linklist)//linklist是最短路由上的linklist
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
					//*********************以上为判断环能否保护节点
	
					if(noprotect==0)//若没有冲突则输出节点对 环 与两条路径
					{
						int file=nodepair_intNumber.get(protectNodepair.getName());
						String datafile=file+"0";
//						System.out.println("datafile:="+datafile+"0");//test
//						String filename="F:\\programFile\\5node\\"+datafile+".dat";
						CycleOutput out =new CycleOutput();
						//输出protectcycle
//						if(!count.contains(datafile)){
//							filewrite.filewrite(filename,"set ProtectCycle["+protectNodepair.getName()+"] :=");
//							filewrite.filewrite(filename, "\r\n");	
//						}	
//						out.cycleoutput(inputcycle,filename);
//						filewrite.filewrite(filename, "\r\n");
//						count.add(datafile);

						datafile=null;
						
						filewrite.filewrite("F:\\programFile\\5node\\R1.dat", "\r\n");
						filewrite.filewrite("F:\\programFile\\5node\\R1.dat","set R1["+protectNodepair.getName()+",");
						out.cycleoutput(inputcycle,"F:\\programFile\\5node\\R1.dat");						
						filewrite.filewrite("F:\\programFile\\5node\\R1.dat","] :=");
						filewrite.filewrite("F:\\programFile\\5node\\R1.dat", "\r\n");
										
					//输出保护路径
						int x=m-1;
						protect1.clear();
						do{
							if(noprotect==1)
							{
								noprotect=0;
								break;	
							}
							x++;
							filewrite.filewrite("F:\\programFile\\5node\\R1.dat", inputcycle.getNodelist().get(x).getName());
//							System.out.print(inputcycle.getNodelist().get(x).getName());
							protect1.add(inputcycle.getNodelist().get(x));
							if(x==inputcycle.getNodelist().size()-1) 
							 {
								x=0;
								if(x==i) break;

							 }
							if(x!=i)
							{
							filewrite.filewrite("F:\\programFile\\5node\\R1.dat", "-");	
//							System.out.print("-");
							}
							
						}
						while(x!=i);
//						filewrite.filewrite("F:\\programFile\\5node\\R1.dat", ";");
						filewrite.filewrite("F:\\programFile\\5node\\R1.dat", "\r\n");
						
						 protect2.clear();
							for(int n=i;n<=m;n++)
							{
								
								filewrite.filewrite("F:\\programFile\\5node\\R1.dat", inputcycle.getNodelist().get(n).getName());
//								System.out.print(inputcycle.getNodelist().get(n).getName());
								protect2.add(inputcycle.getNodelist().get(n));
							if(n!=m)
								{
//									System.out.print("-");
									filewrite.filewrite("F:\\programFile\\5node\\R1.dat", "-");
								
								}	
							}
							filewrite.filewrite("F:\\programFile\\5node\\R1.dat", ";");
							nodepair_protectroute2.put(protectNodepair.getName()+inputcycle.toString(), protect2);//节点对与保护路径放入
							
							//第二保护输出

							SecondProtectOut SPO=new SecondProtectOut();
							SPO.secondprotectout(protectNodepair, inputcycle, protect1, protect2);
		
//					/*
							//第二保护路径与工作路径的判断
							WorkFirstCross WFS2=new WorkFirstCross();
							WFS2.workfirstcross(protectNodepair, protect1, inputcycle,1,protect2);//work与第一保护第一条判断
							WFS2.workfirstcross(protectNodepair, protect2, inputcycle,1,protect1);//work与第一保护第二条判断
//							work 与第二保护判断 第一行命令将protect1当做第二保护 其对应的第一保护为protect2
							
							WFS2.workfirstcross(protectNodepair, protect1, inputcycle,2,protect2);//work与第二保护第一条判断
							WFS2.workfirstcross(protectNodepair, protect2, inputcycle,2,protect1);//work与第二保护第二条判断
							
		
							ProtectCross PC=new ProtectCross();
							PC.protectcross(protectNodepair, inputcycle,cyclelist, protect1,protect2);//protect2为辅助		
							PC.protectcross(protectNodepair, inputcycle,cyclelist, protect2,protect1);
							//以上为FF的判断
							FirstSecondCross FSC=new FirstSecondCross();
							FSC.firstsecondcross(protectNodepair, inputcycle, cyclelist, protect1, protect2);
							FSC.firstsecondcross(protectNodepair, inputcycle, cyclelist, protect2, protect1);
							//FS判断
							SecondSecond SS=new SecondSecond();
							SS.secondsecond(protectNodepair, inputcycle, cyclelist, protect1, protect2);
							SS.secondsecond(protectNodepair, inputcycle, cyclelist, protect2, protect1);
							
							
						//调制格式
//						int need=0;
//						Format F=new Format();
//						need=(int) (F.format(protectNodepair, protect1, protect2));
//						filewrite.filewrite("F:\\programFile\\5node\\format.dat", protectNodepair.getName()+"    ");
//						out.cycleoutput(inputcycle,"F:\\programFile\\5node\\format.dat");
//						filewrite.filewrite("F:\\programFile\\5node\\format.dat", "    ");
//						filewrite.filewrite("F:\\programFile\\5node\\format.dat", need);
//						
//			 */
					}
					
				}
			}
								
	}

}
}