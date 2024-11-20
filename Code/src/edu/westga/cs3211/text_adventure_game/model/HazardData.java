package edu.westga.cs3211.text_adventure_game.model;

/**
 * Class to hold the data for a hazard
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class HazardData {
	
	private int damage;
	private String description;
	
	/**
	 * Creates a new HazardData object
	 * 
	 * @param damage		the damage the hazard does
	 * @param description	the description of the hazard
	 */
	public HazardData(int damage, String description) {
		if (damage < 0) {
			throw new IllegalArgumentException("Damage cannot be negative");
		}
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be blank");
		}
		
		this.damage = damage;
		this.description = description;
	}
	
	/**
	 * Gets the damage the hazard does
	 * 
	 * @return the damage the hazard does
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * Gets the description of the hazard
	 * 
	 * @return the description of the hazard
	 */
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String toString() {
		return this.getDamage() + " " + this.getDescription();
	}
}
