/**
 * Java Class for Priority Queue that Implements SimpleQueue
 *
 * @author Steven Bradley
 *
 * @version CPE103-05 - Paul Hatalsky
 * @version October 19 2016
 */
 
 import java.util.*;

public class PriorityQueue<T extends Comparable<? super T>> implements SimpleQueue<T>
{
	//FIELD VARIABLES
	private ArrayList<T> queue;
	private boolean max;
	
	//CONSTRUCTORS
	
	public PriorityQueue() // min queue
	{
		queue = new ArrayList<>();
		queue.add(null);
	}

	public PriorityQueue(boolean isMax) //max queue when bool is true; min otherwise
	{
		queue = new ArrayList<>();
		queue.add(null);
		max = isMax;
	}
	
	public PriorityQueue(T[] arr, int size) //min queue w/ elements from array
	{
		queue = new ArrayList<>();
		queue.add(null);
		for(int i = 0; i < size; i++)
			enqueue(arr[i]);
	}
	
	public PriorityQueue(T[] arr, int size, boolean isMax) // min/max queue from array 
	{
		queue = new ArrayList<>();
		queue.add(null);
		max = isMax;
		for(int i = 0; i < size; i++)
			enqueue(arr[i]);
	}

	//PUBLIC METHODS
	public T dequeue()
	{
		if(queue.size() == 1)
			throw new NoSuchElementException();
		T temp = queue.get(1);
		percolateDown(queue,max, queue.get(size()), 1, size());
		return temp;
	}

	public void enqueue(T element)
	{
		percolateUp(queue, element,max, queue.size());
	}

	public T peek()
	{
		if(queue.size() == 1)
			throw new NoSuchElementException();
		return queue.get(1);
	}

	public int size()
	{
		return queue.size() - 1;
	}

	public static <E extends Comparable<? super E>> void sort(E[] arr, int size)
	{
		PriorityQueue<E> q = new PriorityQueue<E>(arr, size);
		for(int i = 0; i < size; i++)
		{
			arr[i] = q.dequeue();
		}
	}

	public static <E extends Comparable<? super E>> E kth(E[] arr, int size, int k)
	{
		PriorityQueue<E> q;
		if(k < size-k +1)
		{
			q = new PriorityQueue<E>(arr,k);
			for(int i = k; i < size; i++)
			{
				if(arr[i].compareTo(q.peek()) > 0)
				{
					q.dequeue();
					q.enqueue(arr[i]);
				}
			}
		}
		else
		{
			q = new PriorityQueue<E>(arr, size - k + 1, true);
			for(int i = size - k+1; i < size; i++)
			{
				if(arr[i].compareTo(q.peek()) < 0)
				{
					q.dequeue();
					q.enqueue(arr[i]);
				}
			}
		}
		return q.peek();
	}
	
	//PRIVATE RECURSIVE METHODS
	private void percolateUp(ArrayList<T> a, T element, boolean max, int index)
	{
	  int parent = index/2;
	  T temp = a.get(parent);
	  if(!max) // for min queue
	  {
		if(index == 1 || element.compareTo(temp) >= 0) //if at root or if element is greater than parent
		{
			if(index != a.size())
			{
				a.set(index, element);
			}
			else
				a.add(index, element);
		}
		else
		{
			if(index != a.size()) //if not at the end of the tree
			{
				a.set(index, temp);
				
			}
			else
			{
				// a.set(index, temp); //swaps parent down to childs index
				a.add(temp); //add parent to end
				a.set(parent, element); //swaps element to parent index
			}
			percolateUp(a, element, max, parent); 
		}
		
	  }
	  else // for max queue
	  {
		if(index == 1 || element.compareTo(temp) <= 0)
		{
			if(index != a.size())
			{
				a.set(index, element);
			}
			else
				a.add(index, element);
		}
		else
		{
			if(index != a.size())
			{
				a.set(index, temp);
				
			}
			else
			{
				a.add(temp);
				a.set(parent, element);
			}
			percolateUp(a, element, max, parent);
		}
	  }
	}
	
	private void percolateDown(ArrayList<T> a, boolean max, T element, int index, int size)
	{
		int left = 2*index, right = 2*index +1;
		if(right <= size) // if both children exist
		{
			T leftChild = a.get(left), rightChild = a.get(right);
			if(!max) //if min queue
			{
				if(!(element.compareTo(leftChild) < 0 && element.compareTo(rightChild) < 0)) // if element is less than both children
				{
					if(leftChild.compareTo(rightChild) < 0) //if leftChild is less than rightChild
					{
						a.set(index,leftChild);
						percolateDown(a, max, element, left, size);
					}
					else
					{
						a.set(index, rightChild);
						percolateDown(a, max,element,right, size);
					}
				}
				else
				{
					a.set(index, element);
					a.remove(size);
				}
			}
			else //max queue
			{
				if(!(element.compareTo(leftChild) > 0 && element.compareTo(rightChild) > 0))//if element is greater than both children
				{
					if(leftChild.compareTo(rightChild) > 0)// if leftChild is greater than rightChild
					{
						a.set(index,leftChild);
						percolateDown(a, max, element, left,size);
					}
					else
					{
						a.set(index, rightChild);
						percolateDown(a, max,element,right,size);
					}
				}
				else
				{
					a.set(index, element);
					a.remove(size);
				}
			}
		}
		else //only one child exists
		{
			a.set(index, element);
			a.remove(size); //removes element from original location
		}
	}
}