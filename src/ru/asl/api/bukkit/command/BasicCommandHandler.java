package ru.asl.api.bukkit.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import ru.asl.api.bukkit.command.interfaze.CommandHandler;
import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.message.EText;

public class BasicCommandHandler implements CommandHandler {

	protected Map<String, ECommand> commands = new HashMap<>();

	public Collection<ECommand> getRegisteredCommands() { return commands.values(); }

	private ECommand defCommand;

	public BasicCommandHandler(ECommand def) { defCommand = def; }

	@Override
	public ECommand getDefaultCommand() { return defCommand; }

	@Override
	public void registerCommand(ECommand command) { commands.put(command.getName(), command); }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ECommand cmd = null;

		if (args.length == 0 || commands.get(args[0]) == null) cmd = defCommand;
		else {
			cmd = commands.get(args[0]);
			args = EText.trimArgs(args);
		}

		if (cmd.getPermission() == null || sender.hasPermission(cmd.getPermission()))
			cmd.use(sender, args);
		else
			sender.sendMessage("Unknown command!");

		return true;
	}

}
