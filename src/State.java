import java.util.*;
import java.util.concurrent.*;


public class State{
	
	//Height of the grid to be set by the server
	public int gameHeight;
	//Width of the grid to be set by the server
	public int gameWidth;
	
	ConcurrentHashMap<String, Location> grid = new ConcurrentHashMap<String, Location>();
	
	
	//Starting constructor for creating a completely blank grid.
	public State(int gameHeight, int gameWidth)
	{
		this.gameHeight = gameHeight;
		this.gameWidth = gameWidth;
		
		for (int i=0; i<gameHeight; i++ )
		{
			for (int j=0; j<gameWidth; j++)
			{
				String key = i + "-" + j;
				Location current = new Location(i, j, "EMPTY");
				grid.put(key, current);
			}
		}
	}
	
	public HashMap<Integer, Player> populate(HashMap<Integer, Player> players, int numPlayers)
	{
		for (int i = 0; i < numPlayers; i++)
		{
			Player currentPlayer = players.get(i);
			int x = 4;
			int y = i;
			Location headLocation = new Location(x, y, "SNAKE");
			Location secondLocation = new Location(x-1, y, "SNAKE");
			Location thirdLocation = new Location(x-2, y, "SNAKE");
			
			grid.replace(headLocation.getKey(), headLocation);
			grid.replace(secondLocation.getKey(), secondLocation);
			grid.replace(thirdLocation.getKey(), thirdLocation);
			
			currentPlayer.locations.add(headLocation);
			currentPlayer.locations.add(secondLocation);
			currentPlayer.locations.add(thirdLocation);
			
		}
		
		return players;
	}
	
	public Location getGridLoc(int x, int y)
	{
		String key = x + "-" + y;
		Location atLoc = grid.get(key);
		return atLoc;
		
	}
	
	public void setGricLoc(int x, int y, String contains)
	{
		Location current = new Location(x, y, contains);
		String key = x + "-" + y;
		grid.replace(key, current);
	}
	
	
}