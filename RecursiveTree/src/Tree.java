import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.ArrayList;

public class Tree {
	final int SEED = 0;
	final double min = 15d, max = 45d;
	final double minScl = 0.5d, maxScl = 0.9d;
	double depth;
	double branchLength;
	
	double windSpeed = 0;
	double windInc = 0.01;
	double noisePos;
	
	Point2D.Double start;
	Point2D.Double anchor;
	Random random = new Random();
	ArrayList<ArrayList<Point2D.Double>> branches = new ArrayList<>();
	ArrayList<Double> treeDepths = new ArrayList<>();
	ArrayList<Double> angles = new ArrayList<>();
	
	Tree(double n, double branchLength, Point2D.Double start) {
		this.depth = n;
		this.branchLength = branchLength;
		this.start = start;
		this.anchor = new Point2D.Double(start.getX(), start.getY() - branchLength);
		
		storeLine(start, anchor);
		storeData(depth, 0.0);
		
		branchGeneration(depth, start, anchor);
	}
	
	void modify() {
		storeLine(this.start, this.anchor);
		branchModification(depth, 1, this.start, this.anchor);
		
		windSpeed = OpenSimplexNoise.noise2_ImproveX(SEED, noisePos, 0) * 10;
		noisePos += windInc;
	}
	
	void storeLine(Point2D.Double startPoint, Point2D.Double endPoint) {
		ArrayList<Point2D.Double> key = new ArrayList<>();
		key.add(startPoint);
		key.add(endPoint);
		branches.add(key);
	}
	
	void storeData(double n, double angle) {
		treeDepths.add(n);
		angles.add(angle);
	}
	
	void setLine(int index, Point2D.Double startPoint, Point2D.Double endPoint) {
		ArrayList<Point2D.Double> key = new ArrayList<>();
		key.add(startPoint);
		key.add(endPoint);
		branches.set(index, key);
	}
	
	double getRandomizedAngle() {
		return min + (max - min) * random.nextDouble();
	}
	
	public static double lerp(double x, double s1, double e1, double s2, double e2) {
		if (x > e1) return e2;
		if (x < s1) return s2;
		return s2 + (x - s1) * (e2 - s2) / (e1 - s1);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	Point2D.Double rotateLine(Point2D.Double startPoint, Point2D.Double endPoint, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        
        double deltaX = endPoint.getX() - startPoint.getX();
        double deltaY = endPoint.getY() - startPoint.getY();
        
        double rotatedX = endPoint.getX() + (deltaX * Math.cos(angleRadians) - deltaY * Math.sin(angleRadians));
        double rotatedY = endPoint.getY() + (deltaX * Math.sin(angleRadians) + deltaY * Math.cos(angleRadians));
        
        return new Point2D.Double(rotatedX, rotatedY);
	}
	
	public static Point2D.Double scaleLine(Point2D.Double startPoint, Point2D.Double endPoint, double scaleFactor) {
        double scaledEndX = startPoint.getX() + (endPoint.getX() - startPoint.getX()) * scaleFactor;
        double scaledEndY = startPoint.getY() + (endPoint.getY() - startPoint.getY()) * scaleFactor;
        
        return new Point2D.Double(scaledEndX, scaledEndY);
    }
	
	void createBranch(double n, Point2D.Double startPoint, Point2D.Double endPoint, double factor, double direction) {
		double angle = getRandomizedAngle();
		Point2D.Double rotated = rotateLine(startPoint, endPoint, direction * angle);
		Point2D.Double next = scaleLine(endPoint, rotated, factor);
		storeLine(endPoint, next);
		storeData(n, angle);
		branchGeneration(n - 1, endPoint, next);
	}
	
	void branchGeneration(double n, Point2D.Double startPoint, Point2D.Double endPoint) {
		if (n <= 0) {
			return;
		}
		double factor = lerp(n / depth, 1 / depth, 1, minScl, maxScl);		
		
		createBranch(n, startPoint, endPoint, factor, 1);
		createBranch(n, startPoint, endPoint, factor, -1);
	}
	
	int branchModification(double n, int index, Point2D.Double startPoint, Point2D.Double endPoint) {
		if (n <= 0) {
			return index;
		}
		double factor = lerp(n / depth, 1 / depth, 1, minScl, maxScl);
		double stiffness = lerp(n, 1, depth, 1, 0.3);
		double twist = windSpeed;
		
		double angle = angles.get(index);
		
		Point2D.Double rotated = rotateLine(startPoint, endPoint, angle + twist);
		Point2D.Double next = scaleLine(endPoint, rotated, factor);	
		storeLine(endPoint, next);
		int i = branchModification(n - 1, index + 1, endPoint, next);
		
		angle = angles.get(i);
		
		rotated = rotateLine(startPoint, endPoint, -(angle - twist / 2));
		next = scaleLine(endPoint, rotated, factor);
		storeLine(endPoint, next);
		return branchModification(n - 1, i + 1, endPoint, next);
	}
}