package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;

/**
 * The game manager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GameManager {
	private World world;
	private WorldManager worldManager;
	private Player player;
	private Location currentLocation;
	private boolean isGameOverLose = false;
	private boolean isGameOverWin = false;
	private String gameOverMessage;
	private String interactionInfo;
	
	/**
	 * Constructor for the GameManager
	 */
	public GameManager() {
		this.worldManager = new WorldManager();
		this.world = this.worldManager.getWorld();
		this.player = new Player();
		this.currentLocation = this.world.getStartLocation();
	}
	
	/**
	 * Gets the current location
	 * 
	 * @return the current location
	 */
	public Location getCurrentLocation() {
		return this.currentLocation;
	}
	
	/**
	 * Gets the player
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Gets the game over message
	 * 
	 * @return the game over message
	 */
	public String getGameOverMessage() {
		return this.gameOverMessage;
	}
	
	/**
	 * Checks if the game is over
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean checkIfGameOver() {
		return this.isGameOverLose || this.isGameOverWin;
	}
	
	/**
	 * Performs the given action
	 * 
	 * @param action	the action to perform
	 */
	public void performAction(Action action) {
		switch (action.getType()) {
		case MOVE:
			this.movePlayer(Direction.valueOf(action.getDescription()));
			break;
		case TALK:
			this.talkToNPC();
			break;
		default:
			throw new IllegalArgumentException("Unknown action type." + action.getType());
		}
	}

	private void talkToNPC() {
		NPC npc = this.world.getNPC();
		Location attic = this.world.getLocationByName(LocationName.ATTIC);
		Location entrance = this.world.getLocationByName(LocationName.ENTRANCEHALL);
		
		this.setInteractionInfo(npc.getDialogue());
		
		this.world.moveNPCToLocation(npc, entrance);
		attic.removeNPC();
		
		npc.setDialogue("The dialogue after moving to entrance hall.");
		npc.setDescription("The ghost from before is staring at you.");
	}
	
	/**
	 * Moves the player in a given direction
	 * 
	 * @param direction		the direction to move the player
	 */
	public void movePlayer(Direction direction) {
		Location nextLocation = this.currentLocation.getConnection(direction);
		if (nextLocation == null) {
			throw new IllegalArgumentException("There is no location in that direction.");
		}
		
		if (this.world.checkIfLocationIsHazard(nextLocation)) {
			int damage = this.world.getHazardDataForLocation(nextLocation).getDamage();
			this.player.takeDamage(damage);
			this.setInteractionInfo(this.world.getHazardDataForLocation(nextLocation), damage);
		}
		
		if (!this.checkIfPlayerIsAlive()) {
			this.setInteractionInfo(this.world.getHazardDataForLocation(nextLocation), this.world.getHazardDataForLocation(nextLocation).getDamage());
			this.onGameOverLose(nextLocation);
		}
		
		if (this.world.checkIfLocationIsGoal(nextLocation)) {
			this.currentLocation = nextLocation;
			this.isGameOverWin = true;
			this.onGameOverWin(nextLocation);
		}
		
		this.currentLocation = nextLocation;
	}
	
	private void setInteractionInfo(HazardData hazardData, int damage) {
		this.interactionInfo = "You have taken " + damage + " damage due to: " + System.lineSeparator()
								+ "\t" + hazardData.getDescription();
	}
	
	private void setInteractionInfo(String interactionInfo) {
		this.interactionInfo = interactionInfo;
	}
	
	/**
	 * Gets the interaction info
	 * @return the interaction info
	 */
	public String getInteractionInfo() {
		return this.interactionInfo;
	}	
	
	private void onGameOverLose(Location newLocation) {
		this.currentLocation = newLocation;
		this.isGameOverLose = true;
		this.setGameOverMessage();
		this.setInteractionInfo(this.gameOverMessage);
	}
	
	private void onGameOverWin(Location newLocation) {
        this.currentLocation = newLocation;
        this.isGameOverWin = true;
        this.setGameOverMessage();
        this.setInteractionInfo(this.gameOverMessage);
	}
	
	private void setGameOverMessage() {
		if (this.isGameOverWin) {
			this.gameOverMessage = "You have made it to the " + this.currentLocation.getName()
					+ " alive. Your journey is complete and you have won the game. Rest well adventurer.";
		}
		
		if (this.isGameOverLose) {
			this.gameOverMessage = this.getDamageMessage() + " You have taken "
					+ this.world.getHazardDataForLocation(this.currentLocation).getDamage() + " damage. "
					+ "You have died in the " + this.currentLocation.getName()
					+ ". Your journey is over and you have lost the game. Better luck next time.";
		}
	}
	
	/**
	 * Gets the message for the cause of damage
	 * 
	 * @return	the message for the cause of damage
	 */
	public String getDamageMessage() {
		return this.world.getHazardDataForLocation(this.currentLocation).getDescription();
	}
	
	private boolean checkIfPlayerIsAlive() {
		return this.player.getHealth() > 0;
	}
	
	/**
	 * Gets all available actions from the current Location and the player
	 * 
	 * !!! This method does not have full functionality yet !!!
	 * !!! More will be added when player actions are implemented !!!
	 * 
	 * @return the available actions
	 */
	public ArrayList<Action> getAllAvailableActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.addAll(this.currentLocation.getActions());
		return actions;
	}
	
	/**
	 * Gets the player's inventory
	 * @return the player's inventory
	 */
	public List<String> getPlayerInventory() {
		return this.player.getInventory();
	}
}
