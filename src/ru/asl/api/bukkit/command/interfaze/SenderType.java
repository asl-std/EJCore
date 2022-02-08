package ru.asl.api.bukkit.command.interfaze;

public enum SenderType {
	ALL,
	CONSOLE_ONLY,
	PLAYER_ONLY,
	;

	public static SenderType fromString(String type) {
		switch(type.toUpperCase()) {
		case "CONSOLE_ONLY": return CONSOLE_ONLY;
		case "PLAYER_ONLY": return PLAYER_ONLY;
		default: return ALL;
		}
	}

}
