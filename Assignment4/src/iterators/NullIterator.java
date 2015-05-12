package iterators;

import java.util.Iterator;

public class NullIterator<Shape> implements Iterator<Shape> {

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Shape next() {
		return null;
	}
	
}
