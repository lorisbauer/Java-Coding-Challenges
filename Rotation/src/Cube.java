
public class Cube {
	int size;
	int oDist;
	int displaySize;
	double[][] projPlane;
	
	int n = 0;
	double xAngle = 0;
	double yAngle = 0;
	double zAngle = 1;
	
	boolean rotatePlane = true;
	int anglePlane = 10;
	
	Node[] nodes;
	
	double[] center;
	double[][] XRotMatrix = XRotationMatrix(xAngle);
	double[][] YRotMatrix = YRotationMatrix(yAngle);
	double[][] ZRotMatrix = ZRotationMatrix(zAngle);
	
	double[][] RotMatrix = multiplyMatrices(XRotMatrix, multiplyMatrices(ZRotMatrix, YRotMatrix));
			
	Cube(int size, int oDist, int displaySize, double[][] projPlane) {
		this.size = size;
		this.oDist = oDist;
		this.displaySize = displaySize;
		this.projPlane = projPlane;
		this.center = new double[] {0, this.size + this.oDist, 0};
		nodes = initNodes();
	}
	
	Node[] initNodes() {
		Node[] nodes = {
			new Node(new double[] {size, oDist, -size}, displaySize),
			new Node(new double[] {-size, oDist, -size}, displaySize),
			new Node(new double[] {size, oDist + 2 * size, -size}, displaySize),
			new Node(new double[] {-size, oDist + 2 * size, -size}, displaySize),
			new Node(new double[] {size, oDist, size}, displaySize),
			new Node(new double[] {-size, oDist, size}, displaySize),
			new Node(new double[] {size, oDist + 2 * size, size}, displaySize),
			new Node(new double[] {-size, oDist + 2 * size, size}, displaySize)
		};
		
		for (Node node: nodes) {
			for (Node other: nodes) {
				if (node.pos != other.pos) {
					if (lengthOf3DVector(subtract3DVectors(node.pos, other.pos)) == 2 * size && !other.contains(node)) {
						node.addNeighbor(other);
					}
				}
			}
		}
		nodes = projPositions(nodes);
		return nodes;
	}
	
	int transform(int i, int k) {
        return (i / k % 2 == 0) ? i % k + 1 : k - i % k + 1;
    }
	
	public static double lerp(double x, double s1, double e1, double s2, double e2) {
		if (x > e1) return e2;
		if (x < s1) return s2;
		return s2 + (x - s1) * (e2 - s2) / (e1 - s1);
	}
	
	double[][] rotateProjPlane() {
//		int max = 180;
//		double angle = lerp((double) transform(n, max - 1), 0, max, 0, max);
//		n += 1;
		return XRotationMatrix(anglePlane);
	}
	
	void updateNodes() {
		for (Node node: nodes) {
			node.pos = rotatePoint(node, RotMatrix);
		}		
		nodes = projPositions(nodes);
	}
	
	double[] rotatePoint(Node node, double[][] rotMatrix) {		
		double[][] vec = new double[][] {
			{node.pos[0] - center[0]}, {node.pos[1] - center[1]}, {node.pos[2] - center[2]}
		};		
		double[][] result = multiplyMatrices(rotMatrix, vec);
		
		double x = result[0][0];
		double y = result[1][0];
		double z = result[2][0];
		return new double[] {x + center[0], y + center[1], z + center[2]};	
	}
	
	Node[] projPositions(Node[] nodes) {
		double[][] projMatrix = multiplyMatrices(projPlane, transpose(projPlane));
		if (rotatePlane) {
			projMatrix = multiplyMatrices(projMatrix, rotateProjPlane());
		}
		for (Node node: nodes) {
			double[][] projPos = multiplyMatrices(projMatrix, node.convertPos(node.pos));
			node.setProjPos(projPos);
		}
		return nodes;
	}
	
	double[] subtract3DVectors(double[] v1, double[] v2) {
		return new double[] {v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2]};
	}

	double lengthOf3DVector(double[] vector) {
		double x = (double) vector[0];
		double y = (double) vector[1];
		double z = (double) vector[2];
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	double[][] XRotationMatrix(double angle) {
		double radAngle = Math.toRadians(angle);
		double[][] rotMatrix = {
			{1, 0, 0},
			{0, Math.cos(radAngle), -Math.sin(radAngle)}, 
			{0, Math.sin(radAngle), Math.cos(radAngle)}
		};
		return rotMatrix;
	}
	
	double[][] YRotationMatrix(double angle) {
		double radAngle = Math.toRadians(angle);
		double[][] rotMatrix = {
			{Math.cos(radAngle), 0, Math.sin(radAngle)},
			{0, 1, 0}, 
			{-Math.sin(radAngle), 0, Math.cos(radAngle)}
		};
		return rotMatrix;
	}
	
	double[][] ZRotationMatrix(double angle) {
		double radAngle = Math.toRadians(angle);
		double[][] rotMatrix = {
			{Math.cos(radAngle), -Math.sin(radAngle), 0},
			{Math.sin(radAngle), Math.cos(radAngle), 0}, 
			{0, 0, 1}
		};
		return rotMatrix;
	}
	
	public double[][] transpose(double[][] matrix) {
	    int width = matrix.length;
	    int height = matrix[0].length;

	    double[][] result = new double[height][width];

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            result[y][x] = matrix[x][y];
	        }
	    }
	    return result;
	}
	
	double[][] multiplyMatrices(double[][] A, double[][] B) {
	    double[][] result = new double[A.length][B[0].length];

	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = multiplyMatricesCell(A, B, row, col);
	        }
	    }
	    return result;
	}
	
	double multiplyMatricesCell(double[][] A, double[][] B, int row, int col) {
	    double cell = 0;
	    for (int i = 0; i < B.length; i++) {
	        cell += A[row][i] * B[i][col];
	    }
	    return cell;
	}
	
	public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}