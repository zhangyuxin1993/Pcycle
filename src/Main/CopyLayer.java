package Main;

import java.util.HashMap;
import java.util.Iterator;

import Network.Layer;
import Network.Link;
import Network.Node;

public class CopyLayer {
	/*
	 * 函数主要实现了将layer的节点特性以及链路特复制到一个新的层里
	 * 为了不把各个节点的相关信息复制进去要通过新建每个节点来复制
	 */
	public  void copylayer(Layer layer,Layer newlayer)
	{
		//将node全部复制到新的layer里面
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {	
			Node node = (Node) (map.get(iter.next()));
			
			String name = node.getName();
			int index = node.getIndex();
			Node newnode = new Node(name, index, "", newlayer,0,0);
			newlayer.addNode(newnode);
		
		}
		
		//将link复制到layer里面
	
		
		HashMap<String, Link> mapLink = layer.getLinklist();
		Iterator<String> iterLink = mapLink.keySet().iterator();
		while (iterLink.hasNext()) {
			Link link = (Link) (mapLink.get(iterLink.next()));
			Node nodeSrc = newlayer.getNodelist().get(link.getNodeA().getName());
			Node nodeDes = newlayer.getNodelist().get(link.getNodeB().getName());
			nodeSrc.setIndex(link.getNodeA().getIndex());
			nodeDes.setIndex(link.getNodeB().getIndex());
			int index = newlayer.getLinklist().size();
			String name = null;
			Link newlink = null;
			name = nodeSrc.getName() + "-" + nodeDes.getName();
			
			newlink=new Link(name, index, "", newlayer, nodeSrc,nodeDes, link.getLength(), 0);
//					new Link(name, index, "", newlayer, nodeDes, nodeSrc, link.getLength(), cost);
			newlayer.addlink(newlink);
			if (nodeSrc.getIndex() < nodeDes.getIndex()) {
			nodeSrc.addNeiNode(nodeDes);
			nodeDes.addNeiNode(nodeSrc);
			}
		}
		

		
		
		
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}



