package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
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

	private boolean isDiaryFound = false;
	private boolean isRingOnFinger = false;
	private boolean isDressWorn = false;

	private String gameOverMessage;
	private String interactionInfo;
	private String currentLocationDescription;

	/**
	 * Constructor for the GameManager
	 */
	public GameManager() {
		this.worldManager = new WorldManager();
		this.world = this.worldManager.getWorld();
		this.player = new Player();
		this.currentLocation = this.world.getStartLocation();
		this.currentLocationDescription = this.currentLocation.getDescription();
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
	 * Gets the current location description.
	 * 
	 * @return the current location description.
	 */
	public String getCurrentLocationDescription() {
		return this.currentLocationDescription;
	}

	/**
	 * Sets the current location description.
	 * 
	 * @param description the description to set.
	 */
	public void setCurrentLocationDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}

		this.currentLocationDescription = description;
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
	 * Performs the given action, using items from player inventory if necessary.
	 * 
	 * @param action the action to perform
	 * @param item   the item to use
	 */
	public void performAction(Action action, Item item) {
		switch (action.getType()) {
		case MOVE:
			this.movePlayer(Direction.valueOf(action.getDescription()));
			break;
		case SEARCH:
			this.search();
			break;
		case TAKE:
			this.takeItem(item);
			break;
		case USE:
			this.useItem(item);
			break;
		case DROP:
			this.dropItem(item);
			break;
		case EXAMINE:
			this.examineItem(item);
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
	 * Moves the player in the given direction
	 * 
	 * @param direction the direction to move the player.
	 */
	public void movePlayer(Direction direction) {
		Location nextLocation = this.currentLocation.getConnection(direction);
		if (nextLocation == null) {
			throw new IllegalArgumentException("There is no location in that direction.");
		}

		if (this.world.checkIfLocationIsHazard(nextLocation)) {
			int damage = this.world.getHazardDataForLocation(nextLocation).getDamage();
			this.player.applyDamage(damage);
			this.setInteractionInfo(this.world.getHazardDataForLocation(nextLocation), damage);
		}

		if (!this.checkIfPlayerIsAlive()) {
			this.setInteractionInfo(this.world.getHazardDataForLocation(nextLocation),
					this.world.getHazardDataForLocation(nextLocation).getDamage());
			this.onGameOverLose(nextLocation);
		}

		if (this.world.checkIfLocationIsGoal(nextLocation)) {
			this.currentLocation = nextLocation;
			this.isGameOverWin = true;
			this.onGameOverWin(nextLocation);
		}

		this.currentLocation = nextLocation;
		this.setCurrentLocationDescription(this.currentLocation.getDescription() + "\n\nLocation Items:\n");
		this.updateAvailableActions();
	}

	private void search() {
		if (!this.currentLocation.isSearched()) {
			this.setCurrentLocationDescription(this.currentLocation.getDescription() + "\n\nLocation Items:\n");
			this.setInteractionInfo("You have searched the location and found an item.\n\n");
			this.setInteractionInfo(
					this.interactionInfo + this.currentLocation.getStartingItem().getDescription() + "\n");

			this.currentLocation.setSearched(true);
			if (!this.currentLocation.getItems().contains(this.currentLocation.getStartingItem())) {
				this.currentLocation.addItem(this.currentLocation.getStartingItem());
			}

			this.updateAvailableActions();
		} else {
			this.setInteractionInfo("You search the location and find nothing of interest.");
		}
	}

	private void takeItem(Item item) {
		if (this.currentLocation.getItems().contains(item)) {
			this.currentLocation.removeItem(item);
			this.player.addItemToInventory(item);

			this.setInteractionInfo("You have taken the item: " + item);
			this.setCurrentLocationDescription(this.currentLocation.getDescription() + "\n\nLocation Items:\n");

			this.updateAvailableActions();
		} else {
			this.setInteractionInfo("There is no item to take.");
		}
	}

	private void dropItem(Item item) {
		if (this.player.getInventory().isEmpty()) {
			this.setInteractionInfo("You have no items to drop.");
			return;
		}

		if (this.player.getInventory().contains(item)) {
			this.player.getInventory().remove(item);
			this.currentLocation.addItem(item);

			this.setInteractionInfo("You have dropped the item: " + item);
			this.setCurrentLocationDescription(this.currentLocation.getDescription() + "\n\nLocation Items:\n");

			this.updateAvailableActions();
		} else {
			this.setInteractionInfo("You must select an item to drop it.");
		}
	}

	private void examineItem(Item item) {
		if (item == Item.NONE) {
			this.setInteractionInfo("You must select an item to examine it.");
		} else {
			this.setInteractionInfo("You examine the item:\n" + item.getDescription());
		}
	}

	private void updateAvailableActions() {
		this.currentLocation.removeTakeActions();
		for (Item item : this.currentLocation.getItems()) {
			this.currentLocation.addAction(
					new Action(GlobalEnums.ActionType.TAKE.toString(), item.toString(), GlobalEnums.ActionType.TAKE));
			this.setCurrentLocationDescription(this.currentLocationDescription + item + "\n");
		}
	}

	private void useItem(Item item) {
		if (this.player.getInventory().isEmpty()) {
			this.setInteractionInfo("You have no items to use.");
			return;
		}

		switch (item) {
		case POTION:
			this.usePotion(item);
			break;
		case RING:
			this.useRing();
			break;
		case DIARY:
			this.useDiary();
			break;
		case MUSICBOX:
			this.useMusicBox();
			break;
		case DRESS:
			this.useDress();
			break;
		default:
			this.setInteractionInfo("You must select an item to use.");
		}
	}

	private void useDiary() {
		this.setInteractionInfo(
				"You open the diary and read the faded ink. The journal is filled with heartfelt entries from a grieving man, recounting cherished moments with his late wife. Her favorite song, a haunting melody from an old music box, is mentioned time and time again, a symbol of their undying bond.");
		this.isDiaryFound = true;

		if (this.player.getInventory().contains(GlobalEnums.Item.MUSICBOX)) {
			this.setInteractionInfo(this.interactionInfo + System.lineSeparator() + System.lineSeparator()
					+ "The diary's mention of the haunting melody catches your attention. You remember the music box in your inventory. Perhaps the two are connected.");
		}
	}

	private void useRing() {
		if (this.isRingOnFinger) {
			this.setInteractionInfo("You already have the ring on your finger.");
		} else {
			this.setInteractionInfo("You slip the ring onto your finger. Oddly enough, it's a perfect fit.");
			this.isRingOnFinger = true;
		}
	}

	private void useDress() {
		if (this.isDressWorn) {
			this.setInteractionInfo("You are already wearing the dress.");
		} else {
			this.setInteractionInfo(
					"You put on the dress. The fabric is soft and cool against your skin, and the color is a deep, rich crimson. It's a beautiful garment, fit for a grand ball.");
			this.isDressWorn = true;
		}
	}

	private void useMusicBox() {
		this.setInteractionInfo(
				"You turn the crank on the music box. The haunting melody fills the room, echoing off the walls. The sound is both beautiful and eerie, sending shivers down your spine.");
		if (this.currentLocation == this.world.getLocationByName(GlobalEnums.LocationName.BALLROOM)) {
			this.setInteractionInfo(this.interactionInfo + System.lineSeparator()
					+ "The shadows in the room seem to dance to the music, swirling and twirling in time with the melody. It's a mesmerizing sight, and watching it soothes your soul."
					+ System.lineSeparator() + "\tYou regain 5 health.");
			this.player.applyDamage(-5);
		}
		if (this.isDiaryFound) {
			this.setInteractionInfo(this.interactionInfo + System.lineSeparator()
					+ " The music box's song is familiar. You remember the diary's mention of the haunting melody. Could this be the same music box?");
		}
	}

	private void usePotion(Item item) {
		this.setInteractionInfo("You use the potion and drink it. Its contents taste incredibly bitter.");
		Random random = new Random();
		int damage = random.nextInt(-10, 10);
		this.player.applyDamage(damage);
		if (damage > 0) {
			this.setInteractionInfo(this.interactionInfo + System.lineSeparator()
					+ "Your stomach gurgles uncomfortably. You probably shouldn't have done that."
					+ System.lineSeparator() + "\tYou have taken " + damage + " damage.");
		} else if (damage == 0) {
			this.setInteractionInfo(
					this.interactionInfo + System.lineSeparator() + "You can't tell if it did anything.");
		} else {
			this.setInteractionInfo(this.interactionInfo + System.lineSeparator() + "Surprisingly, you feel better."
					+ System.lineSeparator() + "\tYou have gained " + -damage + " health.");
		}

		this.player.getInventory().remove(item);
	}

	private void setInteractionInfo(HazardData hazardData, int damage) {
		this.interactionInfo = "You have taken " + damage + " damage due to: " + System.lineSeparator() + "\t"
				+ hazardData.getDescription();
	}

	private void setInteractionInfo(String interactionInfo) {
		this.interactionInfo = interactionInfo;
	}

	/**
	 * Gets the interaction info
	 * 
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
	 * @return the message for the cause of damage
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
	 * !!! This method does not have full functionality yet !!! !!! More will be
	 * added when player actions are implemented !!!
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
	 * 
	 * @return the player's inventory
	 */
	public List<Item> getPlayerInventory() {
		return this.player.getInventory();
	}
}
