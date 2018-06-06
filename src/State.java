import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.concurrent.*;

import javax.swing.JFrame;
import javax.swing.Timer;


public class State {
	
	//Height of the grid to be set by the server
	public int gameHeight = 800;
	//Width of the grid to be set by the server
	public int gameWidth = 800;
	protected int gameSize = 100;
	private long speed = 200;
	protected JFrame frame = null;
	protected Canvas canvas = null;
	protected Graphics graph = null;
	protected BufferStrategy strategy = null;
	public final static int EMPTY = 0;
	public final static int SNAKE = 1;
	
	//Adding food items
	public final static int FOOD_BONUS = 2;
	public final static int FOOD_NEGATIVE = 3;
	public final static int BIG_FOOD_BONUS = 4;
	
	
	ConcurrentHashMap<String, Location> grid = new ConcurrentHashMap<String, Location>();
	
	
	//Starting constructor for creating a completely blank grid.
	public State()
	{
		frame = new JFrame();
		canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		frame.setTitle("Snake Server");
		frame.setVisible(true);
		canvas.setIgnoreRepaint(true);
		// *** => This is what causes two difference color windows i think (canvas)
		canvas.setBackground(new Color(153, 153, 102));
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		graph = strategy.getDrawGraphics();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		renderLoop();
		
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
							graph.setColor(Color.yellow);
							graph.fillOval(i * gridUnit, j * gridUnit,
									gridUnit, gridUnit);
							break;
						case FOOD_BONUS:
							graph.setColor(Color.green);
							graph.fillOval(i*gridUnit, j* gridUnit,
									(gridUnit/2)+3, (gridUnit/2)+3);
							break;
						case FOOD_NEGATIVE:
							graph.setColor(Color.red);
							graph.fillOval(i*gridUnit, j* gridUnit,
									(gridUnit/2)+3, (gridUnit/2+3));
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
	
	/**
	 * Starts the rendering loop. This is done using a timer.
	 */
	protected void renderLoop() {
		int delay = 1000 / 25;
		new Timer(delay, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				render();
			}
		}).start();
	}
	
	public Player populate(Player currentPlayer)
	{

		int x = (int) Math.floor(Math.random()*gameSize)+3;
		int y = (int) Math.floor(Math.random()*gameSize);
		
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

		
		for(int i=0; i<30; i++)
		{
			int x1 = (int) Math.floor(Math.random()*gameSize)+1;
			int y1 = (int) Math.floor(Math.random()*gameSize)+1;
			String key = x1 + "-" + y1;
			if (grid.get(key).getType() == 1)
			{
				x1 = (int) Math.floor(Math.random()*gameSize)+1;
				y1 = (int) Math.floor(Math.random()*gameSize)+1;
			}
			else
			{
				grid.replace(key, new Location(x1,y1,2));
			}
		}
		
		for(int i=0; i<15; i++)
		{
			int x2 = (int) Math.floor(Math.random()*gameSize)+1;
			int y2 = (int) Math.floor(Math.random()*gameSize)+1;
			String key = x2 + "-" + y2;
			if (grid.get(key).getType() == 1)
			{
				x2 = (int) Math.floor(Math.random()*gameSize)+1;
				y2 = (int) Math.floor(Math.random()*gameSize)+1;
			}
			else
			{
				grid.replace(key, new Location(x2,y2,3));
			}
		}
		
		return currentPlayer;
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
		
		for(int i=0; i<30; i++)
		{
			int x = (int) Math.floor(Math.random()*gameSize)+1;
			int y = (int) Math.floor(Math.random()*gameSize)+1;
			String key = x + "-" + y;
			if (grid.get(key).getType() == 1)
			{
				x = (int) Math.floor(Math.random()*gameSize)+1;
				y = (int) Math.floor(Math.random()*gameSize)+1;
			}
			else
			{
				grid.replace(key, new Location(x,y,2));
			}
		}
		
		for(int i=0; i<15; i++)
		{
			int x = (int) Math.floor(Math.random()*gameSize)+1;
			int y = (int) Math.floor(Math.random()*gameSize)+1;
			String key = x + "-" + y;
			if (grid.get(key).getType() == 1)
			{
				x = (int) Math.floor(Math.random()*gameSize)+1;
				y = (int) Math.floor(Math.random()*gameSize)+1;
			}
			else
			{
				grid.replace(key, new Location(x,y,3));
			}
		}
		
		return players;
	}
	
	public Player move(Player currentPlayer, int direction)
	{
		boolean fed = false;
		boolean poisoned = false;
		if (currentPlayer.getLength() == 1)
		{
			currentPlayer.setAlive(false);
		}
		if(currentPlayer.getAlive() == true)
		{
			Location temp = currentPlayer.getLocation(currentPlayer.getLength()-1);
			String key = temp.getKey();
			temp.setType(EMPTY);
			grid.replace(key, temp);
			
			Location head = currentPlayer.getLocation(0);
			Location next = head;
			//move right
			if (direction == 1)
			{
				if (currentPlayer.getFacing() != 4)
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
					currentPlayer.setFacing(1);
				}
				else 
				{
					int newx;
					if (head.getx() == 0)
					{
						newx = gameSize-1;
					}
					else 
					{
						newx = head.getx() -1;
					}
					next = new Location(newx, head.gety(), 1);
					currentPlayer.setFacing(4);
				}
				
			}
			//move down
			else if (direction == 2)
			{
				if (currentPlayer.getFacing() != 3)
				{
					int newy;
					if (head.gety() == gameSize-1)
					{
						newy = 1;
					}
					else 
					{
						newy = head.gety() +1;
					}
					next = new Location(head.getx(), newy, 1);
					currentPlayer.setFacing(2);
				}
				else
				{
					int newy;
					if (head.gety() == 0)
					{
						newy = gameSize-1;
					}
					else 
					{
						newy = head.gety() -1;
					}
					next = new Location(head.getx(), newy, 1);
					currentPlayer.setFacing(3);
				}
				
			}
			//move up
			else if (direction == 3)
			{
				
				if (currentPlayer.getFacing() != 2)
				{
					int newy;
					if (head.gety() == 0)
					{
						newy = gameSize-1;
					}
					else 
					{
						newy = head.gety() -1;
					}
					next = new Location(head.getx(), newy, 1);
					currentPlayer.setFacing(3);
				}
				else
				{
					int newy;
					if (head.gety() == gameSize-1)
					{
						newy = 1;
					}
					else 
					{
						newy = head.gety() +1;
					}
					next = new Location(head.getx(), newy, 1);
					currentPlayer.setFacing(2);
				}
				
			}
			//move left
			else if (direction == 4)
			{
				if (currentPlayer.getFacing() != 1)
				{
					int newx;
					if (head.getx() == 0)
					{
						newx = gameSize-1;
					}
					else 
					{
						newx = head.getx() -1;
					}
					next = new Location(newx, head.gety(), 1);
					currentPlayer.setFacing(4);
				}
				else
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
					currentPlayer.setFacing(1);
				}
				
			}
			
			if (checkCollision(next) == 1)
			{
				currentPlayer.setAlive(false);
				for (int i=0; i<currentPlayer.getLength(); i++)
				{
					Location fragment = currentPlayer.getLocation(i);
					fragment.setType(0);
					grid.replace(fragment.getKey(), fragment);
					
					
				}
				currentPlayer.setLength(0);
				return currentPlayer;
			}
			else if (checkCollision(next) == 2)
			{
				fed = true;
				int x = (int) Math.floor(Math.random()*gameSize)+1;
				int y = (int) Math.floor(Math.random()*gameSize)+1;
				String key2 = x+"-"+y;
				while (grid.get(key2).getType() != 0)
				{
					x = (int) Math.floor(Math.random()*gameSize)+1;
					y = (int) Math.floor(Math.random()*gameSize)+1;
				}
				grid.replace(key2, new Location(x,y,2));
			}
			else if (checkCollision(next) == 3)
			{
				poisoned = true;
				int x = (int) Math.floor(Math.random()*gameSize)+1;
				int y = (int) Math.floor(Math.random()*gameSize)+1;
				String key2 = x+"-"+y;
				while (grid.get(key2).getType() != 0)
				{
					x = (int) Math.floor(Math.random()*gameSize)+1;
					y = (int) Math.floor(Math.random()*gameSize)+1;
				}
				grid.replace(key2, new Location(x,y,3));
			}
			
			ArrayList<Location> newArray = new ArrayList<Location>();
			newArray.add(next);
			if (poisoned == true)
			{
				currentPlayer.setLength(currentPlayer.getLength() -1);
				Location tail = currentPlayer.getLocation(currentPlayer.getLength()-1);
				grid.replace(tail.getKey(), new Location(tail.getx(), tail.gety(), 0));
				
			}
			
			grid.replace(next.getKey(), next);
			
			for (int i=0; i<currentPlayer.getLength()-1; i++)
			{
				newArray.add(currentPlayer.getLocation(i));
			}
			
			if (fed == true)
			{
				newArray.add(temp);
				currentPlayer.setLength(currentPlayer.getLength()+1);
			}
			currentPlayer.setLocation(newArray);
			
		}
		
		
		return currentPlayer;
	}
	
	public int checkCollision(Location next)
	{
		if (grid.get(next.getKey()).getType() == 1)
		{
			return 1;
		}
		else if (grid.get(next.getKey()).getType() == 2)
		{
			return 2;
		}
		else if (grid.get(next.getKey()).getType() == 3)
		{
			return 3;
		}
		return 0;
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
	
	//This method closes the application on call
	public void closeWindow()
	{
		System.exit(0);
	}
}