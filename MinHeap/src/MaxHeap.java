
public class MaxHeap {
	int capacity;
	int[] heap;
	int last = 0;
	
	MaxHeap(int capacity) {
		this.capacity = capacity;
		heap = new int[capacity];
	}
	
	int parent(int index) { return (index - 1) / 2; }
	int leftChild(int index) { return index * 2 + 1; }
	int rightChild(int index) { return index * 2 + 2; }
	
	boolean hasParent(int index) { return index != 0; }
	boolean hasLeftChild(int index) { return leftChild(index) < last; }
	boolean hasRightChild(int index) { return rightChild(index) < last; }
	
	int getParent(int index) { return heap[parent(index)]; }
	int getLeftChild(int index) { return heap[leftChild(index)]; }
	int getRightChild(int index) { return heap[rightChild(index)]; }
	
	void add(int num) {
		ensureSpace(last);
		heap[last++] = num;	
		heapifyUp();
	}
	
	void heapifyUp() {
		int index = last - 1;
		while (hasParent(index) && getParent(index) < heap[index]) {
			swap(parent(index), index);
			index = parent(index);
		}
	}
	
	public int peek() {
		int peek = heap[0];
		if (last > 1) {
			heap[0] = heap[last - 1];
			heap[last - 1] = 0;
			last--;
			heapifyDown();
			return peek;
		}
		else {
			heap[0] = 0;
			return peek;
		}
	}
	
	void heapifyDown() {
		int index = 0;
		while (hasLeftChild(index)) {
			int swapIndex = leftChild(index);
			if (hasRightChild(index) && getLeftChild(index) < getRightChild(index)) {
				swapIndex = rightChild(index);
			}
			
			if (heap[swapIndex] > heap[index]) {
				swap(swapIndex, index);
				index = swapIndex;
			}
			else {
				break;
			}
		}
	}
	
	void swap(int i, int j) {
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	void ensureSpace(int index) {
		if (index < capacity) { 
			return; 
		}
		int[] newHeap = new int[capacity * 2];
		capacity *= 2;
		for (int i = 0; i < heap.length; i++) {
			newHeap[i] = heap[i];
		}
		heap = newHeap;
	}
}
