
public class Trip {

	private int startTime;
	private int endTime;
	private int travelTime;
	private int elapsedTime;
	private int numOfInstances;
	
	public Trip(String startTime, String endTime, String travelTime, String elapsedTime) {
		
		this.startTime = Integer.parseInt(startTime);
		this.endTime = Integer.parseInt(endTime);
		this.travelTime = Integer.parseInt(travelTime);
		this.elapsedTime = Integer.parseInt(elapsedTime);
		this.numOfInstances = 1;
		
	}
	
	public void incNumOfInstances () {this.numOfInstances++;}
	
	public boolean checkTripEqual(Trip trip) {
		
		if(this.startTime == trip.startTime)
			if(this.endTime == trip.endTime)
				if(this.travelTime == trip.travelTime)
					if(this.elapsedTime == trip.elapsedTime)
						return true;
		return false;
	}
	public int getStartTime() {return startTime;}
	public int getEndTime() {return endTime;}
	public int getTravelTime() {return travelTime;}
	public int getElapsedTime() {return elapsedTime;}
	public int getNumOfInstances() {return numOfInstances;}
	
	public String toString() {
		
		return startTime+","+endTime+","+travelTime+","+elapsedTime+"\n";
	}
}
