package ro.utcluj.composite;

public class Boundary {
	private int x1, x2, y1, y2;

	public Boundary(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public static Boundary createBoundary(int x1, int x2, int y1, int y2) {
		int x, y, w, h;
		if (x1 >= x2) {  // x2 is left edge
			x = x2;
			w = x1;
		} else {          // x1 is left edge
			x = x1;
			w = x2;
		}
		if (y1 >= y2) {  // y2 is top edge
			y = y2;
			h = y1;
		} else {          // y1 is top edge.
			y = y1;
			h = y2;
		}
		return new Boundary(x, w, y, h);
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
}
