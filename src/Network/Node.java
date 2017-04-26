package Network;

import java.util.ArrayList;

import General.*;

public class Node extends CommonObject{
	        private Layer associatedLayer=null;
          private ArrayList<Node>neinodelist=null;
          private double cost_from_src=Constant.maxium;
          private int hop_from_src=Constant.maxium;
          private int status=Constant.UNVISITED;
          private Node parentnode=null;
          private  int x;
          private  int y;
          
          /**
           *   �м̳ؽڵ�
           */
          private ArrayList<Regen>RegenList=null;
          private int RegenStatus=0;
          
          
          
          
		public ArrayList<Regen> getRegenList() {
			return RegenList;
		}
		public void setRegenList(ArrayList<Regen> regenList) {
			RegenList = regenList;
		}
		public int getRegenStatus() {
			return RegenStatus;
		}
		public void setRegenStatus(int regenStatus) {
			RegenStatus = regenStatus;
		}
		
		
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
		public Node(String name,int index,String comments,Layer associatedLayer,int x,int y){
        	     super(name,index,comments);
        	     this.associatedLayer=associatedLayer;
        	     this.neinodelist = new ArrayList<Node>();
        	     this.x =x;
        	     this.y=y;
        	     
         }
		public Layer getAssociatedLayer() {
			return associatedLayer;
		}
		public void setAssociatedLayer(Layer associatedLayer) {
			this.associatedLayer = associatedLayer;
		}
		public ArrayList<Node> getNeinodelist() {
			return neinodelist;
		}
		public void setNeinodelist(ArrayList<Node> neinodelist) {
			this.neinodelist = neinodelist;
		}
		public double getCost_from_src() {
			return cost_from_src;
		}
		public void setCost_from_src(double cost_from_src) {
			this.cost_from_src = cost_from_src;
		}
		public int getHop_from_src() {
			return hop_from_src;
		}
		public void setHop_from_src(int hop_from_src) {
			this.hop_from_src = hop_from_src;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public Node getParentnode() {
			return parentnode;
		}
		public void setParentnode(Node parentnode) {
			this.parentnode = parentnode;
		}
		
		/*
		 * add node to neinode
		 */
		public void addNeiNode(Node node){
			this.neinodelist.add(node);
		}
		/*
		 * remove node from neinode by index
		 */
		public void removeNeiNode(int index){
			for(int i=0;i<this.neinodelist.size();i++){
				if(this.neinodelist.get(i).getIndex()==index){
					this.neinodelist.remove(this.neinodelist.get(i));
				    break;
				}
			}
		}
		/*
		 * remove node from neinode by name
		 */
		public void removeNeiNode(String name){
			for(int i=0;i<this.neinodelist.size();i++){
				if(this.neinodelist.get(i).getName().equals(name)){
					this.neinodelist.remove(this.neinodelist.get(i));
		            break;
				}
			}
		}
		public void removeNeiNode(Node node){
			this.neinodelist.remove(node);
		}
		public int getDegree(){
			return this.neinodelist.size();
		}
		

}
