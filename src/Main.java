import java.util.HashMap;

public class Main {
	static Boolean existed = false;
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Checking if Login details matches with"
				+ " the database :) + IF TRUE then Start State");

		Server gameServer = new Server();
		int numbers = 10;
		int players = 2;
		
		//Creating login data for quantity players
		loginData(gameServer,numbers);
		for(int i=0; i<players ; i++) {
			Player p1=new Player("Player" + i, i, gameServer.getBuffer(), gameServer);
			if(gameServer.login(p1, i))
			{
				System.out.println("Login was correct");
				PlayerWindow  playerWindow= new PlayerWindow(p1, gameServer.getGameState());
			}
			else {
				System.out.println("Login wasn't correct");
			}
		}
		
		//login for AI players
		for(int i=players; i<numbers ; i++) {
			Player p1=new Player("Player" + i, i, gameServer.getBuffer(), gameServer);
			if(gameServer.login(p1, i))
			{
				System.out.println("Login was correct");
			}
			else {
				System.out.println("Login wasn't correct");
			}
		}

		HashMap<Integer, Player> playerList = gameServer.hmap;

		gameServer.getGameState().populate(playerList, playerList.size());
		gameServer.getGameState().render();

//		while(gameServer.isRunning())
//		{
//			
			for (int j=players; j<playerList.size();j++)
			{
				final int a = j;

					Thread thread = new Thread(){
					    public void run(){
					    	while(gameServer.isRunning()) {
					    		try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    		if(playerList.get(a).getAlive()) {
						    		gameServer.getBuffer().append(a, (int) Math.floor(Math.random()*4)+1);
									gameServer.updateGameInterface();
						    	}
					    	}
					    	
					    }
					  };
					
					thread.start();

				
//				playerList.replace(j, gameServer.getGameState().move(playerList.get(j), (int) Math.floor(Math.random()*4)+1));
			}

//			gameServer.getGameState().render();
//		}
//		
//		//Testing 
//		String moveAndPlayerID=
//		gameServer.getBuffer().take();
//		System.out.println("This is move: "+moveAndPlayerID.split("/"));
	}

	//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
	public synchronized static void loginData(Server server,int numbers)
	{
		for(int i=0;i<numbers;i++)
		{
			server.getPlayers().put(i,"Player"+i);
			server.getPasswords().put("Player"+i,i);
		}
	}
}