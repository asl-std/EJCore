package ru.asl.core.commands;

import org.bukkit.command.CommandSender;

import ru.asl.api.bukkit.command.BasicCommand;
import ru.asl.api.bukkit.command.BasicCommandHandler;
import ru.asl.api.bukkit.command.interfaze.SenderType;
import ru.asl.api.bukkit.command.interfaze.Usable;

public class CoreDump extends BasicCommand {

	public CoreDump(BasicCommandHandler handler, String label, Usable<CommandSender, String[]> func) {
		super(handler, label, func);
		senderType = SenderType.PLAYER_ONLY;
	}

	@Override
	public String getDescription() {
		return "Dumps all stored player settings into 'yml' file which is located in the plugin root folder";
	}

	@Override
	public String getUsage() {
		return "/ejc dump";
	}

	@Override
	public String getPermission() {
		return "ejc.command.dump.self";
	}

}
