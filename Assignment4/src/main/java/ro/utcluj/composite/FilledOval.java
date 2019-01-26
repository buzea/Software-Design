/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.utcluj.composite;

import ro.utcluj.iterators.NullIterator;

import java.awt.*;
import java.util.Iterator;

import static ro.utcluj.composite.Boundary.createBoundary;

public class FilledOval implements Shape {

	private static final long  serialVersionUID = 4745012335321275587L;
	private final        int   x1;
	private final        int   x2;
	private final        int   y1;
	private final        int   y2;
	private final        Color color;

	public FilledOval(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
	}

	@Override
	public void drawShape(Graphics dragGraphics) {
		int x, y;  // Top left corner of rectangle that contains the figure.
		int w, h;  // Width and height of rectangle that contains the figure.
		if (x1 >= x2) {  // x2 is left edge
			x = x2;
			w = x1 - x2;
		} else {          // x1 is left edge
			x = x1;
			w = x2 - x1;
		}
		if (y1 >= y2) {  // y2 is top edge
			y = y2;
			h = y1 - y2;
		} else {          // y1 is top edge.
			y = y1;
			h = y2 - y1;
		}
		dragGraphics.setColor(color);
		dragGraphics.fillOval(x, y, w, h);
	}

	@Override
	public Iterator<Shape> createIterator() {
		return new NullIterator<>();
	}

	@Override
	public Boundary getBoundaries() {
		return createBoundary(x1, x2, y1, y2);
	}
}
