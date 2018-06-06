
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Utils;

public class Server {

	private Buffer buffer;
	private State gameState;
	private boolean running;
	private int processors;
	private ExecutorService executorService;

	//hashmap / looby to store the values of player name 
	//and their corresponding thread
	static HashMap<Integer, Player> hmap;
	//map database where all the records are saved
	static File dbFile;
	private static DB db;
	//open an collection, TreeMap has better performance then HashMap
	private static ConcurrentNavigableMap<Integer,String> players;
	private static ConcurrentNavigableMap<String,Integer> passwords;


	//**CONSTRUCTOR
	public Server () {
		buffer = new Buffer(100);
		gameState = new State();
		running = true;
		processors = Runtime.getRuntime().availableProcessors();
		executorService = Executors.newFixedThreadPool(processors);
		hmap = new HashMap<Integer, Player>();
		dbFile = Utils.tempDbFile();
		db = DBMaker.newFileDB(dbFile)
				.closeOnJvmShutdown()
				.encryptionEnable("password")
				.make();

		players = db.getTreeMap("Players");
		passwords = db.getTreeMap("Passwords");
	}

	
	//Warning ! Don't do anything with this method
	//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
			public synchronized static void loginData()
			{
				for(int i=0;i<100;i++)
				{
					Player player = new Player("Player"+i,i);
					players.put(i, player.getPlayerName());
					passwords.put(""+i,i);
				}
			}
			
			// **<=
			//Warning ! Don't do anything with this method
	//verify login player **=>
	public synchronized static boolean login (Player player, int password) {
		if(getPlayer(player.getPlayerName(),password))
		{
			putPlayerInHashMap(player.getPlayerID(),player);
			return true;
		}
		return false;
	}
	
	//Warning ! Don't do anything with this method
	//checks if player exists in record and
	//returns true if it does
	public static boolean getPlayer(String name, int password)
	{
		if(players.get(password).equals(name) && passwords.get(name).equals(password)) {
			return true;
		}	
		
		return false;
	}
	
	//Warning ! Don't do anything with this method
	//puts player in hashmap 
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
	public synchronized void updateGameInterface () {
		
			String stringBuffer = buffer.take();
			List<String> moveList = Arrays.asList(stringBuffer.split("/")); //split the string to get the playerId and move
			int playerId = Integer.parseInt(moveList.get(0));
			int playerMove = Integer.parseInt(moveList.get(1));
			Player currentPlayer = hmap.get(playerId); //Retrieve the player from the player hashmap
			
			UpdateSnake update = new UpdateSnake(currentPlayer, playerMove, getGameState());
			
			executorService.execute(update);

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

	public static ConcurrentNavigableMap<String, Integer> getPasswords() {
		return passwords;
	}

	/**
	 * @return the db
	 */
	public static DB getDb() {
		return db;
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @return the gameState
	 */
	public State getGameState() {
		return gameState;
	}


}
