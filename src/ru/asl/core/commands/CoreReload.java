package ru.asl.core.commands;

import org.bukkit.command.CommandSender;

import ru.asl.api.bukkit.command.BasicCommand;
import ru.asl.api.bukkit.command.BasicCommandHandler;
import ru.asl.api.bukkit.command.interfaze.Usable;

public class CoreReload extends BasicCommand {

	public CoreReload(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func) {
		super(handler, label, func);
	}

	@Override
	public String getDescription() {
		return "Reloads core and all modules (checks for new modules)";
	}

	@Override
	public String getUsage() {
		return "/ejc reload";
	}

	@Override
	public String getPermission() {
		return "ejc.admin.reload";
	}

}
