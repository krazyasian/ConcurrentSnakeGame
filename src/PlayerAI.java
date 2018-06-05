//Currently unused

public class PlayerAI extends Player {

	public PlayerAI(String PlayerName, int PlayerID) {
		super(PlayerName, PlayerID);
		resetLastKeyPressed();
		}
	
	private void randomMove() {
		double rand = Math.random()*100;
		if (rand > 0 && rand < 25) {
			setLastKeyPressed(Move.UP);
		} else if (rand >= 25 && rand < 50) {
			setLastKeyPressed(Move.LEFT);
		} else if (rand >= 50 && rand < 75) {
			setLastKeyPressed(Move.DOWN);
		} else if (rand >= 75 && rand < 100) {
			setLastKeyPressed(Move.RIGHT);
		}
	}
}
