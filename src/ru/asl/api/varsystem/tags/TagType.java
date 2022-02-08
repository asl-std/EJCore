package ru.asl.api.varsystem.tags;

public enum TagType {
	DEFAULT, RETURN_STRING;

	public static TagType forName(String str) {
		switch(str.toUpperCase()) {
		case "RETURNSTRING":
		case "RETURN_STRING":
			return RETURN_STRING;
		default:
			return DEFAULT;
		}
	}
}
