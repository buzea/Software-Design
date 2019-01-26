/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.utcluj.composite;

import java.awt.*;
import java.io.Serializable;
import java.util.Iterator;

public interface Shape extends Serializable {

	void drawShape(Graphics dragGraphics);

	Iterator<Shape> createIterator();

	default void add(Shape shape) {
	}

	Boundary getBoundaries();
}