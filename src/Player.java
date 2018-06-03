import java.awt.event.KeyEvent;
import java.util.*;

public class Player implements Runnable {
	
	public String PlayerName = "PlayerName";
	public boolean alive;
	public int PlayerID;
	
	//Array list containing Location objects which show what squares in the grid this player occupies
	public ArrayList locations = new ArrayList(); 
	
	public Player(String PlayerName, int PlayerID) {
		this.PlayerName  = PlayerName;
		this.alive = false;
		this.PlayerID = PlayerID;
		int LocX;
		int LocY;
		resetLastKeyPressed();
	}

	public static enum Move {
		UP, DOWN, LEFT, RIGHT, NONE;
	}
	
	Move lastKeyPressed = null;

	public void run()
	{
		System.out.println("new player created");
	}

	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			setLastKeyPressed(Move.UP);
		} else if(key == KeyEvent.VK_DOWN) {
			setLastKeyPressed(Move.DOWN);
		} else if(key == KeyEvent.VK_LEFT) {
			setLastKeyPressed(Move.LEFT);
		} else if(key == KeyEvent.VK_RIGHT) {
			setLastKeyPressed(Move.RIGHT);
		} else if(key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	//Getters and Setters
	public String getPlayerName() {return this.PlayerName;}
	public void setPlayerName(String Name) {this.PlayerName = Name;}
	
	public Move getLastKeyPressed() {
//		System.out.println(lastKeyPressed.toString());
		return lastKeyPressed;
	}
	public void setLastKeyPressed(Move input) {lastKeyPressed = input;}
	public void resetLastKeyPressed() {lastKeyPressed = Move.NONE;}
}