
public class UpdateSnake implements Runnable {
	private Player _player;
	private int _move;
	private State _state;
	static int count = 0;
	

	public UpdateSnake(Player currentPlayer, int playerMove, State gameState) {
		_player = currentPlayer;
		_move = playerMove;
		_state = gameState;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		_state.move(_player, _move);
		count++;
		
//		if(count > 1000) {
//			_state.render();
//		}
	
	}

}
