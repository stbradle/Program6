/**
 * Simple Queue Interface
 *
 * @author Steven Bradley
 *
 * @version FALL 2016
 * @version CPE 103
 *
 */

public interface SimpleQueue<T>{


	//METHODS
	
	public T dequeue();
	public void enqueue(T element);
	public T peek();
	public int size();
}
