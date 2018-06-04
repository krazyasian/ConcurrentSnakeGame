
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Utils;

public class Server {
	
	private Buffer buffer;
	private State gameState;
	
	//hashmap / looby to store the values of player name 
	//and their corresponding thread
	static HashMap<Integer, Thread> hmap = new HashMap<Integer, Thread>();
	//map database where all the records are saved
	static File dbFile = Utils.tempDbFile();
	static DB db = DBMaker.newFileDB(dbFile)
			.closeOnJvmShutdown()
			.encryptionEnable("password")
			.make();
//open an collection, TreeMap has better performance then HashMap
	static ConcurrentNavigableMap<Integer,String> players = db.getTreeMap("Players");
	static ConcurrentNavigableMap<String,Integer> passwords = db.getTreeMap("Passwords");


	//**CONSTRUCTOR
		public Server () {

		}
		
		
		//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
		public synchronized static void LoginData()
		{
			for(int i=0;i<100;i++)
			{
				players.put(i,"Player"+i);
				passwords.put("Player"+i,i);
			}
			db.commit();  //persist changes into disk
		}
		
		//**<=
		
	//verify login player **=>
	public synchronized static boolean login (Player player, int password) {
		if(getPlayer(player.getPlayerName(),password))
		{
			Thread playerR=new Thread(new Player(player.getPlayerName(),password));
			putPlayerInHashMap(player.getPlayerID(),playerR);
			System.out.println("putting player in hashMap, Name: "+player.getPlayerName());
			return true;
		}
		return false;
	}
	
	// **<=
	
	//checks if player exists in record and
	//returns true if it does **=>
	public static boolean getPlayer(String name, int password)
	{
		for(int i=0;i<100;i++)
		{
			//player index in players database and his password index 
			//in passwords is same so we are checking if player and their password matches
			if(players.get(i).equals(name) && password==i)
			{
				return true;
			}
		}
		return false;
	}

	// **<=
	
	//puts player in hashmap if login was successful **=>
	public static void putPlayerInHashMap(int playerId,Thread player)
	{
			hmap.put(playerId,player);
	}
	
	//<=
	
	

	
	
	
	public static void main(String[] args) {
		System.out.println("Hi this is a temp main method in server");
		//Create 100 player's data with passwords
		LoginData();
		for(int i = 0;i<100;i++)
		{
			Player player= new Player("Player"+i,i);
		login(player,i);
		}
		
	}
	
	//pull moves from the buffer
	private Buffer getBuffer () {
		return buffer;
	}

	//notify the players to update their game state
	private void notifyPlayers () {

	}



	//updates the game interface with the new moves coming from the players
	private synchronized void updateGameInterface () {
		//

	}

	private synchronized Thread addThread () {
		return new Thread();
	}


	private synchronized void removePlayer (Player removePlayer) {
		hmap.remove(removePlayer);
	}





}
