/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ComplexShape extends Shape {

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

}
