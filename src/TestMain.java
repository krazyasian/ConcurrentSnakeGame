import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class TestMain {

	Buffer tempBuffer = new Buffer(5);
	Location tempLocation = new Location(3, 3, 3);
	Location tempLocation2 = new Location (1,2,2);
	Location tempLocation3 = new Location(1, 3, 1);

	Login tempLogin = new Login(1,"Player1");
	Player tempPlayer = new Player("Player1", 1, tempBuffer, new Server());

	@Test
	void BufferTest(){
		tempBuffer.append(1,1);
		System.out.println("Checking if append method adds values to buffer: ");
		assertEquals("1/1",tempBuffer.take());
	}

	@Test
	void LocationTest()
	{
		State gameState = new State();
		System.out.print("Checking if the output of getKey method of location is correct.");				
		assertEquals(3+"-"+3, tempLocation.getKey());
	}

	@Test
	public void LoginTest()	{
		System.out.println("Checking if Login class returns the player's name.");
		assertEquals("Player1", tempLogin.getName());
	}

	@Test
	void PlayerTest()
	{
		ArrayList<Location> locList = new ArrayList<Location>();
		locList.add(tempLocation);
		locList.add(tempLocation2);
		locList.add(tempLocation3);

		tempPlayer.setLocation(locList);		
		System.out.println("Checking if player class is adding gameObject's location to it's List.");
		assertEquals(3+"-"+3, tempPlayer.getLocation(0).getKey());

	}

	@Test
	void StateTest()
	{	
		State state = new State();	
		state.setGricLoc(1, 1, state.SNAKE);
		Location loc = state.getGridLoc(1, 1);
		state.grid.replace(tempLocation.getKey(), loc);		
		System.out.println("Checking if state class is setting a gameObject at expected position/n");
		assertEquals(1,loc.getType());

	}
}
