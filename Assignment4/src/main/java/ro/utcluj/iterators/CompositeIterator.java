package ro.utcluj.iterators;

import ro.utcluj.composite.ComplexShape;
import ro.utcluj.composite.Shape;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator<E> implements Iterator<E> {
	private final Stack<Iterator<Shape>> stack;

	public CompositeIterator() {
		stack = new Stack<>();
	}

	public CompositeIterator(Iterator<Shape> iterator) {
		this();
		stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		if (stack.empty()) {
			return false;
		}

		Iterator<Shape> iterator = stack.peek();
		if (iterator.hasNext()) {
			return true;
		} else {
			stack.pop();
			return hasNext();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E next() {
		if (hasNext()) {
			Iterator<Shape> iterator = stack.peek();
			Shape shape = iterator.next();
			if (shape instanceof ComplexShape) {
				ComplexShape complexShape = (ComplexShape) shape;
				stack.push(complexShape.createIterator());
			}
			return (E) shape;
		}
		return null;
	}
}
