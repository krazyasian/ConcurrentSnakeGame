import java.util.HashMap;

public class Main {
	static Boolean existed = false;
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Checking if Login details matches with"
				+ " the database :) + IF TRUE then Start State");

		Server gameServer = new Server();
		loginData(gameServer);
		int players = 4;
		
		for(int i=0; i<players ; i++) {
			Player p1=new Player("Player" + i, i);
			if(gameServer.login(p1, i))
			{
				System.out.println("Login was correct");
				gameServer.getGameState().populate(p1);
			}
			else {
				System.out.println("Login wasn't correct");
			}
		}

		HashMap<Integer, Player> playerList = gameServer.hmap;

//		gameServer.getGameState().populate(playerList, playerList.size());
//		gameServer.getGameState().render();

		while(gameServer.isRunning())
		{
			Thread.sleep(200);
			for (int j=0; j<playerList.size();j++)
			{
				final int a = j;
				
				if(playerList.get(a).getAlive()) {
					Thread thread = new Thread(){
					    public void run(){
					    	gameServer.getBuffer().append(a, (int) Math.floor(Math.random()*4)+1);
							gameServer.updateGameInterface();
					    }
					  };
					
					thread.start();
				}	
				
//				playerList.replace(j, gameServer.getGameState().move(playerList.get(j), (int) Math.floor(Math.random()*4)+1));
			}

			gameServer.getGameState().render();
		}
		
		//Testing 
		String moveAndPlayerID=
		gameServer.getBuffer().take();
		System.out.println("This is move: "+moveAndPlayerID.split("/"));
	}

	//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
	public synchronized static void loginData(Server server)
	{
		for(int i=0;i<4;i++)
		{
			server.getPlayers().put(i,"Player"+i);
			server.getPasswords().put("Player"+i,i);
		}
	}
}