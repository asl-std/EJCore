package ru.asl.api.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.command.interfaze.SenderType;
import ru.asl.api.bukkit.command.interfaze.Usable;

public abstract class BasicCommand implements ECommand {

	public static boolean isValid(Object obj, SenderType senderType) {
		boolean isPlayer = obj instanceof Player;
		switch (senderType) {
			case ALL:			return true;
			case CONSOLE_ONLY:	if (isPlayer) return false;
			case PLAYER_ONLY:	if (isPlayer) return true;
			default:			return false;
		}
	}

	protected SenderType							senderType	= SenderType.ALL;
	private   String								commandLabel;
	private final Usable<CommandSender, String[]>	func;


	public String getHelp() {return ChatColor.GOLD + this.getUsage() + " - " + ChatColor.GREEN + this.getDescription();}
	public BasicCommand(String command, Usable<CommandSender, String[]> func) {
		this.commandLabel = command;
		this.func = func;
	}


	@Override
	public String getName() {return this.commandLabel;}
	@Override
	public SenderType getSenderType() {return this.senderType;}
	@Override
	public void use(CommandSender sender, String[] args) {
		if (BasicCommand.isValid(sender, this.senderType)) this.func.execute(sender, args);
	}

}
