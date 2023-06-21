package org.aslstd.core.command;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.bukkit.command.BasicCommand;
import org.aslstd.api.bukkit.command.BasicCommandHandler;
import org.aslstd.api.bukkit.command.interfaze.ECommand;
import org.aslstd.api.bukkit.entity.pick.Pick;
import org.aslstd.api.bukkit.entity.util.EntityUtil;
import org.aslstd.api.bukkit.message.Text;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.plugin.hook.Placeholders;
import org.aslstd.core.OpenLib;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

/**
 * <p>
 * CoreCommandHandler class.
 * </p>
 *
 * @author Snoop1CattZ69
 */
public class CoreCommandHandler extends BasicCommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> list = new ArrayList<>();
		if (command.getName().equalsIgnoreCase("mol")) {
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
		super(OpenLib.instance(), "mol");
		registerCommand(new BasicCommand(this, "help", (s, args) -> {
			Text.send(s, "&c»------>&5[&6Minecraft Open Plugin Library&5&l]");
			final List<ECommand> commands = new ArrayList<>(getRegisteredCommands());
			commands.add(getDefaultCommand());
			for (final ECommand command : commands)
				if (command.testConditions(s))
					Text.send(s, "&6" + command.getUsage() + " - &2" + command.getDescription()
					+ (s.isOp() || s.hasPermission("*") ? " &f- &5" + command.getPermission() : ""));
			Text.send(s, "&c»------>&5[&6Minecraft Open Plugin Library&5&l]");
			return null;
		}))

		.registerCommand(new BasicCommand(this, "dump", (s, args) -> {
			Text.dump((Player)s);
			return null;
		}))

		.registerCommand(new BasicCommand(this, "reload", (s, args) -> {
			OpenLib.instance().reloadPlugin();
			OpenLib.instance().reloadPlugins();
			return null;
		}));

		if (Placeholders.enabled())
			registerCommand(new BasicCommand(this, "data", (s, args) -> {
				if (args.length < 4)
					return "&c[moLib] Not enough arguments: /mol data <player/custom> <name/uid/file> <key> <value>";

				switch (args[0].toLowerCase()) {
					case "player":
						final OfflinePlayer player = EntityUtil.getPlayer(args[1]);

						if (player == null)
							return "&c[moLib] Player with name/uuid " + args[1] + " was not found";

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
							Pick.of(player.getPlayer()).options().writeData(args[2], value);
						else {
							final Yaml pfile = OpenLib.playerDatabase().getPlayerFile(player);
							pfile.set(args[2], value);
						}

						return "&a[moLib] Successfully added data " + args[2] + ": " + value + " to a player " + args[1];
					case "custom":
						final Yaml data = Yaml.getCustomStorage(args[1]);

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
						return "&a[moLib] Successfully added data " + args[2] + ": " + val + " to file " + data.getFile().getName();
					default:
						return "&a[moLib] Incorrect usage: /mol data &c<player/custom>&a <name/uid/file> <key> <value>";
				}

			}));
		else
			registerCommand(new BasicCommand(this, "data", (s, args) -> "&c[moLib] PlaceholderAPI not installed, this command was disabled"));
	}

}
