package ru.asl.api.ejcore.value.random;

import lombok.Getter;
import lombok.Setter;
import ru.asl.api.ejcore.value.abstrakt.ModifierType;
import ru.asl.api.ejcore.value.util.ValueUtil;

public class Value {

	@Getter private String value;

	@Getter @Setter private ModifierType type = ModifierType.POSITIVE;

	@Getter @Setter private String keyName;

	public void setValue(String value) {
		this.value = value.replaceAll("%", "");
	}

	public void setValue(Number value) {
		this.value = String.valueOf(value);
	}

	public String getAndScale(double scale, double lvl) {
		if (value == null) return null;

		return String.valueOf((ValueUtil.parseDouble(value) + (scale * lvl)));
	}

	public String getAndScale(String scale, double lvl) {
		if (value == null) return null;

		final String[] sc = scale.split("-");
		final String[] val = value.split("-");

		if (sc.length < 2)
			return (ValueUtil.parseDouble(val[0]) + ValueUtil.parseDouble(sc[0]) * lvl) + "-" + (ValueUtil.parseDouble(val[1]) + ValueUtil.parseDouble(sc[0]) * lvl);
		else
			return (ValueUtil.parseDouble(val[0]) + ValueUtil.parseDouble(sc[0]) * lvl) + "-" + (ValueUtil.parseDouble(val[1]) + ValueUtil.parseDouble(sc[1]) * lvl);
	}

	public Value() {}

	public Value(String value) {
		setValue(value);
	}

	public Value(Number value) {
		setValue(value);
	}

	public Value(String value, ModifierType type) {
		this(value);
		setType(type);
	}

	public Value(Number value, ModifierType type) {
		this(value);
		setType(type);
	}

}
