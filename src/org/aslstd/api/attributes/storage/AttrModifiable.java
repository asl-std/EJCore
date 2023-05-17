package org.aslstd.api.attributes.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aslstd.api.attributes.AttrType;
import org.aslstd.api.bukkit.value.ValuePair;

import com.google.common.collect.ForwardingList;

public class AttrModifiable extends ForwardingList<ValuePair<Double>> {

	private static List<ValuePair<Double>> list = new ArrayList<>();

	@Override
	public boolean add(ValuePair<Double> e) {
		return list.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends ValuePair<Double>> c) {
		return list.addAll(c);
	}

	@Override
	protected List<ValuePair<Double>> delegate() {
		return list;
	}

	public ValuePair<Double> calculate(AttrType type) {
		final ValuePair<Double> f = ValuePair.of(0d, 0d);
		switch(type) {
			case RANGE -> {
				for (final ValuePair<Double> pair : list) {
					f.setFirst(f.getFirst() + pair.getFirst());
					f.setSecond(f.getSecond() + pair.getSecond());
				}
			}
			default -> {
				for (final ValuePair<Double> pair : list) {
					f.setFirst(f.getFirst() + pair.getFirst());
				}
			}
		}
		return f;
	}

}
