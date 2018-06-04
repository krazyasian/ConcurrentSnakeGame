import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.concurrent.*;


public class State{
	
	//Height of the grid to be set by the server
	public int gameHeight = 1000;
	//Width of the grid to be set by the server
	public int gameWidth = 1000;
	private int gameSize = 90;
	private long speed = 70;
	private Frame frame = null;
	private Canvas canvas = null;
	private Graphics graph = null;
	private BufferStrategy strategy = null;
	public final static int EMPTY = 0;
	public final static int SNAKE = 1;
	
	
	//Adding food items
	public final static int FOOD_BONUS = 1;
	public final static int FOOD_MALUS = 2;
	public final static int BIG_FOOD_BONUS = 3;
	
	
	ConcurrentHashMap<String, Location> grid = new ConcurrentHashMap<String, Location>();
	
	
	//Starting constructor for creating a completely blank grid.
	public State()
	{
		frame = new Frame();
		canvas = new Canvas();
		for (int i=0; i<gameHeight; i++ )
		{
			for (int j=0; j<gameWidth; j++)
			{
				String key = i + "-" + j;
				Location current = new Location(i, j, 0);
				grid.put(key, current);
			}
		}
		
		frame.setSize(gameWidth + 7, gameHeight + 27);
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		canvas.setSize(gameWidth + 7, gameHeight + 27);
		frame.add(canvas);
		frame.dispose();
		frame.validate();
		frame.setTitle("Snake");
		frame.setVisible(true);
		canvas.setIgnoreRepaint(true);
		canvas.setBackground(new Color(153, 153, 102));
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		graph = strategy.getDrawGraphics();
		
	}
	
	public void render()
	{
		int gridUnit = gameHeight / gameSize;
		canvas.paint(graph);
		do {
			do {
				graph = strategy.getDrawGraphics();

				
				graph.setColor(new Color(230, 230, 255));

				// Draw Background
				graph.setColor(new Color(0, 0, 102));

				graph.fillRect(0, 0, gameWidth, gameHeight);
				
				int gridCase = EMPTY;
				for (int i = 0; i < gameSize; i++) {

					for (int j = 0; j < gameSize; j++) {
						String key = i + "-" + j;
						gridCase = grid.get(key).getType();
						switch (gridCase) {
						case SNAKE:
							graph.setColor(Color.red);
							graph.fillOval(i * gridUnit, j * gridUnit,
									gridUnit, gridUnit);
							break;
						case FOOD_MALUS:
							graph.setColor(Color.yellow);
							graph.fillOval(i*gridUnit, j* gridUnit,
									gridUnit, gridUnit);
							break;
						default:
							break;
						}
					}
				}
				
				graph.dispose();
			} while (strategy.contentsRestored());
			
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
		} while (strategy.contentsLost());
	}
	
	public HashMap<Integer, Player> populate(HashMap<Integer, Player> players, int numPlayers)
	{
		for (int i = 0; i < numPlayers; i++)
		{
			//Player currentPlayer = players.get(j);
			int x = (int) Math.floor(Math.random()*gameSize)+3;
			int y = (int) Math.floor(Math.random()*gameSize);
			Player currentPlayer = players.get(i);
			
			Location headLocation = new Location(x, y, 1);
			Location secondLocation = new Location(x-1, y, 1);
			Location thirdLocation = new Location(x-2, y, 1);
			Location fourthLocation = new Location(x-3, y, 1);
			
			grid.replace(headLocation.getKey(), headLocation);
			grid.replace(secondLocation.getKey(), secondLocation);
			grid.replace(thirdLocation.getKey(), thirdLocation);
			grid.replace(fourthLocation.getKey(), fourthLocation);
			
			currentPlayer.addLocation(headLocation);
			currentPlayer.addLocation(secondLocation);
			currentPlayer.addLocation(thirdLocation);
			currentPlayer.addLocation(fourthLocation);
			
			players.replace(i, currentPlayer);
			
		}
		
		return players;
	}
	
	public Player move(Player currentPlayer, int direction)
	{
		Location temp = currentPlayer.getLocation(currentPlayer.getLength()-1);
		String key = temp.getKey();
		temp.setType(EMPTY);
		grid.replace(key, temp);
		
		Location head = currentPlayer.getLocation(0);
		Location next = head;
		if (direction == 1)
		{
			int newx;
			if (head.getx() == gameSize-1)
			{
				newx = 1;
			}
			else 
			{
				newx = head.getx() +1;
			}
			next = new Location(newx, head.gety(), 1);
		}
		
		ArrayList<Location> newArray = new ArrayList<Location>();
		newArray.add(next);
		grid.replace(next.getKey(), next);
		
		for (int i=0; i<currentPlayer.getLength()-1; i++)
		{
			newArray.add(currentPlayer.getLocation(i));
		}
		
		currentPlayer.setLocation(newArray);
		
		return currentPlayer;
	}
	
	public Location getGridLoc(int x, int y)
	{
		String key = x + "-" + y;
		Location atLoc = grid.get(key);
		return atLoc;
		
	}
	
	public void setGricLoc(int x, int y, int contains)
	{
		Location current = new Location(x, y, contains);
		String key = x + "-" + y;
		grid.replace(key, current);
	}
	
	//This method closese the application on call
	public void closeWindow()
	{
		System.exit(0);
	}
}