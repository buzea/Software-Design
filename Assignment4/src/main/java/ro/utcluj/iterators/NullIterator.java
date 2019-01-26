package ro.utcluj.iterators;

import java.util.Iterator;

public class NullIterator<E> implements Iterator<E> {

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public E next() {
		return null;
	}
}
