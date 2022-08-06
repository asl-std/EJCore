package ru.aslcraft.api.bukkit.command.interfaze;

import org.bukkit.command.CommandSender;

/**
 * <p>ECommand interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface ECommand extends Perms {

	/**
	 * <p>getSenderType.</p>
	 *
	 * @return a {@link ru.aslcraft.api.bukkit.command.interfaze.SenderType} object
	 */
	SenderType getSenderType();

	/**
	 * <p>getDescription.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getDescription();

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getName();

	/**
	 * <p>getUsage.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getUsage();

	boolean testConditions(CommandSender player);

	/**
	 * <p>use.</p>
	 *
	 * @param sender a {@link org.bukkit.command.CommandSender} object
	 * @param args an array of {@link java.lang.String} objects
	 */
	void use(CommandSender sender, String[] args);

}
