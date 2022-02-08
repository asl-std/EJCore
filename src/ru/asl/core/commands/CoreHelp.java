package ru.asl.core.commands;

import org.bukkit.command.CommandSender;

import ru.asl.api.bukkit.command.BasicCommand;
import ru.asl.api.bukkit.command.BasicCommandHandler;
import ru.asl.api.bukkit.command.interfaze.Usable;

public class CoreHelp extends BasicCommand {

	public CoreHelp(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func) {
		super(handler, label, func);
	}

	@Override
	public String getDescription() {
		return "Show all available commands";
	}

	@Override
	public String getUsage() {
		return "/ejc help";
	}

	@Override
	public String getPermission() {
		return "ejc.command.help";
	}

}
