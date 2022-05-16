package ru.asl.api.bukkit.command.interfaze;

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
	 * @return a {@link ru.asl.api.bukkit.command.interfaze.ECommand} object
	 */
	ECommand getDefaultCommand();

	/**
	 * <p>registerCommand.</p>
	 *
	 * @param command a {@link ru.asl.api.bukkit.command.interfaze.ECommand} object
	 */
	void registerCommand(ECommand command);
}
