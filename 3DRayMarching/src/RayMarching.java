import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.math3.linear.*;

class RayMarching implements Runnable {
	final double CRITMIN = 0.01;
	final double CRITMAX = 300;
	final double CRITDIST = 1000;
	
	FMR.DataSplit DS;
	RayMarchInit RMI;
	CountDownLatch latch;
	
	boolean collided = false;
	Color shapeColor = null;
	
	RayMarching(FMR.DataSplit DS, RayMarchInit RMI) {
		this.DS = DS;
		this.RMI = RMI;
	}
	
	@Override
	public void run() {
		for (int i = DS.startIndex; i < DS.startIndex + DS.length; i++) {
			int[] index = RMI.rays.get(i).getKey();
			RealVector ray = RMI.rays.get(i).getValue();
			
			double k = 0;	
			int j = 0;
			while (k < RMI.viewDistance) {
				RealVector pos = RMI.cam.position.add(ray.mapMultiply(k));
				double inc = findClosestSmooth(pos);
				
				if (j++ >= CRITMAX || inc > CRITDIST) {
					break;
				}
				
				if (collided) {	
					Color shapeC = FMR.interpolate1C(shapeColor, FMR.lerp(k / RMI.viewDistance, 0, 1, 1, 0));
					Color color = FMR.interpolate2C(shapeC, RMI.BG, FMR.polyLerp(k / RMI.viewDistance, 6));
					RMI.setColor(index, color);
					break;
				}
				k += inc;
			}
			
			if (!collided) {
				RMI.setColor(index, RMI.BG);
			} else {
				shapeColor = null;
				collided = false;
			}
		}
		latch.countDown();
	}
	
	double findClosest(RealVector point) {
        double minDist = Double.MAX_VALUE;

        for (int i = 0; i < RMI.shapes.size(); i++) {
            double distance = RMI.shapes.get(i).distanceFunction(point);
            
            if (distance < minDist) {
            	minDist = distance;
            	if (distance <= CRITMIN) { 
            		shapeColor = RMI.shapes.get(i).getColor();
            		collided = true;
            		break; 
            	}
            }
        }
        return minDist;
    }
	
	double findClosestRep(RealVector point) {
		double k = 50;
        double minDist = RMI.shapes.get(0).distanceFunction(FMR.modVector(point, k));
        
        if (minDist <= CRITMIN) {
        	shapeColor = RMI.shapes.get(0).getColor();
    		collided = true;
    		return minDist; 
        }

        for (int i = 1; i < RMI.shapes.size(); i++) {
            double distance = RMI.shapes.get(i).distanceFunction(point);
            
            if (distance < minDist) {
            	minDist = distance;
            	if (distance <= CRITMIN) { 
            		shapeColor = RMI.shapes.get(i).getColor();
            		collided = true;
            		break; 
            	}
            }
        }
        return minDist;
    }
	
	double findClosestInt(RealVector point) {
		double d1 = RMI.shapes.get(0).distanceFunction(point);
		double d2 = RMI.shapes.get(1).distanceFunction(point);
        double minDist = Math.max(d1, -d2);
        
        if (minDist <= CRITMIN) {
        	shapeColor = RMI.shapes.get(1).getColor();
    		collided = true;
        	return minDist;
        }
        
        for (int i = 2; i < RMI.shapes.size(); i++) {
            double distance = RMI.shapes.get(i).distanceFunction(point);
            
            if (distance < minDist) {
            	minDist = distance;
            	if (distance <= CRITMIN) { 
            		shapeColor = RMI.shapes.get(i).getColor();
            		collided = true;
            		break; 
            	}
            }          
        }
        return minDist;
    }
	
	double findClosestSmooth(RealVector point) {
		double a = RMI.shapes.get(0).distanceFunction(point);
		double b = RMI.shapes.get(1).distanceFunction(point);
        double minDist = FMR.smoothMin(a, b, 20);
        
        if (minDist <= CRITMIN) {
        	shapeColor = FMR.interpolate2C(RMI.shapes.get(0).getColor(), RMI.shapes.get(1).getColor(), FMR.polyLerp(b / (a + b), 0.5));
    		collided = true;
        	return minDist;
        }
        
        for (int i = 2; i < RMI.shapes.size(); i++) {
            double distance = RMI.shapes.get(i).distanceFunction(point);
            
            if (distance < minDist) {
            	minDist = distance;
            	if (distance <= CRITMIN) { 
            		shapeColor = RMI.shapes.get(i).getColor();
            		collided = true;
            		break; 
            	}
            }          
        }
        return minDist;
    }
}
