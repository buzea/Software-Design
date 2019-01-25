/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import iterators.CompositeIterator;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComplexShape extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 43331098905157714L;
	private List<Shape> list;

	public ComplexShape() {
		list = new ArrayList<Shape>();
	}

	@Override
	public void drawShape(Graphics dragGraphics) {
		for (Shape s : list) {
			s.drawShape(dragGraphics);
		}
	}

	@Override
	public void add(Shape shape) {
		list.add(shape);
	}

	@Override
	public void remove(Shape shape) {
		list.remove(shape);

	}

	@Override
	public Shape getChild(int number) {
		return list.get(number);

	}

	@Override
	public Iterator<Shape> createIterator() {
		return new CompositeIterator<Shape>(list.iterator());
	}

	public void clear() {
		list.clear();
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

}
