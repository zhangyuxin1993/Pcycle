package General;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import Network.Layer;
import Network.Link;
public class random {
    public void random_link_cost(Layer layer){
    	HashMap<String,Link>map=layer.getLinklist();
        Iterator<String>iter=map.keySet().iterator();
        while(iter.hasNext()){
        Link link=(Link)(map.get(iter.next()));
        double length=(double)((int)(Math.random()*100)/100);
	    double cost=length;	
        link.setLength(length);
        link.setCost(cost);
        link.setInitialcost(cost);
        }//0-1֮������������� 
    }  
    public double exponential_random_distribution(int Lamda){
    	Random rand=new Random();
    	double p=rand.nextDouble();
    	double ele=Math.log(p);
    	System.out.println(p);
    	double result=(-ele)/Lamda;
		return result;
    	
    }
    //��0--��bound_number-1��֮�����ɸ�����ͬ����
    public int[] Dif_random(int number,int bound_number){
    	int[] arr = new int[number];           
		for (int i = 0; i < number; i++){              
			arr[i] = (int) (Math.random()*bound_number) ;              
			for (int j = 0; j < i; j++)              
			{                  
				if (arr[j] == arr[i])                  
				{                      
					i--;                      
					break;                  
					}              
				}          
			}          
    	return arr;
    }
    //��0-��number-1��֮��������
    public int[] Num_random(int number,int bound_number){
    	int[] arr = new int[number]; 
    	for (int i = 0; i < number; i++){              
			arr[i] = (int) (Math.random()*bound_number) ;   
    	}
    	return arr;
    }
}
