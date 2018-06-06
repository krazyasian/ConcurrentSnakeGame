import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMain {

	Buffer tempBuffer = new Buffer(5);
	Location tempLocation = new Location(3, 3, 3);
	Login tempLogin = new Login(1,"Player1");
	Player tempPlayer = new Player("Player1", 1, tempBuffer, new Server());
	
	@Test
	void Buffer()
	{
		tempBuffer.append(1,1);
		assertEquals("Checking if append method adds values to buffer: "
				,"1/1",tempBuffer.take());
	}
	@Test
	void Location()
	{
		//TODO
	}
	@Test
	public void Login()
	{
		//TODO
	}
	@Test
	void Player()
	{
		//TODO
	}
	@Test
	void PlayerAl()
	{
		//TODO
	}
	@Test
	void Server()
	{
		//TODO
	}
	@Test
	void State()
	{
		//TODO
	}
	@Test
	void updateSnake()
	{
		//TODO
	}
}
