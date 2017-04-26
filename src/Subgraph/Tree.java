package Subgraph;

import General.Constant;

public class Tree extends Subgraph {
	private int type = Constant.WORKING;//default status of the tree is working

	public Tree(String name, int index, String comments, int type) {
		super(name, index, comments);
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
