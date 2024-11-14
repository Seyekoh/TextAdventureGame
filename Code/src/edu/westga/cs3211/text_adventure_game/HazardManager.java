package edu.westga.cs3211.text_adventure_game;

import java.util.HashMap;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.GlobalEnums.HazardType;

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
				case PIT:
					this.hazardDataMap.put(type, new HazardData(80, "A deep, dark pit with no visible bottom."));
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
}
