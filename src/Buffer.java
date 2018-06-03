public class Buffer {
 // Size of the buffer
	private int N ;
    // The buffer is implemented as an array    
	private int[] B = new int[N];
    // The pointers to the append and take positions    
	private int InPtr = 0,OutPtr = 0;
    // The number of items in the buffer   
	private int Count = 0;
	// Constructor takes the size as a parameter    
	public  Buffer(int size) {  
		N = size;
	
	}
        // initialise the array        
	
	
public synchronized void append(int value) {
    // If the buffer is full we cannot append to it   
	while (Count == N) { 
		 try {             
			 this.wait();
			 } catch (InterruptedException e) {
				 System.out.println("Program got interrupted: ");
			 }

	}
      // Place the value in the buffer     
    	B[InPtr] = value;
      // print out a debug message
    	
    	System.out.println(Thread.currentThread().getName() +" added "+value+" at "+InPtr+" Count was= "+Count);
      // display the state of the buffer for debug purposes   
    	display();
      // increment the pointer. Note the pointer must wrap around to the start      
    	InPtr = (InPtr + 1) % N;
      // Update the count      
    	Count = Count + 1;
      // If this is the first item added will the consumer thread know?
    	this.notifyAll();
    
	}

public synchronized int take () {
	while (Count==0) {
		try { wait();
		} catch (InterruptedException e) {}
		}   int I = B[OutPtr];
		System.out.println(Thread.currentThread().getName()+
        " removed "+I+" at "+OutPtr+" Count was = "+Count);
		display();
        OutPtr = (OutPtr+1) % N;    Count = Count-1;
        notifyAll();    return I;
        }




public synchronized void display() {
	for (int i=0; i<N; i++) {
		if (i==InPtr) System.out.print("#");
		if (i==OutPtr) System.out.print("*");
		System.out.print(i+":"+B[i]+" ");    }   
	System.out.println("#InPtr "+InPtr+" *OutPtr "+OutPtr);
	}

  
}