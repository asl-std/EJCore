package org.aslstd.api.bukkit.command.interfaze;

import org.bukkit.command.CommandSender;

/**
 * <p>ECommand interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface ECommand extends Perms {

	/**
	 * <p>getSenderType.</p>
	 *
	 * @return a {@link org.aslstd.api.bukkit.command.interfaze.SenderType} object
	 */
	SenderType getSenderType();

	/**
	 * <p>getDescription.</p>
	 *
	 * @return a {@link String} object
	 */
	String getDescription();

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link String} object
	 */
	String getName();

	/**
	 * <p>getUsage.</p>
	 *
	 * @return a {@link String} object
	 */
	String getUsage();

	boolean testConditions(CommandSender player);

	/**
	 * <p>use.</p>
	 *
	 * @param sender a {@link org.bukkit.command.CommandSender} object
	 * @param args an array of {@link String} objects
	 * @return
	 */
	String use(CommandSender sender, String[] args);

}
