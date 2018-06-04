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
	public int gameHeight = 600;
	//Width of the grid to be set by the server
	public int gameWidth = 600;
	private int gameSize = 40;
	private long speed = 70;
	private Frame frame = null;
	private Canvas canvas = null;
	private Graphics graph = null;
	private BufferStrategy strategy = null;
	
	public final static int EMPTY = 0;
	public final static int SNAKE = 1;
	
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
				// Draw Background
				graph.setColor(new Color(230, 230, 255));
				graph.fillRect(0, 0, gameWidth, gameHeight);
				// Draw snake, bonus ...
				int gridCase = EMPTY;
				for (int i = 0; i < gameSize; i++) {

					for (int j = 0; j < gameSize; j++) {
						String key = i + "-" + j;
						gridCase = grid.get(key).getType();
						switch (gridCase) {
						case SNAKE:
							graph.setColor(Color.black);
							graph.fillOval(i * gridUnit, j * gridUnit,
									gridUnit, gridUnit);
							break;
						default:
							break;
						}
					}
				}
				
				graph.dispose();
			} while (strategy.contentsRestored());
			// Draw image from buffer
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
		} while (strategy.contentsLost());
	}
	
	public HashMap<Integer, Player> populate(HashMap<Integer, Player> players, int numPlayers)
	{
		for (int i = 0; i < numPlayers; i++)
		{
			//Player currentPlayer = players.get(j);
			int x = (int) Math.floor(Math.random()*35)+4;
			int y = (int) Math.floor(Math.random()*35);
			Location headLocation = new Location(x, y, 1);
			Location secondLocation = new Location(x-1, y, 1);
			Location thirdLocation = new Location(x-2, y, 1);
			Location fourthLocation = new Location(x-3, y, 1);
			
			grid.replace(headLocation.getKey(), headLocation);
			grid.replace(secondLocation.getKey(), secondLocation);
			grid.replace(thirdLocation.getKey(), thirdLocation);
			grid.replace(fourthLocation.getKey(), fourthLocation);
			
			
		}
		
		return players;
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
	
	
}