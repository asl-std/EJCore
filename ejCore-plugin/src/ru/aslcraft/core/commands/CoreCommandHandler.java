package ru.aslcraft.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.aslcraft.api.bukkit.command.BasicCommand;
import ru.aslcraft.api.bukkit.command.BasicCommandHandler;
import ru.aslcraft.api.bukkit.command.interfaze.ECommand;
import ru.aslcraft.api.bukkit.entity.EPlayer;
import ru.aslcraft.api.bukkit.entity.util.EntityUtil;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.bukkit.yaml.YAML;
import ru.aslcraft.api.ejcore.plugin.hook.HookManager;
import ru.aslcraft.core.Core;

/**
 * <p>
 * CoreCommandHandler class.
 * </p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
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
		registerCommand(new BasicCommand(this, "help", (s, args) -> {
			EText.send(s, "&c»------>&5[&6Elephant&Jaguar Plugin Core&5&l]");
			final List<ECommand> commands = new ArrayList<>(getRegisteredCommands());
			commands.add(getDefaultCommand());
			for (final ECommand command : commands)
				if (command.testConditions(s))
					EText.send(s, "&6" + command.getUsage() + " - &2" + command.getDescription()
					+ (s.isOp() || s.hasPermission("*") ? " &f- &5" + command.getPermission() : ""));
			EText.send(s, "&c»------>&5[&6Elephant&Jaguar Plugin Core&5&l]");
		}))

		.registerCommand(new BasicCommand(this, "dump", (s, args) -> {
			final EPlayer p = EPlayer.getEPlayer((Player) s);
			p.getTempSettings().dumpToFile(Core.instance());
			p.getSettings().dumpToFile(Core.instance());
		}))

		.registerCommand(new BasicCommand(this, "reload", (s, args) -> {
			Core.instance().reloadPlugin();
			Core.instance().reloadPlugins();
		}));

		if (HookManager.isPapiEnabled())
			registerCommand(new BasicCommand(this, "data", (s, args) -> {
				if (args.length < 4) {
					EText.send(s, "&c[ejCore] Not enough arguments: /ejc data <player/custom> <name/uid/file> <key> <value>");
					return;
				}

				switch (args[0].toLowerCase()) {
				case "player":
					final OfflinePlayer player = EntityUtil.getPlayer(args[1]);

					if (player == null) {
						EText.send(s, "&c[ejCore] Player with name/uuid " + args[1] + " was not found");
						return;
					}


					String value = args[3];
					final StringBuilder b = new StringBuilder(value);

					if (args.length > 4) {
						for (int i = 4 ; i < args.length ; i++) {
							b.append(" ");
							b.append(args[i]);
						}
					}

					value = PlaceholderAPI.setPlaceholders(player, b.toString());

					if (player.isOnline())
						EPlayer.getEPlayer(player.getPlayer()).getSettings().setValue(args[2], value);
					else {
						final YAML pfile = Core.getPlayerDatabase().getPlayerFile(player);
						pfile.set(args[2], value);
					}

					EText.send(s, "&a[ejCore] Successfully added data " + args[2] + ": " + value + " to a player " + args[1]);
					break;
				case "custom":
					final YAML data = YAML.getCustomStorage(args[1]);

					String val = args[3];
					final StringBuilder bu = new StringBuilder(val);

					if (args.length > 4) {
						for (int i = 3 ; i < args.length ; i++) {
							bu.append(" ");
							bu.append(args[i]);
						}
					}

					val = PlaceholderAPI.setPlaceholders(null, bu.toString());

					data.set(args[2], val);
					EText.send(s, "&a[ejCore] Successfully added data " + args[2] + ": " + val + " to file " + data.getFile().getName());
					break;
				default:
					EText.send(s, "&a[ejCore] Incorrect usage: /ejc data &c<player/custom>&a <name/uid/file> <key> <value>");
					break;
				}

			}));
		else
			registerCommand(new BasicCommand(this, "data", (s, args) -> EText.send(s, "&c[ejCore] PlaceholderAPI not installed, this command was disabled")));
	}

}
