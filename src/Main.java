import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Checking if Login details matches with"
				+ " the database :) + IF TRUE then Start State");

		Server server = new Server();
		loginData(server);

		int players = 5;
		for(int i=0; i<5 ; i++) {
			Player p1=new Player("Player" + i, i);
			if(server.login(p1, i))
			{
				System.out.println("Login was correct");
			}
			else {
				System.out.println("Login wasn't correct");
			}
		}

		HashMap<Integer, Player> playerList = server.hmap;


		State gameState = new State();



		gameState.populate(playerList, playerList.size());
		gameState.render();

		for (int i=0; i<20; i++)
		{
			Thread.sleep(500);
			playerList.replace(0, gameState.move(playerList.get(0), 1));
			gameState.render();
		}


		System.exit(0);
		//** TODO :- Pass Moves to G here
		server.getDb().commit();  //persist changes into disk

	}
	
	//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
	public synchronized static void loginData(Server server)
	{
		for(int i=0;i<100;i++)
		{
			server.getPlayers().put(i,"Player"+i);
			server.getPasswords().put("Player"+i,i);
		}
	}

}