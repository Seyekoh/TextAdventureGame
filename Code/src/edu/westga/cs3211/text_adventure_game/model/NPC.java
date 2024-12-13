package edu.westga.cs3211.text_adventure_game.model;

/**
 * An NPC in the game
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class NPC {
	private static final String INITIAL_DIALOGUE = "The ghost gives you a cold stare, seemingly looking through you. "
			+ "As you're about to step away, the ghost speaks to you." + System.lineSeparator() + System.lineSeparator()
			+ "Ghost: I take it you wish to escape, yes? There is something I am missing, something I am looking for. Find it, and I shall set you free. I will be waiting for you by the entrance."
			+ System.lineSeparator() + System.lineSeparator()
			+ "Before you can say anything, the ghost fades away, leaving you alone.";
	private String description;
	private String dialogue;

	/**
	 * Creates a new NPC object
	 * 
	 * @param description the NPC description
	 */
	public NPC(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be blank");
		}

		this.description = description;
		this.dialogue = INITIAL_DIALOGUE;
	}

	/**
	 * Gets the npc's description
	 * 
	 * @return the npc's description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the npc's description
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be blank");
		}

		this.description = description;
	}

	/**
	 * Gets the npc's dialogue
	 * 
	 * @return the npc's dialogue
	 */
	public String getDialogue() {
		return this.dialogue;
	}

	/**
	 * Sets the npc's dialogue
	 * 
	 * @param dialogue the dialogue to set
	 */
	public void setDialogue(String dialogue) {
		if (dialogue == null) {
			throw new IllegalArgumentException("Dialogue cannot be null");
		}
		if (dialogue.isBlank()) {
			throw new IllegalArgumentException("Dialogue cannot be blank");
		}

		this.dialogue = dialogue;
	}
}
