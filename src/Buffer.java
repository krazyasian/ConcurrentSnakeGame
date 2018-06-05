import java.util.HashMap;

public class Buffer {
	// Size of the buffer
	private int n ;
	// The buffer is implemented as an array 
	private HashMap<Integer, Integer> bufferMoves = new HashMap<Integer, Integer>();

	private int[] b = new int[n];
	// The pointers to the append and take positions    
	private int InPtr = 0,OutPtr = 0;
	// The number of items in the buffer   
	private int Count = 0;
	// Constructor takes the size as a parameter    
	public  Buffer(int size) {  
		n = size;

	}
	// initialise the array


	public synchronized void append(int value) {
	}


	public synchronized void append(int id,int move) {
		// If the buffer is full we cannot append to it   
		while (Count == n) { 
			try {             
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Program got interrupted: ");
			}

		}
		// Place the value in the buffer     
		bufferMoves.put(id, move);
		// print out a debug message

		System.out.println(Thread.currentThread().getName() +" added player id: "+id+" move was: "+move+" Count was= "+Count);
		// display the state of the buffer for debug purposes   
		
		// increment the pointer. Note the pointer must wrap around to the start      
		InPtr = (InPtr + 1) % n;
		// Update the count      
		Count = Count + 1;
		// If this is the first item added will the consumer thread know?
		this.notifyAll();

	}

	public synchronized String take () {
		while (Count==0) {
			try { 
				wait();
			} catch (InterruptedException e) 
			{
				
			}
		}   
		int I = b[OutPtr];
		System.out.println(Thread.currentThread().getName()+
				" removed "+I+" at "+OutPtr+" Count was = "+Count);
		
		OutPtr = (OutPtr+1) % n;    
		Count = Count-1;
		notifyAll();
		return bufferMoves.get(OutPtr)+"/"+OutPtr;
	}




	//public synchronized void display() {
		//for (int i=0; i<n; i++) {
			//if (i==InPtr) System.out.print("#");
			//if (i==OutPtr) System.out.print("*");
			//System.out.print(i+":"+b[i]+" ");    }   
		//System.out.println("#InPtr "+InPtr+" *OutPtr "+OutPtr);
	//}


}