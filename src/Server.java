
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

	//verify login player **=>
	public synchronized static boolean login (Player player, int password) {
		if(getPlayer(player.getPlayerName(),password))
		{
			putPlayerInHashMap(player.getPlayerID(),player);
			System.out.println("putting player in hashMap, Name: "+player.getPlayerName());
			return true;
		}
		return false;
	}

	//checks if player exists in record and
	//returns true if it does
	public static boolean getPlayer(String name, int password)
	{
		for(int i=0;i<100;i++)
		{
			//player index in players database and his password index 
			//in passwords is same so we are checking if player and their password matches
			if(getPlayers().get(i).equals(name) && password==i)
			{
				return true;
			}
		}
		return false;
	}

	//puts player in hashmap if login was successful **=>
	public static void putPlayerInHashMap(int playerId,Player player)
	{
		hmap.put(playerId,player);
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
		
		while(isRunning()) {
			String stringBuffer = buffer.take();
			List<String> moveList = Arrays.asList(stringBuffer.split("/")); //split the string to get the playerId and move
			int playerId = Integer.parseInt(moveList.get(0));
			int playerMove = Integer.parseInt(moveList.get(1));
			Player currentPlayer = hmap.get(playerId); //Retrieve the player from the player hashmap

			
			UpdateSnake update = new UpdateSnake(currentPlayer, playerMove, gameState);
			
			executorService.execute(update);

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

	public static ConcurrentNavigableMap<String,Integer> getPasswords() {
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


}
