package book.oop.elegant.ch01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CachedNumber extends Number {
	private Number origin;
	private Collection<Integer> cached = new ArrayList<>(1);

	public CachedNumber(Number origin) {
		this.origin = origin;
	}
	public int intValue() {
		if (this.cached.isEmpty()) {
			this.cached.add(this.origin.intValue());
		}
		return ((List<Integer>)this.cached).get(0);
	}

	@Override
	public long longValue() {
		return origin.longValue();
	}

	@Override
	public float floatValue() {
		return origin.floatValue();
	}

	@Override
	public double doubleValue() {
		return origin.doubleValue();
	}

}
