import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private final static String HOME_PATH = "C:\\Users\\persnal\\Desktop\\DATA61";
	private final static String CENSUS = "LGA_names.csv";
	private static LGACensus censusObj;
	private static List<String> fileNames;
	private static Map<String, List<Trip>> lgaTripsMap;

	public static void mapLGANumber(String args[]) {

		System.out.println(args[0]);
		System.out.println(HOME_PATH);
		if (args.length == 1) {

			if (args[0].equals(HOME_PATH)) {

				try {
					BufferedReader bf = new BufferedReader(new FileReader(HOME_PATH +"\\"+CENSUS));

					String line;

					while ((line = bf.readLine()) != null) {
						System.out.println(line);
						censusObj.addLGANumber(line.split(",")[0], line.split(",")[1]);
					}
					bf.close();

				} catch (FileNotFoundException e) {

					e.printStackTrace();

				} catch (IOException e) {

					e.printStackTrace();
				}

			} else
				System.err.println("Incorrect path name");

		} else
			System.err.println("Incorrect number of arguments");

	}

	public static void retrieveFileList() {

		fileNames = new ArrayList<String>();
		String fileName;

		for (String eachLGA : censusObj.getCensus().keySet()) {
			fileName = "";
			fileName = "Darebin (C)-" + eachLGA + ".csv";
			fileNames.add(fileName);
		}

	}

	public static List<Trip> createLGATripList(String file) {

		List<Trip> trips = new ArrayList<Trip>();
		boolean duplicateFound = false;

		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));

			String line;

			while ((line = bf.readLine()) != null) {

				Trip trip = new Trip(line.split(",")[5], line.split(",")[8], line.split(",")[11], line.split(",")[12]);

				for (Trip eachTrip : trips) {
					if (eachTrip.checkTripEqual(trip)) {
						eachTrip.incNumOfInstances();
						duplicateFound = true;
						break;
					}
				}

				if (duplicateFound == false)
					trips.add(trip);

				duplicateFound = false;
				// System.out.println(line);
				// censusObj.addLGANumber(line.split(",")[0],line.split(",")[1]);
			}
			bf.close();

		} catch (FileNotFoundException e) {

			// ***************************************
			// handle file not found

		} catch (IOException e) {

			e.printStackTrace();
		}
		return trips;

	}

	public static void generateProportionalTrips() {

		int lgaWorkingPopulation, totalTripCount;
	
		
		for (String lga : lgaTripsMap.keySet()) {
			
			if(censusObj.getCensus().get(lga) != null) {
			lgaWorkingPopulation = censusObj.getCensus().get(lga);

			System.out.println("LGA : "+lga);
			
			try {
				FileWriter writer = new FileWriter(HOME_PATH + "\\"+lga + "_out.txt");
				writer.write("STARTHOUR,ARRHOUR,JTWTravelTime,JTWElapsedTime\n");
				
				totalTripCount = 0;

				for (Trip trip : lgaTripsMap.get(lga)) {

					totalTripCount += trip.getNumOfInstances();
				}

				System.out.println("TOTAL TRIPS : "+totalTripCount);
				
				for (Trip trip : lgaTripsMap.get(lga)) {

					String line = "";
					for (int i = 1; i <= (trip.getNumOfInstances() * lgaWorkingPopulation)/ totalTripCount; i++) {
						line = trip.toString();
						writer.write(line);
					}
				}

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			}
		}

	}

	public static void main(String args[]) {

		censusObj = new LGACensus();

		// handle reading of census file to get the number of people
		mapLGANumber(args);

		// retrieve list of all filenames to be searched
		retrieveFileList();

		// handle reading of individual LGA files to create a listing of all trips
		// within the specific LGA incrementing trip instance variable when the trip
		// parameters are equal
		lgaTripsMap = new HashMap<String, List<Trip>>();

		for (String file : fileNames) {
			
			lgaTripsMap.put(file.split("-")[1].split(".csv")[0], createLGATripList(HOME_PATH + "\\"+ file));

		}

		// create new file from subset with the proportional records created
		generateProportionalTrips();
	}
}
