import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;



public class PlayerWindow extends State implements KeyListener, WindowListener {
// KEYS MAP
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	
	private Player _player;
	private State _state;
	
	public PlayerWindow (Player player, State state) {
		super();
		_player = player;
		_state = state;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(_player.getPlayerName() + "'s Snake");
//		state.populate(player);
		init();
		
	}
	
	private void init() {
		grid = _state.grid;
		canvas.addKeyListener(this);
		populate(_player);
		
		renderLoop();
		
	}
	
	public void render()
	{
		int gridUnit = gameHeight / gameSize;
		canvas.paint(graph);
		do {
			do {
				graph = strategy.getDrawGraphics();

				
				graph.setColor(new Color(230, 230, 255));

				// Draw Background
				graph.setColor(new Color(0, 0, 102));

				graph.fillRect(0, 0, gameWidth, gameHeight);
				
				int gridCase = EMPTY;
				for (int i = 0; i < gameSize; i++) {

					for (int j = 0; j < gameSize; j++) {
						String key = i + "-" + j;
						gridCase = grid.get(key).getType();
						switch (gridCase) {
						case SNAKE:
							graph.setColor(Color.yellow);
							graph.fillOval(i * gridUnit, j * gridUnit,
									gridUnit, gridUnit);
							break;
						case FOOD_BONUS:
							graph.setColor(Color.green);
							graph.fillOval(i*gridUnit, j* gridUnit,
									(gridUnit/2)+3, (gridUnit/2)+3);
							break;
						case FOOD_NEGATIVE:
							graph.setColor(Color.red);
							graph.fillOval(i*gridUnit, j* gridUnit,
									(gridUnit/2)+3, (gridUnit/2+3));
							break;
						default:
							break;
						}
					}
				}
				graph.setColor(Color.WHITE);
				graph.drawString("SCORE = " + _player.length, 10, 20);
				
				graph.dispose();
			} while (strategy.contentsRestored());
			
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
		} while (strategy.contentsLost());
	}
	
	
	/**
	 * @return the _state
	 */
	public State get_state() {
		return _state;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		_player.keyPressed(e);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
