/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package composite;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Iterator;



public abstract class Shape implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2014114493787232449L;

	public abstract void drawShape(Graphics dragGraphics);
	
	public abstract Iterator<Shape> createIterator();
	
	public void add(Shape shape){
		//do nothing by default
	}
	
	public void remove(Shape shape){
		//do nothing by default
	}
	
	public Shape getChild(int number){
		return null;
	}
	
	public abstract Boundary getBoundaries();
	

}