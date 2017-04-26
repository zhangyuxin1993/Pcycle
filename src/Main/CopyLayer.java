package Main;

import java.util.HashMap;
import java.util.Iterator;

import Network.Layer;
import Network.Link;
import Network.Node;

public class CopyLayer {
	/*
	 * ������Ҫʵ���˽�layer�Ľڵ������Լ���·�ظ��Ƶ�һ���µĲ���
	 * Ϊ�˲��Ѹ����ڵ�������Ϣ���ƽ�ȥҪͨ���½�ÿ���ڵ�������
	 */
	public  void copylayer(Layer layer,Layer newlayer)
	{
		//��nodeȫ�����Ƶ��µ�layer����
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {	
			Node node = (Node) (map.get(iter.next()));
			
			String name = node.getName();
			int index = node.getIndex();
			Node newnode = new Node(name, index, "", newlayer,0,0);
			newlayer.addNode(newnode);
		
		}
		
		//��link���Ƶ�layer����
	
		
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



