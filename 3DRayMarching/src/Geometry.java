import java.awt.Color;
import org.apache.commons.math3.linear.*;

abstract class Geometry {
	RealVector center;
	Color color;
	
	Geometry(RealVector center, Color color) {
		this.center = center;
		this.color = color;
	}
	abstract double distanceFunction(RealVector center);
	abstract Color getColor();
	abstract void update();
	abstract void changeMov(RealVector v);
}