
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Utils;

public class Server {
	
	private Buffer buffer;
	private State gameState;
	private HashMap playerStates;
	static HashMap<String, Player> hmap = new HashMap<String, Player>();
	static File dbFile = Utils.tempDbFile();
	static DB db = DBMaker.newFileDB(dbFile)
			.closeOnJvmShutdown()
			.encryptionEnable("password")
			.make();

	//open an collection, TreeMap has better performance then HashMap
	static ConcurrentNavigableMap<Integer,String> map = db.getTreeMap("collectionName");

	//CONSTRUCTOR
	public Server () {

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
		playerStates.remove(removePlayer);
	}



	//*
	//verify login player
	public synchronized static boolean login (Player loginPlayer) {
		System.out.println("This is player name"+loginPlayer.getPlayerName());
		if(getPlayer(loginPlayer.PlayerName))
		{
			putPlayerInHashMap(loginPlayer);
			System.out.println("The player exists in record");
			return true;
		}


		return false;
	}
	
	

	//checks if player exists in record and returns true if it does
	public static boolean getPlayer(String name)
	{
		for(int i=0;i<100;i++)
		{
			if(map.get(i).equals(name))
			{
				return true;
			}
		}
		return false;
	}

	//puts player in hashmap
	public static void putPlayerInHashMap(Player p1)
	{
		if(getPlayer(p1.getPlayerName()))
		{
			hmap.put(p1.getPlayerName(),p1);
		}
	}
	
	
	//Create 100 players and puts them in mapDB
	public static void LoginData()
	{
		for(int i=0;i<100;i++)
		{
			map.put(i,"Player"+i);
		}
		//map.keySet() is now [1,2] even before commit

		db.commit();  //persist changes into disk

		//map.keySet() is now [1,2,3]
		db.rollback(); //revert recent changes
		//map.keySet() is now [1,2]
		System.out.println(map.get(1));
	}

}
