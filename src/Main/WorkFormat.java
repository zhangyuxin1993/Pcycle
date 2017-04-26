package Main;

import java.util.HashMap;

import General.file_out_put;
import Network.Link;
import Network.Nodepair;
import Subgraph.LinearRoute;

public class WorkFormat {
	HashMap<String, Integer> nodepair_demand=Main.nodepair_demand;
	public  float workformat(Nodepair nodepair,LinearRoute Route)
	{
		file_out_put filewrite=new file_out_put();
		int length=0,se=0;
		float need=0;
		for(int n=0;n<Route.getLinklist().size();n++)
		{
			Link link=Route.getLinklist().get(n);
			length=(int) (length+link.getLength());
		}
		if(length<1000) se=3;
		if(1000<=length&&length<2000) se=2;
		if(2000<=length&&length<=4000) se=1;
		if(length>4000)  
		filewrite.filewrite("E:\\programFile\\error.txt", "¹¤×÷¾àÀëÌ«³¤");
		need=(float) (nodepair_demand.get(nodepair.getName())/(2*se*12.5));
//		System.out.println("nodepair_demand:  "+nodepair_demand.get(nodepair.getName())+"  need:  "+need+"  se:  "+se);
		
		return need;
	}
}
