/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import java.awt.Graphics;



public interface Shape {

	
	public void drawShape(Graphics dragGraphics);
	
	public void add(Shape shape);
	
	public void remove(Shape shape);
	
	public Shape getChild(int number);

}