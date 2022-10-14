package ru.aslcraft.api.bukkit.value.random;

import lombok.Getter;
import lombok.Setter;

public class BoxedNumber<T extends Number> {

	@Getter @Setter private T value;

	public BoxedNumber() {}

	public BoxedNumber(T value) {
		this.value = value;
	}

}
