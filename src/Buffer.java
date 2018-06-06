import java.util.ArrayList;
import java.util.HashMap;

public class Buffer {
	
	private int n ; // Size of the buffer	
	private ArrayList<String> bufferMoves; // The buffer is implemented as an array
	
	//Constructor takes the size of the buffer
	public  Buffer(int size) {  
		n = size;
		//initialise the array
		bufferMoves = new ArrayList<String>();
	}
	
	public synchronized void append(int id,int move) {
		// If the buffer is full we cannot append to it   
		while (bufferMoves.size() == n) { 
			try {             
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Program got interrupted: ");
			}

		}
		// Place the value in the buffer     
		bufferMoves.add(id + "/" + move);
		// print out a debug message
		System.out.println(Thread.currentThread().getName() +" added player id: "+id+" move was: "+move);
	}

	public synchronized String take () {
		while (bufferMoves.size()==0) {
			try { 
				wait();
			} catch (InterruptedException e) 
			{
				
			}
		}

		String playerMove = bufferMoves.get(0);
		bufferMoves.remove(0);
		notifyAll();
		return playerMove;
	}


}