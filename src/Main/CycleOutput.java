package Main;

import java.util.ArrayList;

import General.file_out_put;
import Network.Node;
import Subgraph.Cycle;

public class CycleOutput {
	public  void cycleoutput(ArrayList<Cycle> cyclelist,String filename)
	{
		
		for(int i=0;i<cyclelist.size();i++)
		{
			file_out_put filewrite=new file_out_put();
			Cycle CycleContain=cyclelist.get(i);
			
			int m=0;
			for(Node Newnode:CycleContain.getNodelist())
			{
				
				filewrite.filewrite(filename, Newnode.getName());
				if(Newnode.getName()==CycleContain.getNodelist().get(0).getName()&&m==0)
				{
					filewrite.filewrite(filename, "-");
				}
				if(Newnode.getName()!=CycleContain.getNodelist().get(0).getName())
				{	
				 filewrite.filewrite(filename, "-");
				}
				m=1;
			}
			filewrite.filewrite(filename, "\r\n");
		}
	}
	public  void cycleoutput(Cycle CycleContain,String filename)
	{
							
			int m=0;
			file_out_put filewrite=new file_out_put();
			for(Node Newnode:CycleContain.getNodelist())
			{
				
				filewrite.filewrite(filename, Newnode.getName());
				if(Newnode.getName()==CycleContain.getNodelist().get(0).getName()&&m==0)
				{
				filewrite.filewrite(filename, "-");
				}
				if(Newnode.getName()!=CycleContain.getNodelist().get(0).getName())
				{	
				filewrite.filewrite(filename, "-");}
				m=1;
			}
		}
	}
	

