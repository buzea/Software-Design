package iterators;

import java.util.Iterator;
import java.util.Stack;

import composite.ComplexShape;
import composite.Shape;

public class CompositeIterator<Shape> implements Iterator<Shape> {
	private Stack<Iterator<Shape>> stack;
	
	public CompositeIterator() {
		stack = new Stack<Iterator<Shape>>();
	}
	public CompositeIterator(Iterator<Shape> iterator) {
		stack = new Stack<Iterator<Shape>>();
		stack.push(iterator);
	}
	
	
	

	@Override
	public boolean hasNext() {
		if(stack.empty())
			return false;
		else{
			Iterator<Shape> iterator = stack.peek();
			if(!iterator.hasNext()){
				stack.pop();
				return hasNext();
			}else{
				return true;
			}
		}
	}

	@Override
	public Shape next() {
		if(hasNext()){
			Iterator<Shape> iterator = stack.peek();
			Shape shape = iterator.next();
			if(shape instanceof ComplexShape){
				ComplexShape complexShape =(ComplexShape) shape;
				stack.push((Iterator<Shape>) complexShape.createIterator());
			}
			return shape;
			
			
		}
		return null;
	}

}
