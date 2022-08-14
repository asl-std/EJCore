package ru.aslcraft.api.bukkit.command.interfaze;

import org.bukkit.command.CommandExecutor;

/**
 * <p>CommandHandler interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface CommandHandler extends CommandExecutor {

	/**
	 * <p>getDefaultCommand.</p>
	 *
	 * @return a {@link ru.aslcraft.api.bukkit.command.interfaze.ECommand} object
	 */
	ECommand getDefaultCommand();

	/**
	 * <p>registerCommand.</p>
	 *
	 * @param command a {@link ru.aslcraft.api.bukkit.command.interfaze.ECommand} object
	 */
	CommandHandler registerCommand(ECommand command);
}
