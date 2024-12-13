package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
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

	private boolean isDiaryRead = false;
	private boolean isRingOnFinger = false;
	private boolean isDressWorn = false;
	private boolean isMusicBoxUsed = false;

	private String gameOverMessage;
	private String interactionInfo;
	private String currentLocationDescription;

	private ActionHandler actionHandler;

	/**
	 * Constructor for the GameManager
	 */
	public GameManager() {
		this.worldManager = new WorldManager();
		this.actionHandler = new ActionHandler(this);
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
	 * Gets if the player is currently wearing the ring
	 * 
	 * @return true if the player is wearing the ring, false otherwise
	 */
	public boolean getIsRingOnFinger() {
		return this.isRingOnFinger;
	}

	/**
	 * Gets if the player is currently wearing the dress
	 * 
	 * @return true if the player is wearing the dress, false otherwise
	 */
	public boolean getIsDressWorn() {
		return this.isDressWorn;
	}

	/**
	 * Gets if the diary is read
	 * 
	 * @return true if the diary is read, false otherwise
	 */
	public boolean getIsDiaryRead() {
		return this.isDiaryRead;
	}

	/**
	 * Gets if the music box is used
	 * 
	 * @return true if the music box is used, false otherwise
	 */
	public boolean getIsMusicBoxUsed() {
		return this.isMusicBoxUsed;
	}

	/**
	 * Gets the interaction info
	 * 
	 * @return the interaction info
	 */
	public String getInteractionInfo() {
		return this.interactionInfo;
	}

	/**
	 * Gets the world.
	 * 
	 * @return the world
	 */
	public World getWorld() {
		return this.world;

	/**
	 * Gets all available actions from the current Location and the player
	 * 
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
	 * Sets the interaction info
	 * 
	 * @param hazardData the hazard data
	 * @param damage     the damage
	 */
	public void setInteractionInfo(HazardData hazardData, int damage) {
		this.interactionInfo = "You have taken " + damage + " damage due to: " + System.lineSeparator() + "\t"
				+ hazardData.getDescription();
	}

	/**
	 * Sets the interaction info
	 * 
	 * @param interactionInfo the interaction information
	 */
	public void setInteractionInfo(String interactionInfo) {
		this.interactionInfo = interactionInfo;
	}

	/**
	 * Sets the current location
	 * 
	 * @param location the location to set
	 */
	public void setCurrentLocation(Location location) {
		if (location == null) {
			throw new IllegalArgumentException("Location cannot be null");
    }
  this.currentLocation = location;
}

	/**
	 * Sets if the game is won
	 * 
	 * @param isGameWon true if the game is won, false otherwise
	 */
	public void setIsGameWon(boolean isGameWon) {
		this.isGameOverWin = isGameWon;
	}

	/**
	 * Sets if the game is lost
	 * 
	 * @param isGameLost true if the game is lost, false otherwise
	 */
	public void setIsGameLost(boolean isGameLost) {
		this.isGameOverLose = isGameLost;
	}

	/**
	 * Sets if the diary is read
	 * 
	 * @param isDiaryRead true if the diary is read, false otherwise
	 */
	public void setIsDiaryRead(boolean isDiaryRead) {
		this.isDiaryRead = isDiaryRead;
	}

	/**
	 * Sets if the music box is used
	 * 
	 * @param isMusicBoxUsed true if the music box is used, false otherwise
	 */
	public void setIsMusicBoxUsed(boolean isMusicBoxUsed) {
		this.isMusicBoxUsed = isMusicBoxUsed;
	}

	/**
	 * Sets if player is weating the ring
	 * 
	 * @param isRingOnFinger true if the player is wearing the ring, false otherwise
	 */
	public void setIsRingOnFinger(boolean isRingOnFinger) {
		this.isRingOnFinger = isRingOnFinger;

	}

	/**
	 * Sets if player is wearing the dress
	 * 
	 * @param isDressWorn true if the player is wearing the dress, false otherwise
	 */
	public void setIsDressWorn(boolean isDressWorn) {
		this.isDressWorn = isDressWorn;
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
		if (action == null) {
			throw new IllegalArgumentException("Action cannot be null");
		}

		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}

		if (action.getType() == ActionType.TALK) {
			this.talkToNPC();
		} else if (action.getType == ActionType.GIVE) {
      this.giveItem(item);
    } else {
			this.actionHandler.handleAction(action, item);
			this.updateAvailableActions();
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
    
    	private void giveItem(Item item) {
		switch (item) {
		case POTION:
			this.givePotion();
			break;
		case RING:
		case DRESS:
			this.giveWifeItem();
			break;
		case DIARY:
			this.giveDiary();
			break;
		case MUSICBOX:
			this.giveMusicBox();
			break;
		case NONE:
			this.setInteractionInfo("There is nothing selected to give.");
			break;
		default:
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}

	private void givePotion() {
		this.setInteractionInfo(
				"Ghost: Could it be my favorite drink... chocolate milk? I'm not sure how long its been sitting out though.");
	}

	private void giveWifeItem() {
		this.setInteractionInfo("Ghost: That wasn't what I was looking for. Please put that back where you found it.");
	}

	private void giveDiary() {
		this.setInteractionInfo(
				"Ghost: Oh hey. I haven't seen that in over 6000 years. That's pretty cool. Still not gonna let you out though.");
	}

	private void giveMusicBox() {
		this.setInteractionInfo(
				"Ghost: Yes! That's it. That's what I was missing. Thank you for your assistance. Let me open the door for you. Goodbye.");
		this.player.removeItemFromInventory(Item.MUSICBOX);
		this.world.connectLocations(this.currentLocation, Direction.SOUTH,
				this.world.getLocationByName(GlobalEnums.LocationName.EXIT));
		this.currentLocation.addAction(new Action(GlobalEnums.ActionType.MOVE.toString(), Direction.SOUTH.toString(),
				GlobalEnums.ActionType.MOVE));
	}

	private void talkToNPC() {
		if (this.isDressWorn || this.isRingOnFinger) {
			this.inflictGhostWrathOnPlayer();
		} else {
			NPC npc = this.world.getNPC();
			Location attic = this.world.getLocationByName(LocationName.ATTIC);
			Location entrance = this.world.getLocationByName(LocationName.ENTRANCEHALL);

			this.setInteractionInfo(npc.getDialogue());

			if (attic.isNPCPresent()) {
				this.world.moveNPCToLocation(npc, entrance);
				attic.removeNPC();

				npc.setDialogue(
						"Ghost: Yes I remember now. Bring me the heirloom from my marriage and I shall grant you freedom from this mansion.");
				npc.setDescription("The ghost from before is staring at you.");
			} else if (entrance.isNPCPresent()) {
				this.world.getLocationByName(LocationName.ENTRANCEHALL)
						.addAction(new Action(ActionType.GIVE.toString(), "item to ghost", ActionType.GIVE));
			}
		}
	}

	private void inflictGhostWrathOnPlayer() {
		HazardData ghostWrath = new HazardData(25, "The ghost gives you an icy chill stare.");
		this.setInteractionInfo(ghostWrath, 25);
		this.player.applyDamage(25);
		this.interactionInfo = this.interactionInfo.concat(" Ghost: You shouldn't be wearing that!!!");
		
		if (!this.checkIfPlayerIsAlive()) {
			this.isGameOverLose = true;
			this.interactionInfo = this.interactionInfo.concat(" You are frozen completely. Your journey is over and you have lost the game. Better luck next time.");
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

	/**
	 * Triggers game over lose
	 * 
	 * @param location the location where the player died
	 */
	public void onGameOverLose(Location location) {
		this.currentLocation = location;
		this.isGameOverLose = true;
		this.setGameOverMessage();
		this.setInteractionInfo(this.gameOverMessage);
	}

	/**
	 * Triggers game over win
	 * 
	 * @param location the winning location
	 */
	public void onGameOverWin(Location location) {
		this.currentLocation = location;
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

	/**
	 * Checks if the player is alive
	 * 
	 * @return true if the player is alive, false otherwise
	 */
	public boolean checkIfPlayerIsAlive() {
		return this.player.getHealth() > 0;
	}
}
