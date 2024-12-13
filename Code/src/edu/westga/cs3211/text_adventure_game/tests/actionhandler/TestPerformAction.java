package edu.westga.cs3211.text_adventure_game.tests.actionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.ActionHandler;
import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.NPC;
import edu.westga.cs3211.text_adventure_game.model.Player;
import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the ActionHandler class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class TestPerformAction {

	private GameManager gameManager;
	private Player player;
	private World world;
	private Location location;
	private ActionHandler actionHandler;
	
	/**
	 * A fake random class to ensure that the random number generated is always the same
	 */
	public class FakeRandom extends Random {
		/**
		 * Unusued constant
		 */
		private static final long serialVersionUID = 1L;
		private  int bound;
		
		/**
		 * Creates a new instance of the FakeRandom class
		 * 
		 * @param bound the bound to return
		 */
		public FakeRandom(int bound) {
			this.bound = bound;
		}
		
		/**
		 * Returns the bound
		 * 
		 * @param bound the bound to return
		 * @return the bound
		 */
		public int nextInt(int bound) {
			return this.bound;
		}
	}

	@BeforeEach
	void setUp() {
		this.location = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall", HazardType.NONE, false,
				new ArrayList<>(), Item.RING);
		this.gameManager = new GameManager();
		this.gameManager.setCurrentLocation(this.location);
		this.player = this.gameManager.getPlayer();
		this.world = this.gameManager.getWorld();
		this.actionHandler = new ActionHandler(this.gameManager);
	}

	@Test
	void testConstructorThrowsExceptionForNullGameManager() {
		assertThrows(IllegalArgumentException.class, () -> new ActionHandler(null));
	}

	@Test
	void testHandleActionMove() {
		Location nextLocation = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		this.location.addConnection(Direction.NORTH, nextLocation);

		Action moveAction = new Action(ActionType.MOVE.toString(), "NORTH", ActionType.MOVE);
		this.actionHandler.handleAction(moveAction, null);

		assertEquals(nextLocation, this.gameManager.getCurrentLocation());
	}

	@Test
	void testHandleActionMoveThrowsForInvalidDirection() {
		Action moveAction = new Action(ActionType.MOVE.toString(), "NORTH", ActionType.MOVE);
		assertThrows(IllegalArgumentException.class, () -> this.actionHandler.handleAction(moveAction, null));
	}

	@Test
	void testHandleActionMoveWhenHazardLocation() {
		Location nextLocation = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen",
				HazardType.GHOSTLYAPPARITION, false, new ArrayList<>(), Item.NONE);
		this.location.addConnection(Direction.NORTH, nextLocation);

		Action moveAction = new Action(ActionType.MOVE.toString(), "NORTH", ActionType.MOVE);
		this.actionHandler.handleAction(moveAction, null);

		assertEquals(95, this.gameManager.getPlayer().getHealth());
	}

	@Test
	void testHandleActionMoveWhenHazardLocationAndKillsPlayer() {
		Location nextLocation = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen",
				HazardType.GHOSTLYAPPARITION, false, new ArrayList<>(), Item.NONE);
		this.location.addConnection(Direction.NORTH, nextLocation);

		this.player.applyDamage(95);

		Action moveAction = new Action(ActionType.MOVE.toString(), "NORTH", ActionType.MOVE);
		this.actionHandler.handleAction(moveAction, null);

		assertAll(() -> assertEquals(0, this.gameManager.getPlayer().getHealth()),
				() -> assertTrue(this.gameManager.getIsGameOverLose()));
	}

	@Test
	void testHandleActionMoveWhenLocationIsGoal() {
		Location nextLocation = new Location(GlobalEnums.LocationName.EXIT, "The exit", HazardType.NONE, true,
				new ArrayList<>(), Item.NONE);
		this.location.addConnection(Direction.NORTH, nextLocation);

		Action moveAction = new Action(ActionType.MOVE.toString(), "NORTH", ActionType.MOVE);
		this.actionHandler.handleAction(moveAction, null);

		assertTrue(this.gameManager.getIsGameOverWin());
	}

	@Test
	void testHandleActionSearch() {
		Action searchAction = new Action(ActionType.SEARCH.toString(), "Search the location", ActionType.SEARCH);
		this.actionHandler.handleAction(searchAction, null);
		
		System.out.println("Items at location: " + this.location.getItems());

		assertTrue(this.location.isSearched());
		assertTrue(this.location.getItems().contains(Item.RING));
	}
	
	@Test
	void testHandleActionSearchLocationAlreadySearched() {
		Action searchAction = new Action(ActionType.SEARCH.toString(), "Search the location", ActionType.SEARCH);
		this.actionHandler.handleAction(searchAction, null);
		
		this.actionHandler.handleAction(searchAction, null);
		assertEquals("You search the location and find nothing of interest.", this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionTakeItem() {
		this.location.addItem(Item.POTION);

		Action takeAction = new Action(ActionType.TAKE.toString(), "Take item", ActionType.TAKE);
		this.actionHandler.handleAction(takeAction, Item.POTION);

		assertFalse(this.location.getItems().contains(Item.POTION));
		assertTrue(this.player.doesPlayerHaveItem(Item.POTION));
	}
	
	@Test
	void testHandleActionTakeItemWhenLocationHasNoItems() {
		Action takeAction = new Action(ActionType.TAKE.toString(), "Take item", ActionType.TAKE);
		this.actionHandler.handleAction(takeAction, Item.POTION);

		assertEquals("There is no item to take.", this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionDropItem() {
		this.player.addItemToInventory(Item.RING);

		Action dropAction = new Action(ActionType.DROP.toString(), "Drop an item", ActionType.DROP);
		this.actionHandler.handleAction(dropAction, Item.RING);

		assertFalse(this.player.doesPlayerHaveItem(Item.RING));
		assertTrue(this.location.getItems().contains(Item.RING));
	}
	
	@Test
	void testHandleActionDropItemWhenPlayerHasNoItems() {
		Action dropAction = new Action(ActionType.DROP.toString(), "Drop an item", ActionType.DROP);
		this.actionHandler.handleAction(dropAction, Item.RING);

		assertEquals("You don't have any items to drop.", this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionDropItemWhenPlayerDoesNotHaveItem() {
		Action dropAction = new Action(ActionType.DROP.toString(), "Drop an item", ActionType.DROP);
		this.player.addItemToInventory(Item.RING);
		this.actionHandler.handleAction(dropAction, Item.DRESS);

		assertEquals("You don't have this item to drop.", this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionDropDress() {
		this.player.addItemToInventory(Item.DRESS);

		Action dropAction = new Action(ActionType.DROP.toString(), "Drop an item", ActionType.DROP);
		this.actionHandler.handleAction(dropAction, Item.DRESS);

		assertFalse(this.player.doesPlayerHaveItem(Item.DRESS));
		assertTrue(this.location.getItems().contains(Item.DRESS));
	}
	
	@Test
	void testHandleActionDropItemNotRingOrDress() {
		this.player.addItemToInventory(Item.POTION);

		Action dropAction = new Action(ActionType.DROP.toString(), "Drop an item", ActionType.DROP);
		this.actionHandler.handleAction(dropAction, Item.POTION);

		assertEquals("You dropped the item: " + Item.POTION, this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionExamineItem() {
		Action examineAction = new Action(ActionType.EXAMINE.toString(), "Examine an item", ActionType.EXAMINE);
		this.actionHandler.handleAction(examineAction, Item.POTION);

		assertEquals("You examine the item:\n" + Item.POTION.getDescription(), this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionExaminItemItemIsNull() {
		Action examineAction = new Action(ActionType.EXAMINE.toString(), "Examine an item", ActionType.EXAMINE);
		this.actionHandler.handleAction(examineAction, Item.NONE);

		assertEquals("You must select an item to examine.", this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionExamineItemItemIsNone() {
		Action examineAction = new Action(ActionType.EXAMINE.toString(), "Examine an item", ActionType.EXAMINE);
		this.actionHandler.handleAction(examineAction, Item.NONE);

		assertEquals("You must select an item to examine.", this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionUseItemPotionDealDamage() {
		this.player.addItemToInventory(Item.POTION);

		Action useAction = new Action(ActionType.USE.toString(), "Use potion", ActionType.USE);
		this.player.applyDamage(99);
		this.actionHandler.handleAction(useAction, Item.POTION);
		this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);
        this.player.addItemToInventory(Item.POTION);
        this.actionHandler.handleAction(useAction, Item.POTION);

		assertFalse(this.player.doesPlayerHaveItem(Item.POTION));
	}

	@Test
	void testHandleActionUseItemMusicBox() {
		this.player.addItemToInventory(Item.MUSICBOX);
		this.gameManager.setCurrentLocation(new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom",
				HazardType.DANCINGSHADOWS, false, new ArrayList<>(), Item.MUSICBOX));

		Action useAction = new Action(ActionType.USE.toString(), "Use Music Box", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.MUSICBOX);

		assertTrue(this.gameManager.getIsMusicBoxUsed());
	}
	
	@Test
	void testHandleActionUseItemMusicBoxWhenNotInBallroom() {
		this.player.addItemToInventory(Item.MUSICBOX);
		this.gameManager.setCurrentLocation(new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall",
				HazardType.NONE, false, new ArrayList<>(), Item.MUSICBOX));

		Action useAction = new Action(ActionType.USE.toString(), "Use Music Box", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.MUSICBOX);

		assertEquals("You turn the crank on the music box. The haunting melody fills the room, echoing off the walls. The sound is both beautiful and eerie, sending shivers down your spine.", this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionsUseItemMusicBoxAfterUsingMusicBoxWhenInBallroom() {
		this.player.addItemToInventory(Item.MUSICBOX);
		this.gameManager.setCurrentLocation(new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom",
				HazardType.DANCINGSHADOWS, false, new ArrayList<>(), Item.MUSICBOX));
		this.gameManager.setIsMusicBoxUsed(true);
		Action useAction = new Action(ActionType.USE.toString(), "Use Music Box", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.MUSICBOX);

		assertEquals("You turn the crank on the music box. The haunting melody fills the room, echoing off the walls. The sound is both beautiful and eerie, sending shivers down your spine.\r\n"
				+ "The shadows in the room seem to dance to the music, swirling and twirling in time with the melody. It's a mesmerizing sight, and watching it soothes your soul.", this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionUseItemMusicBoxWhenDiaryIsRead() {
		this.player.addItemToInventory(Item.MUSICBOX);
		this.gameManager.setCurrentLocation(new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom",
				HazardType.DANCINGSHADOWS, false, new ArrayList<>(), Item.MUSICBOX));
		this.gameManager.setIsDiaryRead(true);
		Action useAction = new Action(ActionType.USE.toString(), "Use Music Box", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.MUSICBOX);

		assertTrue(this.gameManager.getIsMusicBoxUsed());
		assertEquals("You turn the crank on the music box. The haunting melody fills the room, echoing off the walls. The sound is both beautiful and eerie, sending shivers down your spine.\r\n"
				+ "The shadows in the room seem to dance to the music, swirling and twirling in time with the melody. It's a mesmerizing sight, and watching it soothes your soul.\r\n"
				+ "	You regain 10 health.\r\n"
				+ "The music box's song is familiar. You remember the diary's mention of the haunting melody. Could this be the same music box?", this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionUseItemRing() {
		this.player.addItemToInventory(Item.RING);

		Action useAction = new Action(ActionType.USE.toString(), "Use Ring", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.RING);

		assertTrue(this.gameManager.getIsRingOnFinger());

		this.actionHandler.handleAction(useAction, Item.RING);

		assertFalse(this.gameManager.getIsRingOnFinger());
	}

	@Test
	void testHandleActionUseItemDress() {
		this.player.addItemToInventory(Item.DRESS);

		Action useAction = new Action(ActionType.USE.toString(), "Use Dress", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.DRESS);

		assertTrue(this.gameManager.getIsDressWorn());
		this.actionHandler.handleAction(useAction, Item.DRESS);
		assertFalse(this.gameManager.getIsDressWorn());
	}

	@Test
	void testHandleActionUseItemDiary() {
		this.player.addItemToInventory(Item.DIARY);

		Action useAction = new Action(ActionType.USE.toString(), "Use Diary", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.DIARY);

		assertTrue(this.gameManager.getIsDiaryRead());
	}
	
	@Test
	void testHandleActionUseItemDiaryWhenPlayerHasMusicBox() {
		this.player.addItemToInventory(Item.DIARY);
		this.player.addItemToInventory(Item.MUSICBOX);

		Action useAction = new Action(ActionType.USE.toString(), "Use Diary", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.DIARY);

		assertEquals(
				"You open the diary and read the faded ink. The journal is filled with heartfelt entries from a grieving man, recounting cherished moments with his late wife. Her favorite song, a haunting melody from an old music box, is mentioned time and time again, a symbol of their undying bond.\r\n"
				+ "\r\n"
				+ "The diary's mention of the haunting melody catches your attention. You remember the music box in your inventory. Perhaps the two are connected.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionUseItemPlayerDoesntHaveItem() {
		Action useAction = new Action(ActionType.USE.toString(), "Use Diary", ActionType.USE);
		this.actionHandler.handleAction(useAction, Item.DIARY);

		assertEquals("You have no items to use.", this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionTalkToNPC() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action talkAction = new Action(ActionType.TALK.toString(), "Talk to NPC", ActionType.TALK);
		this.actionHandler.handleAction(talkAction, null);

		assertEquals(
				"The ghost gives you a cold stare, seemingly looking through you. As you're about to step away, the ghost speaks to you.\r\n"
						+ "\r\n"
						+ "Ghost: I take it you wish to escape, yes? There is something I am missing, something I am looking for. Find it, and I shall set you free. I will be waiting for you by the entrance.\r\n"
						+ "\r\n" + "Before you can say anything, the ghost fades away, leaving you alone.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionTalkToNPCWhenWearingRing() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);
		this.gameManager.setIsRingOnFinger(true);

		Action talkAction = new Action(ActionType.TALK.toString(), "Talk to NPC", ActionType.TALK);
		this.actionHandler.handleAction(talkAction, null);
		
		assertEquals(75, this.player.getHealth());
	}
	
	@Test
	void testHandleActionTalkToNPCWhenWearingDress() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);
		this.gameManager.setIsDressWorn(true);

		Action talkAction = new Action(ActionType.TALK.toString(), "Talk to NPC", ActionType.TALK);
		this.actionHandler.handleAction(talkAction, null);

		assertEquals(75, this.player.getHealth());
	}
	
	@Test
	void testHandleActionTalkToNPCWhenWearingRingKillsPlayer() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);
		this.gameManager.setIsRingOnFinger(true);
		this.player.applyDamage(75);

		Action talkAction = new Action(ActionType.TALK.toString(), "Talk to NPC", ActionType.TALK);
		this.actionHandler.handleAction(talkAction, null);

		assertAll(() -> assertEquals(0, this.player.getHealth()),
				() -> assertTrue(this.gameManager.getIsGameOverLose()));
	}
	
	@Test
	void testHandleActionTalkToNPCWhenNPCIsByEntrance() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.world.getStartLocation());

		Action talkAction = new Action(ActionType.TALK.toString(), "Talk to NPC", ActionType.TALK);
		this.location.addAction(talkAction);
		this.actionHandler.handleAction(talkAction, Item.NONE);

		assertEquals(
				"The ghost gives you a cold stare, seemingly looking through you. As you're about to step away, the ghost speaks to you.\r\n"
						+ "\r\n"
						+ "Ghost: I take it you wish to escape, yes? There is something I am missing, something I am looking for. Find it, and I shall set you free. I will be waiting for you by the entrance.\r\n"
						+ "\r\n" + "Before you can say anything, the ghost fades away, leaving you alone.",
				this.gameManager.getInteractionInfo());
		
		this.actionHandler.handleAction(talkAction, null);
	}
	
	@Test
	void testhandleActionTalkToNPCAtEntranceWithGiveActionAtLocation() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.world.getStartLocation());
		this.gameManager.setCurrentLocation(this.world.getStartLocation());
		this.gameManager.getCurrentLocation().addAction(new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE));

		Action talkAction = new Action(ActionType.TALK.toString(), "Talk to NPC", ActionType.TALK);
		this.actionHandler.handleAction(talkAction, null);

		assertEquals(
				"The ghost gives you a cold stare, seemingly looking through you. As you're about to step away, the ghost speaks to you.\r\n"
						+ "\r\n"
						+ "Ghost: I take it you wish to escape, yes? There is something I am missing, something I am looking for. Find it, and I shall set you free. I will be waiting for you by the entrance.\r\n"
						+ "\r\n" + "Before you can say anything, the ghost fades away, leaving you alone.",
				this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionGiveItemToNPCMusicBox() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action giveAction = new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE);
		this.actionHandler.handleAction(giveAction, Item.MUSICBOX);

		assertFalse(this.player.doesPlayerHaveItem(Item.MUSICBOX));
		assertEquals(
				"Ghost: Yes! That's it. That's what I was missing. Thank you for your assistance. Let me open the door for you. Goodbye.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionGiveItemTONPCRing() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action giveAction = new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE);
		this.actionHandler.handleAction(giveAction, Item.RING);

		assertEquals(
				"Ghost: That wasn't what I was looking for. Please put that back where you found it.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionGiveItemTONPCDress() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action giveAction = new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE);
		this.actionHandler.handleAction(giveAction, Item.DRESS);

		assertEquals(
				"Ghost: That wasn't what I was looking for. Please put that back where you found it.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionGiveItemTONPCPotion() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action giveAction = new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE);
		this.actionHandler.handleAction(giveAction, Item.POTION);

		assertEquals(
				"Ghost: Could it be my favorite drink... chocolate milk? I'm not sure how long its been sitting out though. I'll pass.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionGiveItemTONPCDiary() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action giveAction = new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE);
		this.actionHandler.handleAction(giveAction, Item.DIARY);

		assertEquals("Ghost: My diary... I have not seen it in over 6000 years. It is not what I am looking for, but perhaps it can provide you some insight. I for one cannot bear to read it.",
				this.gameManager.getInteractionInfo());
	}
	
	@Test
	void testHandleActionGiveItemTONPCNone() {
		NPC npc = new NPC("Test NPC");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.location);

		Action giveAction = new Action(ActionType.GIVE.toString(), "Give item to NPC", ActionType.GIVE);
		this.actionHandler.handleAction(giveAction, Item.NONE);

		assertEquals("You must select an item to give to the ghost.", this.gameManager.getInteractionInfo());
	}

	@Test
	void testHandleActionUnknownThrowsException() {
		Action unknownAction = new Action("INVALID", "Action invalid", ActionType.NONE);
		assertThrows(IllegalArgumentException.class, () -> this.actionHandler.handleAction(unknownAction, null));
	}
}
