import java.awt.Color;
import java.util.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.FastMath;

class FMR {
	private static Random random = new Random();	
	static final double TOLERANCE = 0.01;
	
	public static double euclideanDistance(double[] v, double[] u) {
        double sum = 0.0;
        for (int i = 0; i < v.length; i++) {
            double diff = v[i] - u[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
	
	public static double rounding(double number, int places) {
		double div = Math.pow(10, places);
		return Math.round(number * div) / div;
	}
	
	public static double lerp(double x, double s1, double e1, double s2, double e2) {
		if (x > e1) return e2;
		if (x < s1) return s2;
		return s2 + (x - s1) * (e2 - s2) / (e1 - s1);
	}
	
	public static double polyLerp(double x, double n) {
		return FastMath.pow(x, n);
	}
	
	public static boolean isClose(double value, double target) {
		return Math.abs(target - value) <= TOLERANCE;
	}
	
	public static double randrange(double a, double b) {
		return a + (b - a) * random.nextDouble();
	}
	
	public static Color interpolate2C(Color C1, Color C2, double ratio) {
        int r = (int) (C1.getRed() * (1 - ratio) + C2.getRed() * ratio);
        int g = (int) (C1.getGreen() * (1 - ratio) + C2.getGreen() * ratio);
        int b = (int) (C1.getBlue() * (1 - ratio) + C2.getBlue() * ratio);

        return new Color(r, g, b);
    }
	
	public static Color interpolate1C(Color C, double ratio) {
        int r = (int) (C.getRed() * ratio);
        int g = (int) (C.getGreen() * ratio);
        int b = (int) (C.getBlue() * ratio);

        return new Color(r, g, b);
    }
	
	public static double smoothMin(double a, double b, double k) {
        double h = Math.max(k - Math.abs(a - b), 0.0) / k;
        return Math.min(a, b) - h * h * k * 0.25;
    }
	
	public static double smoothMax(double a, double b, double epsilon) {
        double absDiff = Math.abs(a - b);
        return Math.max(a, b) + Math.log(1 + Math.exp(-epsilon * absDiff));
    }
	
	public static RealVector modVector(RealVector v, double k) {
		RealVector res = new ArrayRealVector(v);
		for (int i = 0; i < v.getDimension(); i++) {
            res.setEntry(i, v.getEntry(i) % k);
        }
		return res;
	}
	
	public static double smoothstep(double edge0, double edge1, double x) {
	    double t = Math.max(0.0, Math.min(1.0, (x - edge0) / (edge1 - edge0)));
	    return t * t * t * (t * (t * 6 - 15) + 10);
	}
	
	public static class DataSplit {
		public final int startIndex;
		public final int length;
		
		DataSplit(int startIndex, int length) {
			this.startIndex = startIndex;
			this.length = length;
		}
		
		@Override
		public String toString() {
			return startIndex + " -> " + (startIndex + length);
		}
	}
}
