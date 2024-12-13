package edu.westga.cs3211.text_adventure_game.model;

import java.util.Random;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;

/**
 * The ActionHandler class. Used to handle player actions.
 * 
 * @author Brailey Sharpe
 * @version Fall 2021
 */
public class ActionHandler {
	private GameManager gameManager;
	private Player player;
	private World world;

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
		this.world = this.gameManager.getWorld();
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
		case TALK:
			this.talkToNPC();
			break;
		case GIVE:
			this.giveItemToNPC(item);
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
		
		this.gameManager.setCurrentLocationDescription(nextLocation.getDescription());
		
		if (this.player.getHealth() <= 0) {
			this.gameManager.setInteractionInfo("You have died.");
			this.gameManager.setCurrentLocation(nextLocation);
			this.gameManager.setCurrentLocationDescription(nextLocation.getDescription());
			this.gameManager.onGameOverLose(nextLocation);
			return;
		}

		if (this.gameManager.getWorld().checkIfLocationIsGoal(nextLocation)) {
			this.gameManager.setInteractionInfo("You escaped the haunted house! Congratulations!");
			this.gameManager.onGameOverWin(nextLocation);
			return;
		}

		this.gameManager.setCurrentLocation(nextLocation);

	}

	private void search() {
		Location currentLocation = this.gameManager.getCurrentLocation();
		if (!currentLocation.isSearched()) {
			currentLocation.setSearched(true);
			this.gameManager.setCurrentLocationDescription(currentLocation.getDescription() + "\n\nLocation Items:\n");
			this.gameManager.setInteractionInfo("You have searched the location and found an item.\n\n");
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + currentLocation.getStartingItem().getDescription() + "\n");
            currentLocation.addItem(currentLocation.getStartingItem());
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
				this.gameManager.onGameOverLose("The liquid is now pouring out of the crater where your stomach once was. Congratulations! Your journey is over and you have lost the game.");
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
		if (this.gameManager.getCurrentLocation().getName() == LocationName.BALLROOM) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ "The shadows in the room seem to dance to the music, swirling and twirling in time with the melody. It's a mesmerizing sight, and watching it soothes your soul.");
			if (!this.gameManager.getIsMusicBoxUsed()) {
				this.player.applyDamage(-10);
				this.gameManager.setInteractionInfo(
						this.gameManager.getInteractionInfo() + System.lineSeparator() + "\tYou regain 10 health.");
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

	private void talkToNPC() {
		if (this.gameManager.getIsDressWorn() || this.gameManager.getIsRingOnFinger()) {
			this.inflictGhostWrathOnPlayer();
		} else {
			NPC npc = this.world.getNPC();
			Location attic = this.world.getLocationByName(LocationName.ATTIC);
			Location entrance = this.world.getLocationByName(LocationName.ENTRANCEHALL);

			this.gameManager.setInteractionInfo(npc.getDialogue());

			if (attic.isNPCPresent()) {
				this.world.moveNPCToLocation(npc, entrance);
				attic.removeNPC();

				npc.setDialogue(
						"Ghost: I'm not quite sure what it was I was looking for... I vaguely recall writing about it. I just can't remember. Maybe you could help me remember?");
				npc.setDescription("The ghost from before is staring at you.");
			} else if (entrance.isNPCPresent() && !this.entranceHasGiveAction()) {
				this.world.getLocationByName(LocationName.ENTRANCEHALL).addAction(new Action(ActionType.GIVE.toString(), "item to ghost", ActionType.GIVE));
			}
		}
	}

	private void inflictGhostWrathOnPlayer() {
		HazardData ghostWrath = new HazardData(25,
				"The ghost gives you an icy chill stare. You can feel your body freezing.");
		this.gameManager.setInteractionInfo(ghostWrath, 25);
		this.player.applyDamage(25);
		
		if (this.gameManager.getIsDressWorn()) {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ " Ghost: That dress is NOT yours to wear. Take it off immediately.");
		} else {
			this.gameManager.setInteractionInfo(this.gameManager.getInteractionInfo() + System.lineSeparator()
					+ " Ghost: That ring is NOT yours to wear. Take it off immediately.");
		}

		if (!this.gameManager.checkIfPlayerIsAlive()) {
			this.gameManager.onGameOverLose("You are frozen completely. Your journey is over and you have lost the game. Maybe next time you will be more careful.");
		}
	}
	
	private boolean entranceHasGiveAction() {
		for (Action action : this.world.getLocationByName(LocationName.ENTRANCEHALL).getActions()) {
			if (action.getType() == ActionType.GIVE) {
				return true;
			}
		}
		
		return false;
	}

	private void giveItemToNPC(Item item) {
		switch (item) {
		case POTION:
			this.givePotion();
			break;
		case RING:
			this.giveWifeItem();
			break;
		case DRESS:
			this.giveWifeItem();
			break;
		case DIARY:
			this.giveDiary();
			break;
		case MUSICBOX:
			this.giveMusicBox();
			break;
		default:
			this.gameManager.setInteractionInfo("You must select an item to give to the ghost.");
			break;
		}
	}

	private void givePotion() {
		this.gameManager.setInteractionInfo(
				"Ghost: Could it be my favorite drink... chocolate milk? I'm not sure how long its been sitting out though. I'll pass.");
	}

	private void giveWifeItem() {
		this.gameManager.setInteractionInfo(
				"Ghost: That wasn't what I was looking for. Please put that back where you found it.");
	}

	private void giveDiary() {
		this.gameManager.setInteractionInfo(
				"Ghost: My diary... I have not seen it in over 6000 years. It is not what I am looking for, but perhaps it can provide you some insight. I for one cannot bear to read it.");
	}

	private void giveMusicBox() {
		Location currentLocation = this.gameManager.getCurrentLocation();
		this.gameManager.setInteractionInfo(
				"Ghost: Yes! That's it. That's what I was missing. Thank you for your assistance. Let me open the door for you. Goodbye.");
		this.player.removeItemFromInventory(Item.MUSICBOX);
		this.world.connectLocations(currentLocation, Direction.SOUTH, this.world.getLocationByName(GlobalEnums.LocationName.EXIT));
		currentLocation.addAction(new Action(GlobalEnums.ActionType.MOVE.toString(), Direction.SOUTH.toString(),
				GlobalEnums.ActionType.MOVE));
	}
}
