package Main;

public class radom_method {

	public static void main(String[] args) {
     int[] demand;
     demand=Num_random(19,249);
     for(int n=0;n<demand.length;n++){
    	 System.out.println(demand[n]+1);
     }
     
		
	}
	public static int[] Num_random(int number,int bound_number){
		int[] arr = new int[number]; 
		for (int i = 0; i < number; i++){              
			arr[i] = (int) (Math.random()*bound_number) ;   
		}
		return arr;
	}

}
