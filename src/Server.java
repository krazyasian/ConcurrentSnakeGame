
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Utils;



public class Server {

	private Buffer buffer;
	private State gameState;
	private boolean running;

	//hashmap / looby to store the values of player name 
	//and their corresponding thread
	static HashMap<Integer, Player> hmap;
	//map database where all the records are saved
	static File dbFile;
	private static DB db;
	//open an collection, TreeMap has better performance then HashMap
	private static ConcurrentNavigableMap<Integer,String> players;
	private static ConcurrentNavigableMap<Integer,String> passwords;


	//**CONSTRUCTOR
	public Server () {
		buffer = new Buffer(100);
		gameState = new State();
		running = true;
		hmap = new HashMap<Integer, Player>();
		dbFile = Utils.tempDbFile();
		db = DBMaker.newFileDB(dbFile)
				.closeOnJvmShutdown()
				.encryptionEnable("password")
				.make();

		players = db.getTreeMap("Players");
		passwords = db.getTreeMap("Passwords");
	}

	
	
	//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
			public synchronized static void loginData()
			{
				for(int i=0;i<100;i++)
				{
					Player player = new Player("Player"+i,i);
					players.put(i, player.getPlayerName());
					passwords.put(i,""+i);
				}
			}
			
			// **<=
			
	//verify login player **=>
	public synchronized static boolean login (Player player, String password) {
		if(getPlayer(player.getPlayerName(),password))
		{
			putPlayerInHashMap(player.getPlayerID(),player);
			return true;
		}
		return false;
	}

	//checks if player exists in record and
	//returns true if it does
	public static boolean getPlayer(String name, String password)
	{
		for(int i=0;i<100;i++)
		{
			//player index in players database and his password index 
			//in passwords is same so we are checking if player and their password matches
			if(players.get(i).equals(name) && passwords.get(i).equals(password))
			{
				return true;
			}
		}
		return false;
	}

	//puts player in hashmap  **=>
	public static void putPlayerInHashMap(int playerId,Player player)
	{
		hmap.put(playerId,player);
	}

	//pull moves from the buffer
	public Buffer getBuffer () {
		return buffer;
	}

	//notify the players to update their game state
	private void notifyPlayers () {

	}

	//updates the game interface with the new moves coming from the players
	private synchronized void updateGameInterface () {
		
		
		while(running) {
			String playerMove = buffer.take();
			List<String> elephantList = Arrays.asList(playerMove.split("/"));
			
			
		}
	}

	private synchronized Thread addThread () {
		return new Thread();
	}


	private synchronized void removePlayer (Player removePlayer) {
		hmap.remove(removePlayer);
	}

	public static ConcurrentNavigableMap<Integer,String> getPlayers() {
		return players;
	}


	public static void setPlayers(ConcurrentNavigableMap<Integer,String> players) {
		Server.players = players;
	}

	public static ConcurrentNavigableMap<Integer, String> getPasswords() {
		return passwords;
	}

	/**
	 * @return the db
	 */
	public static DB getDb() {
		return db;
	}

}
