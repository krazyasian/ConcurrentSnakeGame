public class PlayerAI extends Player {

	public PlayerAI(String PlayerName, int PlayerID, Buffer buffer, Server server) {
		super(PlayerName, PlayerID, buffer, server);
		this.alive = true;
	}
	
	public void run() {
		myServer.getGameState().populate(this);
		resetLastKeyPressed();
		randomMove();
		sendToBuffer();
	}
	
	private void randomMove() {
		while(myServer.isRunning()) {
			waitTime();
			double rand = Math.random()*100;
			if (rand > 0 && rand < 25) {
				setLastKeyPressed(Move.UP);
				move = 3;
			} else if (rand >= 25 && rand < 50) {
				setLastKeyPressed(Move.DOWN);
				move = 2;
			} else if (rand >= 50 && rand < 75) {
				setLastKeyPressed(Move.LEFT);
				move = 4;
			} else if (rand >= 75 && rand < 100) {
				setLastKeyPressed(Move.RIGHT);
				move = 1;
			} else {
				setLastKeyPressed(Move.NONE);
			}
		}	
	}
	
	@Override
	public void sendToBuffer() {
		Thread thread = new Thread(){
		    public void run(){
		    	while(myServer.isRunning()) {
		    		try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myBuffer.append(playerID, move);
					myServer.updateGameInterface();
				}
		    }
		  };
		
		thread.start();
	}
	
	private void waitTime() {
		try {
			Thread.sleep((long) (Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
