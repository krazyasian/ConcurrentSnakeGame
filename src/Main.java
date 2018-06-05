import java.util.HashMap;

public class Main {
	static Boolean existed = false;
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Checking if Login details matches with"
				+ " the database :) + IF TRUE then Start State");
		//Instance of server
		Server server = new Server();
		//Creates 100 players data
		server.loginData();
		int players = 100;
		for(int i=0; i<players ; i++) {
			Player p1=new Player("Player"+i, i);
			String password = ""+i;
			//Checks if "p1" with "password" exists and if it does, then adds it to **server.hmap
			server.login(p1, password);
		}
		

		//We are getting HashMap value from server and putting it to playerList
		HashMap<Integer, Player> playerList = server.hmap;
		State gameState = new State();
		gameState.populate(playerList, playerList.size());
		gameState.render();
		

		for (int i=0; i<100; i++)
		{
			Thread.sleep(200);
			for (int j=0; j<playerList.size();j++)
			{
				//this line adds player with id "j" to buffer with "random move"
				server.getBuffer().append(j, (int) Math.floor(Math.random()*4)+1);
				//playerList.replace(j, gameState.move(playerList.get(j), (int) Math.floor(Math.random()*4)+1));
			}

			gameState.render();
		}
		
		//Testing 
		String moveAndPlayerID=
				server.getBuffer().take();
		System.out.println("This is move: "+moveAndPlayerID.split("/"));
		
		
	}
	
	
	//System.exit(0);

}