/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import iterators.NullIterator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class StraightLine extends Shape{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5793967248688161375L;
	private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Color color;

    public StraightLine(int x1, int y1, int x2, int y2, Color color)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    public void drawShape(Graphics dragGraphics) {
        dragGraphics.setColor(color);
        dragGraphics.drawLine(x1,y1,x2,y2);
    }

    @Override
	public Iterator<Shape> createIterator() {
		return new NullIterator<Shape>();
	}

	



}
