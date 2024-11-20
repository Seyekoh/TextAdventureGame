package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

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
		if (action.getName().equals(ActionType.MOVE.toString())) {
			this.movePlayer(Direction.valueOf(action.getDescription()));
		}
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
		}
		
		if (!this.checkIfPlayerIsAlive()) {
			this.onGameOverLose(nextLocation);
		}
		
		if (this.world.checkIfLocationIsGoal(nextLocation)) {
			this.currentLocation = nextLocation;
			this.isGameOverWin = true;
			this.onGameOverWin(nextLocation);
		}
		
		this.currentLocation = nextLocation;
	}
	
	private void onGameOverLose(Location newLocation) {
		this.currentLocation = newLocation;
		this.isGameOverLose = true;
		this.setGameOverMessage();
	}
	
	private void onGameOverWin(Location newLocation) {
        this.currentLocation = newLocation;
        this.isGameOverWin = true;
        this.setGameOverMessage();
	}
	
	private void setGameOverMessage() {
		if (this.isGameOverWin) {
			this.gameOverMessage = "You have made it to the " + this.currentLocation.getName()
					+ " alive. Your journey is complete and you have won the game. Rest well adventurer.";
		}
		
		if (this.isGameOverLose) {
			this.gameOverMessage = "You have died in the " + this.currentLocation.getName()
					+ " due to " + this.getDamageMessage() 
					+ ". Your journey is over and you have lost the game. Better luck next time.";
		}
	}
	
	/**
	 * Gets the message for the cause of damage
	 * 
	 * @return	the message for the cause of damage
	 */
	public String getDamageMessage() {
		HazardType causeOfDamage = this.currentLocation.getHazardType();
		
		switch (causeOfDamage) {
			case PIT:
				return "falling into a pit";
			default:
				return "unknown causes";
		}
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
}
