package Subgraph;
import java.util.ArrayList;
import Network.*;
import General.CommonObject;

public class Subgraph extends CommonObject{
    private ArrayList<Node>nodelist=null;
    private ArrayList<Link>linklist=null;
    private ArrayList<CommonObject> objectlist =null;//object list
    private ArrayList<CommonObject>objetc=null;
    private ArrayList<Subgraph>routelist=null;
	public Subgraph(String name, int index, String comments) {
		super(name, index, comments);
		// TODO Auto-generated constructor stub
       this.nodelist=new ArrayList<Node>();
       this.linklist=new ArrayList<Link>();
       this.objetc=new ArrayList<CommonObject>();
       this.routelist=new ArrayList<Subgraph>();
       this.objectlist = new ArrayList<CommonObject>();
	}
	public ArrayList<Node> getNodelist() {
		return nodelist;
	}
	public void setNodelist(ArrayList<Node> nodelist) {
		this.nodelist = nodelist;
	}
	public ArrayList<Link> getLinklist() {
		return linklist;
	}
	public void setLinklist(ArrayList<Link> linklist) {
		this.linklist = linklist;
	}
	public ArrayList<CommonObject> getObjetc() {
		return objetc;
	}
	public ArrayList<CommonObject> getObjectlist() {
		return objectlist;
	}
	public void setObjetc(ArrayList<CommonObject> objetc) {
		this.objetc = objetc;
	}
	public ArrayList<Subgraph> getRoutelist() {
		return routelist;
	}
	public void setRoutelist(ArrayList<Subgraph> routelist) {
		this.routelist = routelist;
	}
	public double getlength(){
		double sum=0;
		for(Link link:this.getLinklist()){
			sum+=link.getLength();
		}
		return sum;
	}
	public void ConvertfromNodeListtoLinkandObjectList(){
		this.getLinklist().clear();
		this.getObjectlist().clear();
		for(Node node : this.getNodelist()){
			this.getObjectlist().add(node);
			this.getObjectlist().add(node);
			int next_node_index = this.getNodelist().indexOf(node)+1;
			if(next_node_index<this.getNodelist().size()){
				Node next_node = this.getNodelist().get(next_node_index);			
				if(next_node!=null){
					Link link = node.getAssociatedLayer().findlink(node, next_node);				
					this.getLinklist().add(link);
					this.getObjectlist().add(link);
				}
			}
					
		}
	}
	/**
	 * get the length of subgraph
	 */
	public double getLength(){
		double sum =0;
		for(Link link : this.getLinklist()){
			sum += link.getLength();
			
		}
		return sum;
	}
	
	/**
	 * get the cost of subgraph
	 */
	public double getCost(){
		double sum =0;
		for(Link link : this.getLinklist()){
			sum += link.getCost();
			
		}
		return sum;
	}
	
}
