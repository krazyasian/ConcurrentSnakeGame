import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;



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
		
//		state.populate(player);
		init();
		
	}
	
	private void init() {
//		canvas = _state.canvas;
//		frame = _state.frame;
//		graph = _state.graph;
//		strategy = _state.strategy;
		grid = _state.grid;
		canvas.addKeyListener(this);
		populate(_player);
		graph.drawString("SCORE = " + _player.getLength(), 10, 20);
		render();
		
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
