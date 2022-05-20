
public class ArrayStack<T> implements StackADT<T>{

	
	private T[] array;
	private int top;
	
	
	public ArrayStack() {
		array = (T[]) (new Object[10]);	// array size 10
		top = 9;
		
	} // end method

	public ArrayStack(int arraySize) {		// takes in array size paramater 
		array = (T[]) (new Object[arraySize]);;
		top = arraySize - 1;
				
	}
	
	public void push(T element) {
		
		if (size() == getLength() -1) { // if stack is full
			
			T[] tempArray = (T[])new Object[getLength() + 5]; 	// new length + 5
			int counter = tempArray.length-1;
		for (int x = size();  x >= top; x--) { // transfer elements into larger array starting at end and working down
			tempArray[counter] = array[x];		
			counter--;					
			
		} // end for
		top = counter +1; // update counter
		array = tempArray;
		
		} // end if

		array[top] = element; // add element top top position and decrement top
		top--;
		
	}
	
	public T pop() throws StackException{
		if (isEmpty()) {
			throw new StackException("stack");
			}
		
		T tempElement = array[top+1];	// store last element and remove it
		top = top+1;					
		
		return tempElement;				
	
	}
	
	public T peek() throws StackException{
		if (isEmpty()) {
			throw new StackException("stack");			
			}
		
		return array[top + 1];	
		
	}
	
	public boolean isEmpty() {	// return true if stack is empty
		if (size() == 0) {
			return true;				
		}
		
		return false;
	}
	
	
	public int size() { // returns number elements in stack	
		
		return (array.length-1 - top);		
		
	}
	
	public int getLength() {	// return num slots in array
		
		return array.length;			
		
	}
	
	public int getTop() {	// return top of stack value
		
		return top;
		
	}
	
	public String toString() {		// return a string representaton of the stack
	
	String str = "Stack: ";
				
	if (isEmpty())	{
		
		return "The stack is empty.";
	}
	
	else {
		for (int x = top + 1; x < getLength(); x++) { // transfer elements into larger array starting at top
			
			if (x == getLength() - 1){  // if item is the last element, print without a comma or space
				
				str += array[x] + ".";	
			
			} // end if
			
			else {					//print item with a ","
				
				str += array[x] + ", ";
				
			} // end else
			
								
		} // end for
		
	} // end else
	
	return str;
		
	} // end toString method
	
	
	
	
	
} // end CLASS
