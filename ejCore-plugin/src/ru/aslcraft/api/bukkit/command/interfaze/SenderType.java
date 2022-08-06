package ru.aslcraft.api.bukkit.command.interfaze;

/**
 * <p>SenderType class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public enum SenderType {
	ALL,
	CONSOLE_ONLY,
	PLAYER_ONLY,
	;

	/**
	 * <p>fromString.</p>
	 *
	 * @param type a {@link java.lang.String} object
	 * @return a {@link ru.aslcraft.api.bukkit.command.interfaze.SenderType} object
	 */
	public static SenderType fromString(String type) {
		switch(type.toUpperCase()) {
		case "CONSOLE_ONLY": return CONSOLE_ONLY;
		case "PLAYER_ONLY": return PLAYER_ONLY;
		default: return ALL;
		}
	}

}
