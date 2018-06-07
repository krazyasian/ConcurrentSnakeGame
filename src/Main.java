import java.util.HashMap;

public class Main {
	static Boolean existed = false;
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Checking if Login details matches with"
				+ " the database :) + IF TRUE then Start State");

		Server gameServer = new Server();
		int numbers = 5;
		int players = 1;
		
		//Creating login data for quantity players
		loginData(gameServer,numbers);
		for(int i=0; i<players ; i++) {
			final int a = i;
			
			Thread thread = new Thread(){
			    public void run(){
			    	Player p1=new Player("Player" + a, a, gameServer.getBuffer(), gameServer);
					if(gameServer.login(p1, a))
					{
						System.out.println("Login was correct");
						PlayerWindow  playerWindow= new PlayerWindow(p1, gameServer.getGameState());
					}
					else {
						System.out.println("Login wasn't correct");
					}
			    	
			    }
			  };
			
			thread.start();
		}
		
		//login for AI players
		for(int i=players; i<numbers ; i++) {
			final int a = i;
			Thread thread = new Thread(){
			    public void run(){
			    	Player p1=new PlayerAI("Player" + a, a, gameServer.getBuffer(), gameServer);
					if(gameServer.login(p1, a))
					{
						System.out.println("Login was correct");
						p1.run();
					}
					else {
						System.out.println("Login wasn't correct");
					}	    	
			    }
			  };
			
			thread.start();
		}

	}

	//Create 100 players and their 100 passwords and puts them in database(mapDB) **=>
	public synchronized static void loginData(Server server,int numbers)
	{
		for(int i=0;i<numbers;i++)
		{
			Server.getPlayers().put(i,"Player"+i);
			Server.getPasswords().put("Player"+i,i);
		}
	}
}