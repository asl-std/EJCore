package org.aslstd.api.attributes.storage;

import javax.annotation.Nonnull;

import org.aslstd.api.attributes.AttrType;
import org.aslstd.api.bukkit.equip.EquipSlot;
import org.aslstd.api.bukkit.value.ValuePair;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class AttrStorage {

	@Nonnull private AttrType type;
	@Nonnull private ValuePair<Double> base;
	@Getter private ValuePair<Double> calculated;

	private AttrModifiable add = new AttrModifiable();
	private AttrModifiable mod = new AttrModifiable();

	public AttrStorage addModifier(ValuePair<Double> value, boolean modifier) {
		if (modifier)
			mod.add(value);
		else
			add.add(value);
		return this;
	}

	public AttrStorage removeModifier(EquipSlot slot) {
		mod.removeIf(v -> v.getKey().equalsIgnoreCase(slot.name()));
		add.removeIf(v -> v.getKey().equalsIgnoreCase(slot.name()));
		return this;
	}

	public void calculate() {
		final ValuePair<Double> cAdd = add.calculate(type);
		final ValuePair<Double> cMod = mod.calculate(type);

		switch(type) {
			case RANGE -> {
				calculated.setFirst((base.getFirst() + cAdd.getFirst()) * cMod.getFirst());
				calculated.setSecond((base.getSecond() + cAdd.getSecond()) * cMod.getSecond());
			}
			default -> {
				calculated.setFirst((base.getFirst() + cAdd.getFirst()) * cMod.getFirst());
			}
		}
	}


}
