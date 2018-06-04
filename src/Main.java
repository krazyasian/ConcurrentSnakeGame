import java.util.HashMap;

public class Main {
static Boolean existed = false;
	public static void main(String[] args) {
			
				System.out.println("Checking if Login details matches with"
						+ " the database :) + IF TRUE then Start State");
				
				Server server = new Server();

				server.LoginData();
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
				
				System.exit(0);
				//** TODO :- Pass Moves to G here
			}

}