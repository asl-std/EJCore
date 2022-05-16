package ru.asl.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.command.BasicCommand;
import ru.asl.api.bukkit.command.BasicCommandHandler;
import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.core.Core;

/**
 * <p>CoreCommandHandler class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class CoreCommandHandler extends BasicCommandHandler {

	/** {@inheritDoc} */
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

	/**
	 * <p>Constructor for CoreCommandHandler.</p>
	 */
	public CoreCommandHandler() {
		super(Core.instance(), "ejc");
		registerCommand(new BasicCommand(this, "help", (s, args) -> {
			EText.send(s, "&c»------>&5[&6Elephant&Jaguar Plugin Core&5&l]");
			final List<ECommand> commands = new ArrayList<>(getRegisteredCommands());
			commands.add(getDefaultCommand());
			for (final ECommand command : commands)
				if (s.hasPermission(command.getPermission()))
					EText.send(s,
							"&6" + command.getUsage() +
							" - &2" + command.getDescription() +
							(s.isOp() || s.hasPermission("*") ? " &f- &5" + command.getPermission() : ""));
			EText.send(s, "&c»------>&5[&6Elephant&Jaguar Plugin Core&5&l]");
		}));

		registerCommand(new BasicCommand(this, "dump", (s, args) -> {
			final EPlayer p = EPlayer.getEPlayer((Player)s);
			p.getTempSettings().dumpToFile();
			p.getSettings().dumpToFile();
		}));

		registerCommand(new BasicCommand(this, "reload", (s, args) -> {
			Core.instance().reloadPlugin();
			Core.instance().reloadPlugins();
		}));
		//registerCommand(CoreCommandHandler.getReloadCommand());
	}

}
