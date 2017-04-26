package Main;

import java.util.ArrayList;
import java.util.HashMap;

import General.file_out_put;
import Network.Layer;
import Network.Link;
import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;
import Subgraph.LinearRoute;

public class FirstSecondCross {
	public  void firstsecondcross(Nodepair inputNodepair,Cycle cycle,ArrayList<Cycle> cyclelist,ArrayList<Node> inputprotect,ArrayList<Node> inputprotect2)
	{
		ArrayList<Nodepair> Nodepairlist=Main.Nodepairlist;
		String filename="F:\\programFile\\5node\\FSCorss.dat";
		ArrayList<Node> protect1=new ArrayList<Node>();
		ArrayList<Node> protect2=new ArrayList<Node>();
		HashMap<String, String> nodepair_serialNumber=Main.nodepair_serialNumber;
		HashMap<String, LinearRoute> nodepair_workroute=Main.nodepair_workroute;
		Layer layer=Main.mylayer;
		file_out_put filewrite=new file_out_put();
	
		
		for(int q=0;q<cyclelist.size();q++)
		{
			Cycle inputcycle=cyclelist.get(q);
			Nodepair protectNodepair=new Nodepair(null, 0, null, null, null, null);
		
			if(inputcycle.getNodelist().size()<=4) continue;
//			System.out.println("inputcycle:  "+inputcycle.toString());
		
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
					
					if(protectNodepair.getName().equals(inputNodepair.getName())) continue;
//					判断取出的节点在demand里面是否有
					int nodepairhave=0;
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
//						 System.out.println(link1.getName());//TEST
						ArrayList<Link>linklist=nodepair_workroute.get(protectNodepair.getName()).getLinklist();
						for(Link nowlink:linklist)//linklist是最短路由上的linklist
						 {//判断保护路径与自身的工作路径有无交叉
					if(((nowlink.getNodeA().getName().equals(link1.getNodeA().getName()))&&(nowlink.getNodeB().getName().equals(link1.getNodeB().getName())))||((nowlink.getNodeA().getName().equals(link1.getNodeB().getName()))&&(nowlink.getNodeB().getName().equals(link1.getNodeA().getName()))))
						  		{ 
									noprotect=1;
						  			break;
						  		}	
						
					}
						  	if(noprotect==1) break;
					}
				//*************以上不变
					if(noprotect==0)//若没有冲突则输出节点对 环 与两条路径
					{
							//生成protect1 和protect2
							int x=m-1;
							protect1.clear();	
							do{
								if(noprotect==1)
								{
									noprotect=0;
									break;	
								}
								x++;
								protect1.add(inputcycle.getNodelist().get(x));
								if(x==inputcycle.getNodelist().size()-1) 
								 {
									x=0;
									if(x==i) break;
								 }
							}
							while(x!=i);
							
							protect2.clear();
							for(int n=i;n<=m;n++)
							{
								protect2.add(inputcycle.getNodelist().get(n));
							
							}

							int share1=0,share2=0;
							NodelistCompare NC=new NodelistCompare();
							int result1=NC.nodelistcompare(inputprotect, protect1);
							int result2=NC.nodelistcompare(inputprotect, protect2);
							if(result1==1){
								PanDuan PD=new PanDuan();//第一在这里
								share1=PD.panduan(inputNodepair, inputprotect2,protectNodepair,protect2);//protect1为第二保护
							}
							if(result2==1){
								PanDuan PD=new PanDuan();//第一在这里
								share2=PD.panduan(inputNodepair, inputprotect2,protectNodepair,protect1);//protect2为第二保护
							}
							//先判断在输出
							filewrite.filewrite(filename,inputNodepair.getName()+"    ");
							CycleOutput out =new CycleOutput();
							out.cycleoutput(cycle,filename);
							filewrite.filewrite(filename, "    ");
							for(int p=0;p<inputprotect.size()-1;p++)
							{
								filewrite.filewrite(filename,inputprotect.get(p).getName()+"-");
							}
							filewrite.filewrite(filename,inputprotect.get(inputprotect.size()-1).getName()+"    ");
							filewrite.filewrite(filename,protectNodepair.getName()+"    ");	
							out.cycleoutput(inputcycle,filename);
							filewrite.filewrite(filename, "    ");
							//路径输出
							
							for(int count=0;count<protect1.size()-1;count++){
								filewrite.filewrite(filename, protect1.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect1.get(protect1.size()-1).getName()+"    ");//protect1为第一保护
							
							for(int count=0;count<protect2.size()-1;count++){
								filewrite.filewrite(filename, protect2.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect2.get(protect2.size()-1).getName()+"    ");//protect2为第二保护
							
							filewrite.filewrite(filename, share2);
							share2=0;
//							filewrite.filewrite(filename, "\r\n");
							//以上完成第一段
							
							filewrite.filewrite(filename,inputNodepair.getName()+"    ");
							out.cycleoutput(cycle,filename);
							filewrite.filewrite(filename, "    ");
							for(int p=0;p<inputprotect.size()-1;p++)
							{
								filewrite.filewrite(filename,inputprotect.get(p).getName()+"-");
							}
							filewrite.filewrite(filename,inputprotect.get(inputprotect.size()-1).getName()+"    ");
							filewrite.filewrite(filename,protectNodepair.getName()+"    ");	
							out.cycleoutput(inputcycle,filename);
							filewrite.filewrite(filename, "    ");
							//路径输出
							for(int count=0;count<protect2.size()-1;count++){
								filewrite.filewrite(filename, protect2.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect2.get(protect2.size()-1).getName());//protect2为第一保护
							filewrite.filewrite(filename, "    ");
							for(int count=0;count<protect1.size()-1;count++){
								filewrite.filewrite(filename, protect1.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect1.get(protect1.size()-1).getName());//protect1为第二保护
							
							
							filewrite.filewrite(filename,"    "+share1);
							share2=0;
							filewrite.filewrite(filename, "\r\n");

						  }
					}
	
				}
				}
		
					
	}
}

