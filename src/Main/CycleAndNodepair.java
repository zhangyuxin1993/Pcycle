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
			{ //������ʼ�����
				
				for(int m=i+2;m<inputcycle.getNodelist().size();m++)//ȡ�����ϵĿ��� ��nodepair��
				{  //������ֹ�����
					if((i==0&&m==inputcycle.getNodelist().size()-2)||m==inputcycle.getNodelist().size()-1) continue;
					
					String srcnode=inputcycle.getNodelist().get(i).getName();//�ڵ�Ե�Դ�ڵ�
					String desnode=inputcycle.getNodelist().get(m).getName();//�ڵ�Ե�Ŀ�Ľڵ�
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
					
//					�ж�ȡ���Ľڵ���demand�����Ƿ���
					for(int n=0;n<Nodepairlist.size();n++){
						Nodepair nodepair=Nodepairlist.get(n);
						if(nodepair.equals(protectNodepair)){
							nodepairhave=1;
							break;
						}
					}
					if(nodepairhave==0) continue;
					if(nodepairhave==1) nodepairhave=0;

//					�����жϹ���·���뱣��·���Ƿ��г�ͻ
					int noprotect=0;
					for(int n=0;n<inputcycle.getNodelist().size()-1;n++)//cycle�ϵ���·����
					{
						Node NodeA=new Node(inputcycle.getNodelist().get(n).getName(), 0, null, layer, 0, 0);
						Node NodeB=new Node(inputcycle.getNodelist().get(n+1).getName(), 0, null, layer, 0, 0);
						Link link1=layer.findlink(NodeA, NodeB);//link1�ǻ��ϵ���·
						
//						System.out.println(link1.getName());//TEST
//						test
//						CycleOutput out=new CycleOutput();
//						out.cycleoutput(inputcycle,"F:\\programFile\\5node\\5node\\test.dat");
//						System.out.println("�ڵ�Ե����֣� "+protectNodepair.getName());
//						nodepair_workroute.get(protectNodepair.getName()).OutputRoute_node(nodepair_workroute.get(protectNodepair.getName()));//TEST
						
						ArrayList<Link>linklist=nodepair_workroute.get(protectNodepair.getName()).getLinklist();
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
					//*********************����Ϊ�жϻ��ܷ񱣻��ڵ�
	
					if(noprotect==0)//��û�г�ͻ������ڵ�� �� ������·��
					{
						int file=nodepair_intNumber.get(protectNodepair.getName());
						String datafile=file+"0";
//						System.out.println("datafile:="+datafile+"0");//test
//						String filename="F:\\programFile\\5node\\"+datafile+".dat";
						CycleOutput out =new CycleOutput();
						//���protectcycle
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
										
					//�������·��
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
							nodepair_protectroute2.put(protectNodepair.getName()+inputcycle.toString(), protect2);//�ڵ���뱣��·������
							
							//�ڶ��������

							SecondProtectOut SPO=new SecondProtectOut();
							SPO.secondprotectout(protectNodepair, inputcycle, protect1, protect2);
		
//					/*
							//�ڶ�����·���빤��·�����ж�
							WorkFirstCross WFS2=new WorkFirstCross();
							WFS2.workfirstcross(protectNodepair, protect1, inputcycle,1,protect2);//work���һ������һ���ж�
							WFS2.workfirstcross(protectNodepair, protect2, inputcycle,1,protect1);//work���һ�����ڶ����ж�
//							work ��ڶ������ж� ��һ�����protect1�����ڶ����� ���Ӧ�ĵ�һ����Ϊprotect2
							
							WFS2.workfirstcross(protectNodepair, protect1, inputcycle,2,protect2);//work��ڶ�������һ���ж�
							WFS2.workfirstcross(protectNodepair, protect2, inputcycle,2,protect1);//work��ڶ������ڶ����ж�
							
		
							ProtectCross PC=new ProtectCross();
							PC.protectcross(protectNodepair, inputcycle,cyclelist, protect1,protect2);//protect2Ϊ����		
							PC.protectcross(protectNodepair, inputcycle,cyclelist, protect2,protect1);
							//����ΪFF���ж�
							FirstSecondCross FSC=new FirstSecondCross();
							FSC.firstsecondcross(protectNodepair, inputcycle, cyclelist, protect1, protect2);
							FSC.firstsecondcross(protectNodepair, inputcycle, cyclelist, protect2, protect1);
							//FS�ж�
							SecondSecond SS=new SecondSecond();
							SS.secondsecond(protectNodepair, inputcycle, cyclelist, protect1, protect2);
							SS.secondsecond(protectNodepair, inputcycle, cyclelist, protect2, protect1);
							
							
						//���Ƹ�ʽ
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