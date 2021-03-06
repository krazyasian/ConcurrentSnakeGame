import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.Timer;

public class Player implements Runnable, KeyListener {

	protected static Buffer myBuffer;
	protected static Server myServer;
	
	public String playerName = "PlayerName";
	public boolean alive;
	public int playerID;
	public int facing = 1;
	//Array list containing Location objects which show what squares in the grid this player occupies
	public int length = 4;
	protected int move = 1;
	public ArrayList<Location> locations = new ArrayList<Location>(); 

	public static enum Move {
		UP, DOWN, LEFT, RIGHT, NONE;
	}

	Move lastKeyPressed = null;


	public Player(String PlayerName, int PlayerID, Buffer buffer, Server server) {
		this.playerName  = PlayerName;
		this.alive = true;
		this.playerID = PlayerID;
		this.myBuffer = buffer;
		this.myServer = server;
	}


	public void run()
	{
		resetLastKeyPressed();
		sendToBuffer();
	}

	public void addLocation(Location location)
	{
		locations.add(location);
	}

	public Location getLocation(int atIndex)
	{
		return this.locations.get(atIndex);
	}

	public void setLocation(ArrayList<Location> newArray)
	{
		this.locations = newArray;
	}

	public int getLength()
	{
		return this.length;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}

	public int getFacing()
	{
		return this.facing;
	}

	public void setFacing(int direction)
	{
		this.facing = direction;
	}


	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public boolean getAlive()
	{
		return this.alive;
	}

	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			setLastKeyPressed(Move.UP);
			move = 3;
		} else if(key == KeyEvent.VK_DOWN) {
			setLastKeyPressed(Move.DOWN);
			move = 2;
		} else if(key == KeyEvent.VK_LEFT) {
			setLastKeyPressed(Move.LEFT);
			move = 4;
		} else if(key == KeyEvent.VK_RIGHT) {
			setLastKeyPressed(Move.RIGHT);
			move = 1;
		} else if(key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setLastKeyPressed(Move.NONE);
	}
	
	public void sendToBuffer() {
		Thread thread = new Thread(){
		    public void run(){
		    	while(myServer.isRunning()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myBuffer.append(playerID, move);
					myServer.updateGameInterface();
				}
		    }
		  };
		
		thread.start();	
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	//Getters and Setters
	public String getPlayerName() {return this.playerName;}
	public void setPlayerName(String Name) {this.playerName = Name;}

	public Move getLastKeyPressed() {return lastKeyPressed;}
	public void setLastKeyPressed(Move input) {lastKeyPressed = input;}
	public void resetLastKeyPressed() {lastKeyPressed = Move.NONE;}

	public int getPlayerID() {return this.playerID;}
}