package ru.asl.api.ejcore.property.binding;

import ru.asl.api.ejcore.property.writeable.WriteableDouble;
import ru.asl.api.ejcore.property.writeable.WriteableString;

public class BindingHelper {

	public static void bind(WriteableDouble parent, WriteableDouble... childs) {
		if (parent == null)
			throw new IllegalArgumentException("Writeable parent object cannot be null");

		for (final WriteableDouble child : childs)
			if (child != null)
				parent.bind(child);
	}

	public static void bind(WriteableString parent, WriteableString... childs) {
		if (parent == null)
			throw new IllegalArgumentException("Writeable parent object cannot be null");

		for (final WriteableString child : childs)
			if (child != null)
				parent.bind(child);
	}

}
