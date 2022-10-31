package org.aslstd.api.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.aslstd.api.bukkit.command.interfaze.ECommand;
import org.aslstd.api.bukkit.command.interfaze.SenderType;
import org.aslstd.api.bukkit.command.interfaze.Usable;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * <p>BasicCommand class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BasicCommand implements ECommand {

	protected SenderType	senderType	= SenderType.ALL;
	protected String		commandLabel;

	protected Usable<CommandSender, String[]>	func;

	private String description, permission, arguments;
	private BasicCommandHandler handler;

	protected List<Predicate<CommandSender>> conditions = new ArrayList<>();

	/**
	 * <p>Constructor for BasicCommand.</p>
	 *
	 * @param handler a {@link org.aslstd.api.bukkit.command.BasicCommandHandler} object
	 * @param label a {@link java.lang.String} object
	 * @param func a {@link org.aslstd.api.bukkit.command.interfaze.Usable} object
	 */
	public BasicCommand(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func) {
		commandLabel = handler.cmdFile.getString(label + ".command-name", label, true);
		description = handler.cmdFile.getString(label + ".description", getDescription() == null ? label + " command description" : getDescription(), true);
		permission = handler.cmdFile.getString(label + ".permission", getPermission() == null ? handler.plugin.getName().toLowerCase() + ".command." + label : getPermission(), true);
		arguments = handler.cmdFile.getString(label + ".arguments", "<>", true);
		senderType = SenderType.fromString(handler.cmdFile.getString(label + ".sender-type", "ALL", true));
		this.func = func;
		this.handler = handler;
		conditions.add(p -> p.hasPermission(permission) );
		conditions.add(p -> isValid(p, senderType));
	}

	public BasicCommand(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func, List<Predicate<CommandSender>> filters) {
		this(handler, label, func);
		conditions.addAll(filters);
	}

	/**
	 * <p>getHelp.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getHelp() { return ChatColor.GOLD + getUsage() + " - " + ChatColor.GREEN + getDescription(); }

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public String getPermission() {
		return permission;
	}

	/** {@inheritDoc} */
	@Override
	public String getUsage() {
		return "/" + handler.getLabel() + " " + getName() + " " + arguments;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() { return commandLabel; }

	/** {@inheritDoc} */
	@Override
	public SenderType getSenderType() { return senderType; }

	/** {@inheritDoc} */
	@Override
	public void use(CommandSender sender, String[] args) {
		if (func == null) return; if (isValid(sender, senderType)) func.execute(sender, args);
	}

	@Override
	public boolean testConditions(CommandSender sender) {
		for (final Predicate<CommandSender> filters : conditions)
			if (!filters.test(sender))
				return false;

		return true;
	}

	/**
	 * <p>isValid.</p>
	 *
	 * @param obj a {@link java.lang.Object} object
	 * @param senderType a {@link org.aslstd.api.bukkit.command.interfaze.SenderType} object
	 * @return a boolean
	 */
	public static boolean isValid(Object obj, SenderType senderType) {
		final boolean isPlayer = obj instanceof Player;
		switch (senderType) {
		case ALL:			return true;
		case CONSOLE_ONLY:	return !isPlayer;
		case PLAYER_ONLY:	return isPlayer;
		default:			return false;
		}
	}

}
