package ru.asl.api.ejcore.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ValuePair<T> {

	@Getter @Setter public String key;

	@Getter @Setter public T first, second;

}
