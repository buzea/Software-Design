/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.utcluj.composite;

import ro.utcluj.iterators.CompositeIterator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComplexShape implements Shape {

	private static final long        serialVersionUID = 43331098905157714L;
	private final        List<Shape> list;

	public ComplexShape() {
		list = new ArrayList<>();
	}

	@Override
	public void drawShape(Graphics dragGraphics) {
		for (Shape s : list) {
			s.drawShape(dragGraphics);
		}
	}

	@Override
	public Iterator<Shape> createIterator() {
		return new CompositeIterator<>(list.iterator());
	}

	@Override
	public void add(Shape shape) {
		list.add(shape);
	}

	@Override
	public Boundary getBoundaries() {
		Iterator<Shape> i = list.iterator();
		Boundary boundary = null;
		if (i.hasNext()) {
			Shape temp = i.next();
			boundary = temp.getBoundaries();
		}
		while (i.hasNext()) {
			Shape temp = i.next();
			Boundary currentBoundary = temp.getBoundaries();

			if (boundary.getX1() > currentBoundary.getX1()) {
				boundary.setX1(currentBoundary.getX1());
			}
			if (boundary.getY1() > currentBoundary.getY1()) {
				boundary.setY1(currentBoundary.getY1());
			}
			if (boundary.getX2() < currentBoundary.getX2()) {
				boundary.setX2(currentBoundary.getX2());
			}
			if (boundary.getY2() < currentBoundary.getY2()) {
				boundary.setY2(currentBoundary.getY2());
			}
		}
		return boundary;
	}

	public void clear() {
		list.clear();
	}
}
