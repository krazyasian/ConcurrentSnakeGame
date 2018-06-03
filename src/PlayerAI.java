public class PlayerAI extends Player {

	public PlayerAI(String PlayerName) {
		super(PlayerName);
		resetLastKeyPressed();
		}
	
	private void randomMove() {
		double rand = Math.random()*200;
		if (rand >= 25 && rand < 50) {
			setLastKeyPressed(Move.UP);
		} else if (rand >= 75 && rand < 100) {
			setLastKeyPressed(Move.LEFT);
		} else if (rand >= 125 && rand < 150) {
			setLastKeyPressed(Move.DOWN);
		} else if (rand >= 175 && rand < 200) {
			setLastKeyPressed(Move.RIGHT);
		} else {
			setLastKeyPressed(Move.NONE);
		}
	}
	
	private void waitTime() {
		
	}
}
