package Main;

import java.util.ArrayList;
import java.util.HashMap;

import General.file_out_put;
import Network.Layer;
import Network.Link;
import Network.Node;
import Network.Nodepair;


public class Format {
	Layer layer=Main.mylayer;
	HashMap<String, Integer> nodepair_demand=Main.nodepair_demand;

	public  float format(Nodepair nodepair,ArrayList<Node> protect1,ArrayList<Node> protect2)
	{
		file_out_put filewrite=new file_out_put();
		int length1=0,length2=0,se=0;
		float need1=0,need2=0,need=0;
		for(int n=0;n<protect1.size()-1;n++){
			Node node1=protect1.get(n);
			Node node2=protect1.get(n+1);
			Link link1=layer.findlink(node1, node2);
			length1=(int) (length1+link1.getLength());
		}
		if(length1<1000) se=3;
		if(1000<=length1&&length1<2000) se=2;
		if(2000<=length1&&length1<=4000) se=1;
		if(length1>4000)  filewrite.filewrite("E:\\programFile\\error.txt", "第一距离太长");
		
		need1=(float) (nodepair_demand.get(nodepair.getName())/(2*se*12.5));
		//第二需求
		for(int m=0;m<protect2.size()-1;m++){
			Node node1=protect2.get(m);
			Node node2=protect2.get(m+1);
			Link link2=layer.findlink(node1, node2);
			length2=(int) (length2+link2.getLength());
		}
		if(length2<1000) se=3;
		if(1000<=length2&&length2<2000) se=2;
		if(2000<=length2&&length2<=4000) se=1;
		if(length2>4000)  filewrite.filewrite("E:\\programFile\\error.txt", "第二距离太长");
			
		need2=(float) (nodepair_demand.get(nodepair.getName())/(2*se*12.5));
		//比较
		if(need1<=need2) return need2;
		else return need1;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
