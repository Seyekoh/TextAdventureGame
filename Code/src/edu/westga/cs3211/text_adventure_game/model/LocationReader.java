package edu.westga.cs3211.text_adventure_game.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * The location reader
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class LocationReader {
	private String locationPath;

	/**
	 * Instantiates a new location reader.
	 * @precondition !locationPath.isBlank() && locationPath != null
	 * @postcondition path is instantiated.
	 *
	 * @param locationPath the location path
	 */
	public LocationReader(String locationPath) {
		if (locationPath == null) {
			throw new IllegalArgumentException("locationPath cannot be null");
		}
		if (locationPath.isBlank()) {
			throw new IllegalArgumentException("locationPath cannot be empty.");
		}
		
		this.locationPath = locationPath;
	}
	
	/**
	 * Import locations.
	 *
	 * @return the array list
	 */
	public ArrayList<Location> importLocations() {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(this.locationPath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				try {
					Location location = this.readLocation(line);
					locations.add(location);
				} catch (Exception exception) {
					System.out.println("Line could not be parsed: " + line + exception.getMessage());
				}
			}
		} catch (IOException exception) {
			System.out.println("An error occurred while reading the file." + exception.getMessage());
		}
		
		return locations;
	}
	
	private Location readLocation(String line) {
		String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		if (fields.length != 5) {
			throw new IllegalArgumentException("Line must have 5 fields");
		}
		
        GlobalEnums.LocationName name = GlobalEnums.LocationName.valueOf(fields[0].trim().toUpperCase());
        String description = fields[1].replaceAll("^\"|\"$", "").trim();
        HazardType hazard = HazardType.valueOf(fields[2].trim());
        Boolean isGoal = Boolean.parseBoolean(fields[3].trim());
        Item item = Item.valueOf(fields[4].trim());
                
        return new Location(name, description, hazard, isGoal, new ArrayList<Action>(), item);
	}

}
