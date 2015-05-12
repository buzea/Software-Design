package composite;

import iterators.NullIterator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FreeDrawing extends Shape {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3798188722466753701L;
	private List<Point> points;
	private Color color;
	

	public FreeDrawing(Color color) {
		super();
		this.color=color;
		points = new ArrayList<Point>();
	}
	
	


	public boolean add(Point e) {
		return points.add(e);
	}




	public void clear() {
		points.clear();
	}




	public Point remove(int index) {
		return points.remove(index);
	}




	public boolean remove(Object o) {
		return points.remove(o);
	}

	@Override
	public Iterator<Shape> createIterator() {
		return new NullIterator<Shape>();
	}



	@Override
	public void drawShape(Graphics dragGraphics) {
		if(points.size()>0){
			Point prev = points.get(0);
			for(Point p:points){
				dragGraphics.setColor(color);
				dragGraphics.drawLine(prev.x, prev.y, p.x, p.y);
				prev=p;
			}
		}

	}

}
