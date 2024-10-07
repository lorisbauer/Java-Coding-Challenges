import java.util.*;

public class Main {
	public static void main(String[] args) {
		int size = 100;
		int min = 1;
		int max = 100;
		Random random = new Random();
		
		MinHeap minHeap = new MinHeap(size);
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++) {
			int k = (int) (random.nextDouble() * (max - min) + min);
			array.add(k);
			minHeap.add(k);
		}
		System.out.println(array);
		for (int j = 0; j < size; j++) {
			int peek = minHeap.peek();
			array.set(j, peek);
			
		}
		System.out.println(array + "\n");
		
		MaxHeap maxHeap = new MaxHeap(size);
		array = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++) {
			int k = (int) (random.nextDouble() * (max - min) + min);
			array.add(k);
			maxHeap.add(k);
		}
		System.out.println(array);
		for (int j = 0; j < size; j++) {
			int peek = maxHeap.peek();
			array.set(j, peek);
			
		}
		System.out.println(array);
	}
	
}
