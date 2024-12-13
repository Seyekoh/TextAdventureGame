package edu.westga.cs3211.text_adventure_game.model;

import java.util.HashMap;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

/**
 * The hazard manager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class HazardManager {
	
	private Map<HazardType, HazardData> hazardDataMap;
	
	/**
	 * Creates a new HazardManager object
	 */
	public HazardManager() {
		this.hazardDataMap = new HashMap<>();
		
		this.initializeHazardData();
	}
	
	private void initializeHazardData() {
		for (HazardType type : HazardType.values()) {
			switch (type) {
				case GHOSTLYAPPARITION:
					this.hazardDataMap.put(type, new HazardData(5, "A ghastly apparition appears and attacks you!"));
					break;
				case CURSEDSTOVE:
					this.hazardDataMap.put(type, new HazardData(7, "The stove is cursed and burns you!"));
					break;
				case ROTTINGCORPSE:
					this.hazardDataMap.put(type, new HazardData(100, "You are attacked by a rotting corpse!"));
					break;
				case CREEPYDOLL:
					this.hazardDataMap.put(type, new HazardData(3, "A creepy doll attacks you!"));
					break;
				case DANCINGSHADOWS:
					this.hazardDataMap.put(type, new HazardData(10, "The shadows attack you!"));
					break;
				default:
					this.hazardDataMap.put(type, new HazardData(0, "This area seems safe."));
					break;
			}
		}
	}
	
	/**
	 * Gets the hazard data for a hazard type
	 * 
	 * @param type	the hazard type to get the data for
	 * 
	 * @return		the hazard data for the hazard type
	 */
	public HazardData getHazardData(HazardType type) {
		return this.hazardDataMap.get(type);
	}
	
	/**
	 * Gets the hazard type from the hazard data
	 * 
	 * @param hazardData the hazard data to get the type for
	 * 
	 * @return the hazard type for the hazard data
	 */
	public HazardType getHazardTypeFromHazardData(HazardData hazardData) {
		for (Map.Entry<HazardType, HazardData> entry : this.hazardDataMap.entrySet()) {
			if (entry.getValue().equals(hazardData)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
