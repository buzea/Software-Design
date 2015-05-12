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
	
	

}
