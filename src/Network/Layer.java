package Network;

//import java.io.BufferedReader;
//import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

//import com.sun.java.util.jar.pack.Package.File;

import General.*;
import Subgraph.LinearRoute;
public class Layer extends CommonObject{
    private HashMap<String,Node>nodelist=null;
    private HashMap<String,Link>linklist=null;
    private HashMap<String,Nodepair>nodepairlist=null;
  
	public HashMap<String, Node> getNodelist() {
		return nodelist;
	}
	public void setNodelist(HashMap<String, Node> nodelist) {
		this.nodelist = nodelist;
	}
	public HashMap<String, Link> getLinklist() {
		return linklist;
	}
	public void setLinklist(HashMap<String, Link> linklist) {
		this.linklist = linklist;
	}
	public HashMap<String, Nodepair> getNodepairlist() {
		return nodepairlist;
	}
	public void setNodepairlist(HashMap<String, Nodepair> nodepairlist) {
		this.nodepairlist = nodepairlist;
	}
	public Layer(String name, int index, String comments) {
		super(name, index, comments);
		// TODO Auto-generated constructor stub
      this.nodelist=new HashMap<String, Node>(40);
      this.linklist=new HashMap<String, Link>(100);
      this.nodepairlist=new HashMap<String, Nodepair>(800);
	}
	/*
	 * add node to the layer
	 */
	public void addNode(Node node){
		this.nodelist.put(node.getName(), node);
		node.setAssociatedLayer(this);
    }
	/*
	 * remove node from the layer and remvoe the link about the node in the same time
	 */
	public void removeNode(String nodename){
		Node node=this.nodelist.get(nodename);
		node.setAssociatedLayer(null);
		String linkname="";
		for(int i=0;i<node.getNeinodelist().size();i++){
			Node nodeB=node.getNeinodelist().get(i);
			if(node.getIndex()<nodeB.getIndex()){
				linkname=node.getName()+"-"+nodeB.getName();
				this.linklist.remove(linkname);
			}
			else{
				linkname=nodeB.getName()+"-"+node.getName();
				this.linklist.remove(linkname);
			}
		}
		this.nodelist.remove(nodename);
	}
	/*
	 * add link to the layer
	 */
	 public void addlink(Link link){
		 this.linklist.put(link.getName(), link);
		 link.setAssociatedLayer(this);
	 }
	 /*
	  * remove link from the layer
	  */
	 public void removeLink(String linkname){
		 this.linklist.get(linkname).setAssociatedLayer(null);
		 this.linklist.remove(linkname);
		  }
	 /*
	  * add nodepair to the layer
	  */
	 public void addNodepair(Nodepair nodepair){
		 this.nodepairlist.put(nodepair.getName(), nodepair);
		 nodepair.setAssociatedLayer(this);
	 }
	 /*
	  * remove nodepair from the layer
	  */
	 public void removeNodepair(String nodepairname){
		 this.nodepairlist.get(nodepairname).setAssociatedLayer(null);
		 this.nodepairlist.remove(nodepairname);
	 }
	 /*
	  * get number of all the nodes ,links,nodepairs
	  */
	 public int getNode_num(){
		 return this.nodelist.size();
	 }
	 public int getLink_num(){
		 return this.linklist.size();
	 }
	 public int getNodepair_num(){
		 return this.nodepairlist.size();
	 }
	 /*
	  * copy all the nodes 
	  */
	 public void copyNodes(Layer layer){
		 HashMap<String ,Node>map=layer.getNodelist();
		 Iterator<String>iter1=map.keySet().iterator();
		 while(iter1.hasNext()){
			 Node nodeA=(Node)(map.get(iter1.next()));
			 Node nodeB=new Node(nodeA.getName(),nodeA.getIndex(),"",this,nodeA.getX(),nodeA.getY());
	         this.addNode(nodeB);
		 }
		 
	 }
	 /*
	  * Create nodepair based on the existing nodelist
	  */
	 public void generateNodepairs(){
		 HashMap<String,Node>map=this.getNodelist();
		 HashMap<String,Node>map2=this.getNodelist();
		 Iterator<String>iter1=map.keySet().iterator();
		 while(iter1.hasNext()){
			 Node node1=(Node)(map.get(iter1.next()));
			 Iterator<String>iter2=map2.keySet().iterator();
			 while(iter2.hasNext()){
				 Node node2=(Node)(map.get(iter2.next()));
				 if(!node1.equals(node2)){
					 if(node1.getIndex()<node2.getIndex()){
						 String name=node1.getName()+"-"+node2.getName();
						 int index=this.nodepairlist.size();
						 Nodepair nodepair=new Nodepair(name,index,"",this,node1,node2);
						 this.addNodepair(nodepair);
					 }
				 }
			 }
		 }
	 }
	 public Link findlink(Node nodeA,Node nodeB){
				String name;
			    name = nodeA.getName()+"-"+nodeB.getName();
				return this.getLinklist().get(name);
			}
	 public Node findNode(String name,Layer layer){
		  HashMap<String,Node>map=layer.getNodelist();
          Iterator<String>iter=map.keySet().iterator();
          Node currentnode=null;
          while(iter.hasNext()){
       	    currentnode=(Node)(map.get(iter.next()));
       	   if(currentnode.getName().endsWith(name))break;
          }
		 return currentnode; 
	 }
	 public Nodepair findNodepair(String name){
		 HashMap<String, Nodepair>map=this.getNodepairlist();
         Iterator<String>iter=map.keySet().iterator();
         Nodepair currentnodepair=null;
         while(iter.hasNext()){
      	    currentnodepair=(Nodepair)(map.get(iter.next()));
      	   if(currentnodepair.getName().endsWith(name))break;
         }
		 return currentnodepair; 
	 }
	public void Output_shortpath(){
		 HashMap<String, Nodepair>map=this.getNodepairlist();
         Iterator<String>iter=map.keySet().iterator();
         Nodepair currentnodepair=null;
         while(iter.hasNext()){
        	currentnodepair=(Nodepair)(map.get(iter.next()));
        	System.out.println("===================="+currentnodepair.getName()+"=============");
       	    LinearRoute newroute= currentnodepair.getLinearlist().get(currentnodepair.getLinearlist().size()-1);
       	    newroute.OutputRoute_node(newroute);
           }
	}
     public void Output_shortpath(String write_name ){
    		 HashMap<String, Nodepair>map=this.getNodepairlist();
             Iterator<String>iter=map.keySet().iterator();
             Nodepair currentnodepair=null;
             file_out_put file=new file_out_put();
             while(iter.hasNext()){
            	currentnodepair=(Nodepair)(map.get(iter.next()));
            	System.out.println("===================="+currentnodepair.getName()+"=============");
            	file.filewrite(write_name,"===================="+currentnodepair.getName()+"=============");
           	    LinearRoute newroute= currentnodepair.getLinearlist().get(currentnodepair.getLinearlist().size()-1);
           	    newroute.OutputRoute_node(newroute,write_name);
               }
             
	}
	public void clear_cost(){
		HashMap<String,Node>map=this.getNodelist();
        Iterator<String>iter=map.keySet().iterator();
        while(iter.hasNext()){
       	    Node node=(Node)(map.get(iter.next()));
			node.setCost_from_src(Constant.maxium);
		}
	}
	public void Output_link(String write_name){
		 HashMap<String,Link>map=this.getLinklist();
         Iterator<String>iter=map.keySet().iterator();
         file_out_put file=new file_out_put();
         while(iter.hasNext()){
        	 Link link=(Link)(map.get(iter.next()));
        	 if(link.getNodeA().getIndex()<link.getNodeB().getIndex()){
        	 System.out.println(link.getName()+"_"+link.getCost());
        	 file.filewrite(write_name,link.getName()+"_"+link.getCost());
        	 }
         }

	}
	public void Output_link_W(){
		 HashMap<String,Link>map=this.getLinklist();
         Iterator<String>iter=map.keySet().iterator();
        
         while(iter.hasNext()){
        	 Link link=(Link)(map.get(iter.next()));
        	 if(link.getNodeA().getIndex()<link.getNodeB().getIndex()){
        	 System.out.println(link.getName()+"_"+link.getW());
        	
        	 }
         }
	}
	public void Output_W(){
		HashMap<String,Link>map=this.getLinklist();
        Iterator<String>iter=map.keySet().iterator();
        while(iter.hasNext()){
       	Link link=(Link)(map.get(iter.next()));
       	if(link.getNodeA().getIndex()<link.getNodeB().getIndex()){
       		System.out.println(link.getName());
	        link.Output_W_Ocppuy();
       	 }
        }
	}
   public void readTopology(String filename){
		
		String[] data = new String[10];
		File file = new File(filename);
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null;
		int col = 0;
		try {        
			line = bufRdr.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//read the first title line
		//read each line of text file
		try {
			boolean link = false;
			while((line = bufRdr.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line,",");
				while (st.hasMoreTokens()){
					data[col] = st.nextToken();
					col++;
					
				}
				col=0;
				String name = data[0];
				if(name.equals("Link")){
					link=true;						
				}
				if(!link)//node operation
				{
					int x = Integer.parseInt(data[1]);
					int y = Integer.parseInt(data[2]);					
					int index = this.getNodelist().size();
					Node newnode = new Node(name, index, "", this,x,y);
					this.addNode(newnode);
				
				}
				else{ //link operation
					if(!(name.equals("Link"))){
						Node nodeA = this.getNodelist().get(data[1]);						
						Node nodeB = this.getNodelist().get(data[2]);
						double length = Double.parseDouble(data[3]);
						double cost = Double.parseDouble(data[4]);
						int index = this.getLinklist().size();
						/*if(nodeA.getIndex()<nodeB.getIndex()){
							name = nodeA.getName()+"-"+nodeB.getName();
						}
						else{
							name = nodeB.getName()+"-"+nodeA.getName();
						}
						*/
						String name1=nodeA.getName();
						String name2=nodeB.getName();
						Link name1_link=new Link(name1+"-"+name2,index,"",this,this.getNodelist().get(name1),this.getNodelist().get(name2),length, cost);
						this.addlink(name1_link);
						Link name2_link=new Link(name2+"-"+name1,++index,"",this,this.getNodelist().get(name2),this.getNodelist().get(name1),length, cost);
					    this.addlink(name2_link);
						
						
						//update the neighbor node list
						nodeA.addNeiNode(nodeB);
						nodeB.addNeiNode(nodeA);
					}					
				}				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			bufRdr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
