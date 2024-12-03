package edu.westga.cs3211.text_adventure_game.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

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
					System.out.println("Line could not be parsed: " + line);
				}
			}
		} catch (IOException exception) {
			System.out.println("An error occurred while reading the file.");
		}
		
		return locations;
	}
	
	private Location readLocation(String line) {
		String[] fields = line.split(",");
        String name = fields[0];
        String description = fields[1];
        HazardType hazard = HazardType.valueOf(fields[2]);
        Boolean isGoal = Boolean.parseBoolean(fields[3]);
        
        return new Location(name, description, hazard, isGoal, new ArrayList<Action>());
	}

}
