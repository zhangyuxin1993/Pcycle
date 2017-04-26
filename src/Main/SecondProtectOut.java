package Main;

import java.util.ArrayList;

import General.file_out_put;
import Network.Node;
import Network.Nodepair;
import Subgraph.Cycle;

public class SecondProtectOut {
	public  void secondprotectout(Nodepair nodepair,Cycle cycle,ArrayList<Node> protect1,ArrayList<Node> protect2)
	{
		file_out_put filewrite=new file_out_put();
		CycleOutput out =new CycleOutput();

		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "\r\n");
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat","set R2["+nodepair.getName()+",");
		out.cycleoutput(cycle,"F:\\programFile\\5node\\R2.dat");
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat",",");
		for(int pro=0;pro<protect1.size()-1;pro++){
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect1.get(pro).getName());
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "-");
		}
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect1.get(protect1.size()-1).getName());
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "]:=");
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "\r\n");
		for(int pro=0;pro<protect2.size()-1;pro++){
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect2.get(pro).getName());
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "-");
		}
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect2.get(protect2.size()-1).getName());
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", ";");
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "\r\n");
//		******************第二保护输出
		
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat","set R2["+nodepair.getName()+",");
		out.cycleoutput(cycle,"F:\\programFile\\5node\\R2.dat");
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat",",");
		for(int pro=0;pro<protect2.size()-1;pro++){
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect2.get(pro).getName());
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "-");
			
		}
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect2.get(protect2.size()-1).getName());
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "]:=");
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "\r\n");
		for(int pro=0;pro<protect1.size()-1;pro++){
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect1.get(pro).getName());
			filewrite.filewrite("F:\\programFile\\5node\\R2.dat", "-");
		}
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", protect1.get(protect1.size()-1).getName());
		filewrite.filewrite("F:\\programFile\\5node\\R2.dat", ";");
	}

}
