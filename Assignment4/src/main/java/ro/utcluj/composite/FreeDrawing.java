package ro.utcluj.composite;

import ro.utcluj.iterators.NullIterator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FreeDrawing implements Shape {

	private static final long        serialVersionUID = 3798188722466753701L;
	private final        List<Point> points;
	private final        Color       color;

	public FreeDrawing(Color color) {
		this.color = color;
		points = new ArrayList<>();
	}

	public void add(Point e) {
		points.add(e);
	}

	@Override
	public void drawShape(Graphics dragGraphics) {
		if (points.size() > 0) {
			Point prev = points.get(0);
			for (Point p : points) {
				dragGraphics.setColor(color);
				dragGraphics.drawLine(prev.x, prev.y, p.x, p.y);
				prev = p;
			}
		}
	}

	@Override
	public Iterator<Shape> createIterator() {
		return new NullIterator<>();
	}

	@Override
	public Boundary getBoundaries() {
		Boundary boundary = null;
		Iterator<Point> i = points.iterator();
		if (i.hasNext()) {
			Point p = i.next();
			boundary = new Boundary(p.x, p.x, p.y, p.y);
		}
		while (i.hasNext()) {
			Point p = i.next();
			if (boundary.getX1() > p.x) {
				boundary.setX1(p.x);
			}
			if (boundary.getY1() > p.y) {
				boundary.setY1(p.y);
			}
			if (boundary.getX2() < p.x) {
				boundary.setX2(p.x);
			}
			if (boundary.getY2() < p.y) {
				boundary.setY2(p.y);
			}
		}

		return boundary;
	}
}
