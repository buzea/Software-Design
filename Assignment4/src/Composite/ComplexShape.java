/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Composite;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class ComplexShape implements Shape{

    private List <Shape> list;

    public ComplexShape()
    {
        list = new ArrayList<Shape>();
    }

    /**
     * 
     */
    public void addToShape(Shape shape) {
        list.add(shape);
    }

    /**
     * this method is implemented directly in basic shapes
     * in complex shapes this method is handled with delegation
     */
    public void drawShape(Graphics dragGraphics) {
        for(Shape s: list){
            // use delegation to handle this method
            s.drawShape(dragGraphics);
        }
    }

    public Shape[] explodeShape() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
