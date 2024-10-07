import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;

public class MergeSort {
	ArrayList<double[]> progression = new ArrayList<>();	
	
	MergeSort(int n, int range) {
		double[] A = DoubleStream.generate(() -> new Random().nextInt(range)).limit(n).toArray();
		mergeSort(A, 0, A.length - 1);
	}
	
	public void mergeSort(double[] A, int l, int r){
		if (l < r) {
			int middle = (l + r) / 2;
			mergeSort(A, l, middle);
			mergeSort(A, middle + 1, r);
			merge(A, l, middle, r);
		}
	}
	
	public void merge(double[] A, int l, int m, int r) {
		double[] B = new double[r - l + 1];
		int i = l;
		int j = m + 1;
		int k = 0;
		
		while (i <= m && j <= r) {
			if (A[i] < A[j]) {
				B[k] = A[i];
				i++;
				k++;
			}
			else {
				B[k] = A[j];
				j++;
				k++;
			}
		}
		
		while (i <= m) {
			B[k] = A[i];
			i++;
			k++;
		}
		
		while (j <= r) {
			B[k] = A[j];
			j++;
			k++;
		}
				
		for (int index = 0; index < B.length; index++) {
			A[l + index] = B[index];
			addToProgression(Arrays.copyOf(A, A.length));
		}		
	}
	
	public void addToProgression(double[] A) {
		progression.add(A);
	}

}
