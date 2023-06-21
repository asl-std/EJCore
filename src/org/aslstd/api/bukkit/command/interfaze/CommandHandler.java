package org.aslstd.api.bukkit.command.interfaze;

import org.bukkit.command.CommandExecutor;

/**
 * <p>CommandHandler interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface CommandHandler extends CommandExecutor {

	/**
	 * <p>getDefaultCommand.</p>
	 *
	 * @return a {@link org.aslstd.api.bukkit.command.interfaze.ECommand} object
	 */
	ECommand getDefaultCommand();

	/**
	 * <p>registerCommand.</p>
	 *
	 * @param command a {@link org.aslstd.api.bukkit.command.interfaze.ECommand} object
	 */
	CommandHandler registerCommand(ECommand command);
}
