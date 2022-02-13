package ru.asl.api.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.command.interfaze.SenderType;
import ru.asl.api.bukkit.command.interfaze.Usable;

public class BasicCommand implements ECommand {

	protected SenderType	senderType	= SenderType.ALL;
	protected String		commandLabel;

	protected Usable<CommandSender, String[]>	func;

	private String description, permission, arguments;
	private BasicCommandHandler handler;

	public BasicCommand(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func) {
		commandLabel = handler.cmdFile.getString(label + ".command-name", label, true);
		description = handler.cmdFile.getString(label + ".description", label + " command description", true);
		permission = handler.cmdFile.getString(label + ".permission", handler.plugin.getName().toLowerCase() + ".command." + label, true);
		arguments = handler.cmdFile.getString(label + ".arguments", "<>", true);
		senderType = SenderType.fromString(handler.cmdFile.getString(label + ".sender-type", "ALL", true));
		this.func = func;
		this.handler = handler;
	}

	public String getHelp() { return ChatColor.GOLD + getUsage() + " - " + ChatColor.GREEN + getDescription(); }

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getPermission() {
		return permission;
	}

	@Override
	public String getUsage() {
		return "/" + handler.getLabel() + " " + getName() + " " + arguments;
	}

	@Override
	public String getName() { return commandLabel; }

	@Override
	public SenderType getSenderType() { return senderType; }

	@Override
	public void use(CommandSender sender, String[] args) {
		if (func == null) return; if (isValid(sender, senderType)) func.execute(sender, args);
	}

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
