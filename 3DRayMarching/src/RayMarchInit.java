import java.awt.Color;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.linear.*;

class RayMarchInit {
	int partitionSize = 2048;
	int viewDistance = 400;
	
	int numThreads = Runtime.getRuntime().availableProcessors();
	ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
	
	Cam cam;
	ArrayList<Geometry> shapes;
	Color BG;
	
	List<Map.Entry<int[], RealVector>> rays;
	ArrayList<RayMarching> tasks = new ArrayList<RayMarching>();
	
	Color[][] screen;	
	
	RayMarchInit(Cam cam, ArrayList<Geometry> shapes, Color BG) {
		this.cam = cam;
		this.shapes = shapes;
		this.BG = BG;
		screen = new Color[cam.windowSize][cam.windowSize];
		initTasks();
	}
	
	void initTasks() {		
		RealVector center = cam.position.add(cam.direction);
		RealVector corner = center.add(cam.firstCol.mapMultiply(-cam.windowSize / 2)).add(cam.secondCol.mapMultiply(cam.windowSize / 2));
		Map<int[], RealVector> mapped = new HashMap<int[], RealVector>();
		
		double inc = 0.5;		
		for (int i = 0; i < cam.windowSize; i++) {
			for (int j = 0; j < cam.windowSize; j++) {
				RealVector disp = corner.add(cam.firstCol.mapMultiply(i + inc)).add(cam.secondCol.mapMultiply(-j - inc));
				RealVector ray = disp.subtract(cam.position);
				mapped.put(new int[] {i, j}, ray.unitVector());
			}
		}	
		rays = new ArrayList<Map.Entry<int[], RealVector>>(mapped.entrySet());
		partition();
	}
	
	public void partition() {
		int len = rays.size() - rays.size() % partitionSize;
		int chunk = len / partitionSize;
		int startIndex = 0;
		for (int i = 0; i < partitionSize; i++) {
			FMR.DataSplit DS = i < partitionSize - 1 ? new FMR.DataSplit(startIndex, chunk) : new FMR.DataSplit(startIndex, chunk + rays.size() - len);
			tasks.add(new RayMarching(DS, this));
			startIndex = Math.min(startIndex + chunk, rays.size() - chunk);
		}
	}
	
	public void setColor(int[] index, Color color) {
		int i = index[0], j = index[1];
		screen[i][j] = color;
	}	
	
	public void start() {
		long start = System.nanoTime();
		CountDownLatch latch = new CountDownLatch(partitionSize);
		for (RayMarching task : tasks) {
			task.latch = latch;
			executorService.submit(task);
		}	
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.nanoTime() - start;
		System.out.println(1000 / (end / 1_000_000) + " FPS");
	}
}
