import org.apache.commons.math3.linear.*;

class Cam {
	RealVector position;
	double FOV;
	double zoom = 0;
	int windowSize;
	
	RealVector direction = new ArrayRealVector(new double[] {0, 0, 0});
	RealVector firstCol = new ArrayRealVector(new double[] {1, 0, 0});
	RealVector secondCol = new ArrayRealVector(new double[] {0, 1, 0});
	
	Cam(RealVector position, double FOV, int windowSize) {
		this.position = position;
		this.FOV = FOV;
		this.windowSize = windowSize;
		direction.setEntry(2, FOV);
	}
	
	public void zooming(double inc) {
		direction = direction.unitVector().mapMultiply(FOV + (zoom += inc));
	}
	
	public void revertZoom() {
		direction = direction.unitVector().mapMultiply(FOV);
		zoom = 0;
	}
}
