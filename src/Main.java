import java.util.HashMap;

public class Main {
static Boolean existed = false;
	public static void main(String[] args) {
			
				System.out.println("Checking if Login details matches with"
						+ " the database :) + IF TRUE then Start State");
				
				Server server = new Server();
				server.LoginData();
				
				
				for(int i=0; i<101 ; i++) {
					Player p1=new Player("Player" + i, i);
					if(server.login(p1, i))
						{
						System.out.println("Login was correct");
						}
					else {
						System.out.println("Login wasn't correct");
					}
				}
				HashMap<Integer, Player> playerList = new HashMap<Integer, Player>();
				for(int i=0; i<20; i++)
				{
					Player testPlayer = new Player("test" + i, i);
					playerList.put(i, testPlayer);
				}
				
				State gameState = new State();
			
				
				
				gameState.populate(playerList, 20);
				gameState.render();
				
				//****TODO change this one to true after checking with the database
				existed = true;
		
		
				//System.out.println("Creating Player");
				//for(int i=0;i<100;i++)
				//{
				//	Thread p=new Thread(new Player("P"));
				//}
				//Player p=new Player("P1");
				//System.out.println("Starting Game");
				//State g=new State();
				//g.initGame();
				//g.init();
				
				//** TODO :- Pass Moves to G here
			}

}