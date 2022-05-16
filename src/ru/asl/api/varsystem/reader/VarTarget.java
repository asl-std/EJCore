package ru.asl.api.varsystem.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>VarTarget class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public enum VarTarget {
	/** Создаёт новый объект указанного класса */
	CREATE_OBJECT("createobject"),
	/** Добавляет пользовательский триггер в список триггеров */
	INIT_TRIGGER("inittrigger"),
	/** Изменяет выбранную переменную в выбранном объекте */
	CHANGE_VAR("changevar"),
	/** Изменяет выбранную переменную на переменную указанную в файле */
	VAR_FROM_FILE("varfromfile"),
	/** Указывает на то, что в указанном классе содержаться переменные которым нужно присвоить значение.<br><br>
	 *
	 * Изменяет только статические переменные.<br><br>
	 *
	 * Для того чтобы метод инжекта был успешным, название предоставленной<br>
	 * переменной должно соответствовать названию переменной из класса.
	 */
	VAR_INJECTOR("varinjector"),
	/** Сравнивает две переменные между собой, возвращая String значение при true/false */
	VAR_IF("varif");

	@Getter private String key;
}
