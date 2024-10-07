import java.awt.Color;
import org.apache.commons.math3.linear.*;

class Cube extends Geometry {
	double size;
	RealVector center;
	Color color;
	RealVector mov = new ArrayRealVector(new double[] {0, 0, 0});
		
	Cube(RealVector center, double size, Color color) {
		super(center, color);
		this.size = size;
		this.center = center;
		this.color = color;
	}
	
	public double distanceFunction(RealVector point) {
		double dx = Math.abs(point.getEntry(0) - center.getEntry(0)) - size / 2;
        double dy = Math.abs(point.getEntry(1) - center.getEntry(1)) - size / 2;
        double dz = Math.abs(point.getEntry(2) - center.getEntry(2)) - size / 2;

        return Math.max(Math.max(dx, dy), dz);
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