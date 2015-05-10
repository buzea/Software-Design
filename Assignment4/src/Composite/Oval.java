/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Composite;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Mihai
 */
public class Oval implements Shape{

    private Graphics g;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Color color;

    public Oval(Graphics g,int x1, int y1, int x2, int y2, Color color)
    {
        this.g = g;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

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
        dragGraphics.drawOval(x, y, w, h);
    }

    public Shape[] explodeShape() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}