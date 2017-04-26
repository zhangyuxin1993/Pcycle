package Network;

import General.CommonObject;

public class Regen extends CommonObject{

	private Node associatedNode=null;
	private double regenDataRate=0;
	
	
   /**
    * associated node 
    * @return
    */
	public Node getAssociatedNode() {
		return associatedNode;
	}

	public void setAssociatedNode(Node associatedNode) {
		this.associatedNode = associatedNode;
	}

	/**
	 * regen data rate 
	 * @return
	 */
	public double getRegenDataRate() {
		return regenDataRate;
	}

	public void setRegenDataRate(double regenDataRate) {
		this.regenDataRate = regenDataRate;
	}

	public Regen(String name, int index, String comments, Node associatedNode, double DataRate) {
		super(name, index, comments);
		// TODO Auto-generated constructor stub
		this.associatedNode=associatedNode;
		this.regenDataRate=DataRate;
	}

}
