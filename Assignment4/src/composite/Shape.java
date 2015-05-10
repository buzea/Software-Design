/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import java.awt.Graphics;



public abstract class Shape {

	
	public abstract void drawShape(Graphics dragGraphics);
	
	public void add(Shape shape){
		//do nothing by default
	}
	
	public void remove(Shape shape){
		//do nothing by default
	}
	
	public Shape getChild(int number){
		return null;
	}

}