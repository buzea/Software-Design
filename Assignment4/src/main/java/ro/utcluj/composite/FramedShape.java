package ro.utcluj.composite;

import ro.utcluj.iterators.NullIterator;

import java.awt.*;
import java.util.Iterator;

public class FramedShape implements Shape {

	private static final long  serialVersionUID = 8215392679149851281L;
	private final        Shape content;

	public FramedShape(Shape content) {
		this.content = content;
	}

	@Override
	public void drawShape(Graphics dragGraphics) {
		Boundary b = getBoundaries();
		if (b != null) {
			dragGraphics.setColor(Color.BLACK);
			dragGraphics.fillRect(b.getX1(), b.getY1(), b.getX2() - b.getX1(), b.getY2() - b.getY1());
			dragGraphics.setColor(Color.WHITE);
			dragGraphics.fillRect(b.getX1() + 5, b.getY1() + 5, (b.getX2() - b.getX1()) - 10, (b.getY2() - b.getY1()) - 10);
		}
		content.drawShape(dragGraphics);
	}

	@Override
	public Iterator<Shape> createIterator() {
		return new NullIterator<>();
	}

	@Override
	public Boundary getBoundaries() {
		Boundary boundary = content.getBoundaries();
		if (boundary != null) {
			boundary.setX1(boundary.getX1() - 10);
			boundary.setY1(boundary.getY1() - 10);
			boundary.setX2(boundary.getX2() + 10);
			boundary.setY2(boundary.getY2() + 10);
		}
		return boundary;
	}
}
