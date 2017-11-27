import java.util.HashMap;
import java.util.Map;

public class LGACensus {

	private Map<String,Integer> census;
	
	public LGACensus() {
		
		census = new HashMap<String,Integer>(); 
	}
	
	public void addLGANumber(String lga, String number) {
		
		census.put(lga, Integer.parseInt(number));
		
	}
	
	public Map<String,Integer> getCensus(){
		
		return census;
	}
}
