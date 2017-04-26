package General;

import Subgraph.LinearRoute;

public class Time {
	   String name;
	   double time;
	   int keytime;
	   LinearRoute route;
	   int    route_index;
	   int    Occpy_W=Constant.maxium;
	public Time(String name,double time,int keytime, LinearRoute route){
			// TODO Auto-generated constructor stub
        this.name=name;
        this.time=time;
        this.keytime=keytime;
        this.route=route;
       
		}
	
	 public int getOccpy_W() {
		return Occpy_W;
	}

	public void setOccpy_W(int occpy_W) {
		Occpy_W = occpy_W;
	}

	public int getRoute_index() {
			return route_index;
		}

		public void setRoute_index(int route_index) {
			this.route_index = route_index;
		}

	public LinearRoute getRoute() {
		return route;
	}



	public void setRoute(LinearRoute route) {
		this.route = route;
	}



	public int getKeytime() {
		return keytime;
	}



	public void setKeytime(int keytime) {
		this.keytime = keytime;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	
	
}
