package edu.westga.cs3211.text_adventure_game.model;

import java.util.Random;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * The ActionHandler class. Used to handle player actions.
 * 
 * @author Brailey Sharpe
 * @version Fall 2021
 */
public class ActionHandler {
	private GameManager gameManager;
	private Player player;

	/**
	 * Creates a new ActionHandler object.
	 * 
	 * @param gameManager the game manager
	 */
	public ActionHandler(GameManager gameManager) {
		if (gameManager == null) {
			throw new IllegalArgumentException("Invalid GameManager");
		}

		this.gameManager = gameManager;
		this.player = this.gameManager.getPlayer();
	}

	/**
	 * Handles the player's action.
	 * 
	 * @param action the action to be taken
	 * @param item   the item that can be used in the
	 */
	public void handleAction(Action action, Item item) {
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
			break;
		default:
			throw new IllegalArgumentException("Unknown action type: " + action.getType());
		}
	}

	private void movePlayer(Direction direction) {
		Location currentLocation = this.gameManager.getCurrentLocation();
		Location nextLocation = currentLocation.getConnection(direction);

		if (nextLocation == null) {
			throw new IllegalArgumentException("There is no location in that direction.");
		}

		if (this.gameManager.getWorld().checkIfLocationIsHazard(nextLocation)) {
			HazardData hazardData = this.gameManager.getWorld().getHazardDataForLocation(nextLocation);
			int damage = hazardData.getDamage();
			this.player.applyDamage(damage);
			this.gameManager
					.setInteractionInfo("You have taken " + damage + " damage due to: " + hazardData.getDescription());
		}

		if (this.player.getHealth() <= 0) {
			this.gameManager.setInteractionInfo("You have died.");
			this.gameManager.setCurrentLocation(nextLocation);
			this.gameManager.checkIfGameOver();
			return;
		}

		if (this.gameManager.getWorld().checkIfLocationIsGoal(nextLocation)) {
			this.gameManager.setCurrentLocation(nextLocation);
			this.gameManager.checkIfGameOver();
		}

		this.gameManager.setCurrentLocation(nextLocation);
		this.gameManager.setCurrentLocationDescription(nextLocation.getDescription());
	}

	private void search() {
		Location currentLocation = this.gameManager.getCurrentLocation();
		if (!currentLocation.isSearched()) {
			currentLocation.setSearched(true);
			this.gameManager.setCurrentLocationDescription(currentLocation.getDescription() + "\n\nLocation Items:\n");
			this.gameManager.setInteractionInfo("You have searched the location and found an item.\n\n");
			this.gameManager.setInteractionInfo(
					this.gameManager.getInteractionInfo() + currentLocation.getStartingItem().getDescription() + "\n");

			if (!currentLocation.getItems().contains(currentLocation.getStartingItem())) {
				currentLocation.addItem(currentLocation.getStartingItem());
			}
		} else {
			this.gameManager.setInteractionInfo("You search the location and find nothing of interest.");
		}
	}

	private void takeItem(Item item) {
		Location currentLocation = this.gameManager.getCurrentLocation();

		if (currentLocation.getItems().contains(item)) {
			currentLocation.removeItem(item);
			this.player.addItemToInventory(item);

			this.gameManager.setInteractionInfo("You take the item: " + item);
			this.gameManager.setCurrentLocationDescription(currentLocation.getDescription() + "\n\nLocation Items:\n");
		} else {
			this.gameManager.setInteractionInfo("There is no item to take.");
		}
	}

	private void dropItem(Item item) {
		if (this.player.getInventory().isEmpty()) {
			this.gameManager.setInteractionInfo("You don't have any items to drop.");
			return;
		}

		if (this.player.doesPlayerHaveItem(item)) {
			this.player.removeItemFromInventory(item);
			this.gameManager.getCurrentLocation().addItem(item);

			if (item == Item.RING) {
				this.gameManager.setIsRingOnFinger(false);
			} else if (item == Item.DRESS) {
				this.gameManager.setIsDressWorn(false);
			}

			this.gameManager.setInteractionInfo("You dropped the item: " + item);
			this.gameManager.setCurrentLocationDescription(
					this.gameManager.getCurrentLocation().getDescription() + "\n\nLocation Items:\n");
		} else {
			this.gameManager.setInteractionInfo("You don't have this item to drop.");
		}
	}

	private void examineItem(Item item) {
		if (item == null || item == Item.NONE) {
			this.gameManager.setInteractionInfo("You must select an item to examine.");
		} else {
			this.gameManager.setInteractionInfo("You examine the item:\n" + item.getDescription());
		}
	}

	private void useItem(Item item) {
		if (!this.player.doesPlayerHaveItem(item)) {
			this.gameManager.setInteractionInfo("You have no items to use.");
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
			this.gameManager.setInteractionInfo("You must select an item to use.");
		}
	}

	private void usePotion(Item item) {
		this.gameManager.setInteractionInfo("You use the potion and drink it. Its contents taste incredibly bitter.");

		Random random = new Random();
		int damage = random.nextInt(-10, 10);
		this.player.applyDamage(damage);

		if (damage > 0) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ "Your stomach gurgles uncomfortably. You probably shouldn't have done that."
					+ System.lineSeparator() + "\tYou have taken " + damage + " damage.");
			if (!this.gameManager.checkIfPlayerIsAlive()) {
				this.gameManager.onGameOverLose(this.gameManager.getCurrentLocation());
			}
		} else if (damage == 0) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ "You can't tell if it did anything.");
		} else {
			this.gameManager.setInteractionInfo(
					this.gameManager.getInteractionInfo() + System.lineSeparator() + "Surprisingly, you feel better."
							+ System.lineSeparator() + "\tYou have gained " + -damage + " health.");
		}

		this.player.removeItemFromInventory(item);
	}

	private void useRing() {
		if (this.gameManager.getIsRingOnFinger()) {
			this.gameManager.setInteractionInfo(
					"You slide the ring off of your finger. The band sits heavily in your palm before you stash it away.");
			this.gameManager.setIsRingOnFinger(false);
		} else {
			this.gameManager
					.setInteractionInfo("You slip the ring onto your finger. Oddly enough, it's a perfect fit.");
			this.gameManager.setIsRingOnFinger(true);
		}

	}

	private void useDiary() {
		this.gameManager.setInteractionInfo(
				"You open the diary and read the faded ink. The journal is filled with heartfelt entries from a grieving man, recounting cherished moments with his late wife. Her favorite song, a haunting melody from an old music box, is mentioned time and time again, a symbol of their undying bond.");
		this.gameManager.setIsDiaryRead(true);

		if (this.player.doesPlayerHaveItem(Item.MUSICBOX)) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ System.lineSeparator()
					+ "The diary's mention of the haunting melody catches your attention. You remember the music box in your inventory. Perhaps the two are connected.");
		}
	}

	private void useMusicBox() {
		this.gameManager.setInteractionInfo(
				"You turn the crank on the music box. The haunting melody fills the room, echoing off the walls. The sound is both beautiful and eerie, sending shivers down your spine.");
		if (this.gameManager.getCurrentLocation() == this.gameManager.getWorld()
				.getLocationByName(GlobalEnums.LocationName.BALLROOM)) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ "The shadows in the room seem to dance to the music, swirling and twirling in time with the melody. It's a mesmerizing sight, and watching it soothes your soul.");
			if (!this.gameManager.getIsMusicBoxUsed()) {
				this.player.applyDamage(-10);
				this.gameManager.setInteractionInfo(
						this.gameManager.getInteractionInfo() + System.lineSeparator() + "\tYou regain 5 health.");
				this.gameManager.setIsMusicBoxUsed(true);
			}
		}
		if (this.gameManager.getIsDiaryRead()) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ "The music box's song is familiar. You remember the diary's mention of the haunting melody. Could this be the same music box?");
		}
	}

	private void useDress() {
		if (this.gameManager.getIsDressWorn()) {
			this.gameManager.setInteractionInfo(
					"You take off the dress. An eerie sensaiton washes over you as you do so, as if you've lost something important. The dress lies limp in your hands, a mere garment once more.");
			this.gameManager.setIsDressWorn(false);
		} else {
			this.gameManager.setInteractionInfo(
					"You put on the dress. The fabric is soft and cool against your skin, and the color is a deep, rich crimson. It's a beautiful garment, fit for a grand ball.");
			this.gameManager.setIsDressWorn(true);
		}
	}
}
