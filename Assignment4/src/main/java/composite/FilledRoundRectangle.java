/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import iterators.NullIterator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;


public class FilledRoundRectangle extends Shape{

    /**
	 * 
	 */
	private static final long serialVersionUID = -341529597502090041L;
	private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Color color;

    public FilledRoundRectangle(int x1, int y1, int x2, int y2, Color color)
    {

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
         }
         else {          // x1 is left edge
            x = x1;
            w = x2 - x1;
         }
         if (y1 >= y2) {  // y2 is top edge
            y = y2;
            h = y1 - y2;
         }
         else {          // y1 is top edge.
            y = y1;
            h = y2 - y1;
         }
        dragGraphics.setColor(color);
        dragGraphics.fillRoundRect(x, y, w, h, 30, 30);
    }

    @Override
	public Iterator<Shape> createIterator() {
		return new NullIterator<Shape>();
	}
    
    @Override
	public Boundary getBoundaries() {
		int x,y,w,h;
		if (x1 >= x2) {  // x2 is left edge
            x = x2;
            w = x1;
         }
         else {          // x1 is left edge
            x = x1;
            w = x2;
         }
         if (y1 >= y2) {  // y2 is top edge
            y = y2;
            h = y1;
         }
         else {          // y1 is top edge.
            y = y1;
            h = y2;
         }
         
         return new Boundary(x, w, y, h);
	}

   

}
