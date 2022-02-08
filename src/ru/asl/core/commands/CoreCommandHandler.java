package ru.asl.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.command.BasicCommandHandler;
import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.core.Core;

public class CoreCommandHandler extends BasicCommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> list = new ArrayList<>();
		if (command.getName().equalsIgnoreCase("ejc")) {
			if (args.length == 1) {
				list.add(getDefaultCommand().getName());
				for (final ECommand cmd : getRegisteredCommands())
					list.add(cmd.getName());

				return list;
			}
		}
		return null;
	}

	public CoreCommandHandler() {
		super(Core.instance(), "ejc");
		CoreCommandHandler.handler = this;
		registerCommand(CoreCommandHandler.getHelpCommand());
		registerCommand(CoreCommandHandler.getDumpCommand());
		//registerCommand(CoreCommandHandler.getReloadCommand());
	}

	private static CoreHelp help;
	private static CoreDump dump;
	private static CoreReload reload;
	protected static CoreCommandHandler handler;

	private static ECommand getDumpCommand() {
		return CoreCommandHandler.dump == null ? CoreCommandHandler.dump = new CoreDump(handler, "dump", (s, args) -> {
			final EPlayer p = EPlayer.getEPlayer((Player)s);
			p.getTempSettings().dumpToFile();
			p.getSettings().dumpToFile();
		}) : CoreCommandHandler.dump;
	}

	private static ECommand getHelpCommand() {
		return CoreCommandHandler.help == null ? CoreCommandHandler.help = new CoreHelp(handler, "help", (s, args) -> {
			EText.send(s, "&c»------>&5[&6Elephant&Jaguar RPG Core&5&l]");
			final List<ECommand> commands = new ArrayList<>(CoreCommandHandler.handler.getRegisteredCommands());
			commands.add(CoreCommandHandler.help);
			for (final ECommand command : commands)
				if (s.hasPermission(command.getPermission()))
					EText.send(s,
							"&6" + command.getUsage() +
							" - &2" + command.getDescription() +
							(s.isOp() || s.hasPermission("*") ? " &f- &5" + command.getPermission() : ""));
			EText.send(s, "&c»------>&5[&6Elephant&Jaguar RPG Core&5&l]");
		}) : CoreCommandHandler.help;
	}

	@SuppressWarnings("unused")
	private static ECommand getReloadCommand() {
		return CoreCommandHandler.reload == null ? CoreCommandHandler.reload = new CoreReload(handler, "reload", (s, args) -> {

		}) : CoreCommandHandler.reload;
	}

}
