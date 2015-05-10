/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import java.awt.Color;
import java.awt.Graphics;

public class StraightLine implements Shape{

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
	public void add(Shape shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Shape shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shape getChild(int number) {
		// TODO Auto-generated method stub
		return null;
	}



}
