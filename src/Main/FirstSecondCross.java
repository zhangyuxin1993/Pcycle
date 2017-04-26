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
					
					if(protectNodepair.getName().equals(inputNodepair.getName())) continue;
//					�ж�ȡ���Ľڵ���demand�����Ƿ���
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
					
//					�����жϹ���·���뱣��·���Ƿ��г�ͻ
					int noprotect=0;
					for(int n=0;n<inputcycle.getNodelist().size()-1;n++)//cycle�ϵ���·����
					{
						Node NodeA=new Node(inputcycle.getNodelist().get(n).getName(), 0, null, layer, 0, 0);
						Node NodeB=new Node(inputcycle.getNodelist().get(n+1).getName(), 0, null, layer, 0, 0);
						Link link1=layer.findlink(NodeA, NodeB);//link1�ǻ��ϵ���·
//						 System.out.println(link1.getName());//TEST
						ArrayList<Link>linklist=nodepair_workroute.get(protectNodepair.getName()).getLinklist();
						for(Link nowlink:linklist)//linklist�����·���ϵ�linklist
						 {//�жϱ���·��������Ĺ���·�����޽���
					if(((nowlink.getNodeA().getName().equals(link1.getNodeA().getName()))&&(nowlink.getNodeB().getName().equals(link1.getNodeB().getName())))||((nowlink.getNodeA().getName().equals(link1.getNodeB().getName()))&&(nowlink.getNodeB().getName().equals(link1.getNodeA().getName()))))
						  		{ 
									noprotect=1;
						  			break;
						  		}	
						
					}
						  	if(noprotect==1) break;
					}
				//*************���ϲ���
					if(noprotect==0)//��û�г�ͻ������ڵ�� �� ������·��
					{
							//����protect1 ��protect2
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
								PanDuan PD=new PanDuan();//��һ������
								share1=PD.panduan(inputNodepair, inputprotect2,protectNodepair,protect2);//protect1Ϊ�ڶ�����
							}
							if(result2==1){
								PanDuan PD=new PanDuan();//��һ������
								share2=PD.panduan(inputNodepair, inputprotect2,protectNodepair,protect1);//protect2Ϊ�ڶ�����
							}
							//���ж������
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
							//·�����
							
							for(int count=0;count<protect1.size()-1;count++){
								filewrite.filewrite(filename, protect1.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect1.get(protect1.size()-1).getName()+"    ");//protect1Ϊ��һ����
							
							for(int count=0;count<protect2.size()-1;count++){
								filewrite.filewrite(filename, protect2.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect2.get(protect2.size()-1).getName()+"    ");//protect2Ϊ�ڶ�����
							
							filewrite.filewrite(filename, share2);
							share2=0;
//							filewrite.filewrite(filename, "\r\n");
							//������ɵ�һ��
							
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
							//·�����
							for(int count=0;count<protect2.size()-1;count++){
								filewrite.filewrite(filename, protect2.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect2.get(protect2.size()-1).getName());//protect2Ϊ��һ����
							filewrite.filewrite(filename, "    ");
							for(int count=0;count<protect1.size()-1;count++){
								filewrite.filewrite(filename, protect1.get(count).getName());
								filewrite.filewrite(filename, "-");
							}
							filewrite.filewrite(filename, protect1.get(protect1.size()-1).getName());//protect1Ϊ�ڶ�����
							
							
							filewrite.filewrite(filename,"    "+share1);
							share2=0;
							filewrite.filewrite(filename, "\r\n");

						  }
					}
	
				}
				}
		
					
	}
}

