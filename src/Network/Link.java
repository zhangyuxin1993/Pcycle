package Network;

import java.util.ArrayList;

import General.CommonObject;
import General.Constant;
import General.Cwavelength;

public class Link extends CommonObject{
    private  Layer   associatedLayer=null;
    private  Node    nodeA=null;
    private  Node    nodeB=null;
    private  double  length=0;
    private  double  cost=0;
    private  double  workhop=0;
    private  double  initialcost=0; 
	private  int     status=Constant.UNVISITED; 
	private  double     routevisited=0;
    private  int     w=0;
    private  ArrayList<Cwavelength>WC=null;
   
    
    private  double[] SetUpRate=null;
    private  double freeProbability=0.9;
    private  double[] CapacityDistribution=null;
    
	
 
	
	public double[] getCapacityDistribution() {
		return CapacityDistribution;
	}
	public void setCapacityDistribution(double[] capacityDistribution) {
		CapacityDistribution = capacityDistribution;
	}
	public double getFreeProbability() {
		return freeProbability;
	}
	public void setFreeProbability(double freeProbability) {
		this.freeProbability = freeProbability;
	}
	
	public double[] getSetUpRate() {
		return SetUpRate;
	}
	public void setSetUpRate(double[] setUpRate) {
		SetUpRate = setUpRate;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public ArrayList<Cwavelength> getWC() {
		return WC;
	}
	public void setWC(ArrayList<Cwavelength> wc) {
		WC = wc;
	}
	public double getRoutevisited() {
		return routevisited;
	}
	public void setRoutevisited(double  routevisited) {
		this.routevisited = routevisited;
	}
	public double getWorkhop() {
		return workhop;
	}
	public void setWorkhop(double workhop) {
		this.workhop = workhop;
	}
	public double getInitialcost() {
		return initialcost;
	}
	public void setInitialcost(double initialcost) {
		this.initialcost = initialcost;
	}
	public Layer getAssociatedLayer() {
		return associatedLayer;
	}
	public void setAssociatedLayer(Layer associatedLayer) {
		this.associatedLayer = associatedLayer;
	}
	public Node getNodeA() {
		return nodeA;
	}
	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}
	public Node getNodeB() {
		return nodeB;
	}
	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	 public double getCost() {
			return cost;
	}
		public void setCost(double cost) {
			this.cost = cost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

	public Link(String name, int index, String comments,Layer associatedLayer,Node nodeA,Node nodeB,double length,double cost) {
		super(name,index,comments);
		// TODO Auto-generated constructor stub
		this.associatedLayer=associatedLayer;
		this.nodeA=nodeA;
		this.nodeB=nodeB;
		this.length=length;
	    this.cost=cost;
	    this.initialcost=cost;
	    this.WC=new ArrayList<Cwavelength>();
	}

	public void clear_routevisited(){
		this.routevisited=0;
	}
	public void  add_W(int number){
		for(int i_number=0;i_number< number;i_number++){
			Cwavelength W_n=new Cwavelength();
			//¼ÇÂ¼±êºÅ
			W_n.setVaule(i_number);//add
			this.WC.add(W_n);
		}
	}
	public void Output_W_Ocppuy(){
		for(int i=0;i<this.WC.size();i++){
			System.out.print(this.WC.get(i).getStatus());
			
		}
		System.out.println();
	}
	
	public int Free_index(){
		int Free_index=Constant.maxium;
		for(int i=0;i<this.WC.size();i++){
			if(this.WC.get(i).equals(0)){
				Free_index=i;
				break;
			}
		}
		return Free_index;
		
	}
	public void Init_W(){
		for(int i=0;i<this.WC.size();i++){
			this.WC.get(i).setStatus(0);
		}
	}
	

}
