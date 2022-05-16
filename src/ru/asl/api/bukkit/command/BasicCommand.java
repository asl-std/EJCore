package ru.asl.api.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.command.interfaze.SenderType;
import ru.asl.api.bukkit.command.interfaze.Usable;

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

	/**
	 * <p>Constructor for BasicCommand.</p>
	 *
	 * @param handler a {@link ru.asl.api.bukkit.command.BasicCommandHandler} object
	 * @param label a {@link java.lang.String} object
	 * @param func a {@link ru.asl.api.bukkit.command.interfaze.Usable} object
	 */
	public BasicCommand(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func) {
		commandLabel = handler.cmdFile.getString(label + ".command-name", label, true);
		description = handler.cmdFile.getString(label + ".description", getDescription() == null ? label + " command description" : getDescription(), true);
		permission = handler.cmdFile.getString(label + ".permission", getPermission() == null ? handler.plugin.getName().toLowerCase() + ".command." + label : getPermission(), true);
		arguments = handler.cmdFile.getString(label + ".arguments", "<>", true);
		senderType = SenderType.fromString(handler.cmdFile.getString(label + ".sender-type", "ALL", true));
		this.func = func;
		this.handler = handler;
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

	/**
	 * <p>isValid.</p>
	 *
	 * @param obj a {@link java.lang.Object} object
	 * @param senderType a {@link ru.asl.api.bukkit.command.interfaze.SenderType} object
	 * @return a boolean
	 */
	public static boolean isValid(Object obj, SenderType senderType) {
		final boolean isPlayer = obj instanceof Player;
		switch (senderType) {
		case ALL:			return true;
		case CONSOLE_ONLY:	if (isPlayer) return false;
		case PLAYER_ONLY:	if (isPlayer) return true;
		default:			return false;
		}
	}

}
