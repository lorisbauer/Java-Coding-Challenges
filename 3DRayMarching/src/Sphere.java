import java.awt.Color;
import org.apache.commons.math3.linear.*;

class Sphere extends Geometry {
	RealVector center;
	double radius;
	
	Color color;
	RealVector mov = new ArrayRealVector(new double[] {0, 0, 0});
	
	Sphere(RealVector center, double radius, Color color) {
		super(center, color);
		this.center = center;
		this.radius = radius;
		this.color = color;
	}
	
	public double distanceFunction(RealVector point) {        
		double dist = point.getDistance(center) - radius;
		return dist;
	}
	
	public void update() {
		center = center.add(mov);
	}
	
	public void changeMov(RealVector v) {
		mov = v;
	}
	
	public Color getColor() {
		return color;
	}
}