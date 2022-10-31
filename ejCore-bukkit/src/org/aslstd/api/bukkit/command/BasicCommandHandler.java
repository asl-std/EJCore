package org.aslstd.api.bukkit.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aslstd.api.bukkit.command.interfaze.CommandHandler;
import org.aslstd.api.bukkit.command.interfaze.ECommand;
import org.aslstd.api.bukkit.command.interfaze.SenderType;
import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.bukkit.yaml.YAML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;

/**
 * <p>Abstract BasicCommandHandler class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class BasicCommandHandler implements CommandHandler, TabCompleter {

	protected Map<String, ECommand> commands = new HashMap<>();

	/**
	 * <p>getRegisteredCommands.</p>
	 *
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<ECommand> getRegisteredCommands() { return ImmutableSet.copyOf(commands.values()); }

	@Getter private ECommand defaultCommand;

	@Getter protected YAML cmdFile;
	@Getter private String label;
	protected JavaPlugin plugin;

	/**
	 * <p>Constructor for BasicCommandHandler.</p>
	 *
	 * @param plugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 * @param label a {@link java.lang.String} object
	 */
	public BasicCommandHandler(JavaPlugin plugin, String label) {
		this.plugin = plugin;
		cmdFile = new YAML(plugin.getDataFolder() + "/commands.yml", plugin);
		this.label = label; //cmdFile.getString("main-label", label, true);
	}


	/** {@inheritDoc} */
	@Override
	public BasicCommandHandler registerCommand(ECommand command) {
		if (defaultCommand == null) { defaultCommand = command; return this; }

		commands.put(command.getName(), command);
		return this;
	}

	public BasicCommandHandler registerDefaultHelp() {
		registerCommand(new HelpCommand(this));
		return this;
	}

	public BasicCommandHandler registerDefaultReload() {
		registerCommand(new ReloadCommand(this));
		return this;
	}

	/**
	 * <p>registerHandler.</p>
	 */
	public BasicCommandHandler registerHandler() {
		if (plugin.getCommand(label) != null)
			plugin.getCommand(label).setExecutor(this);
		else
			throw new IllegalStateException("Command is null because it not added in plugin.yml");
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ECommand cmd = null;

		if (args.length == 0 || commands.get(args[0]) == null) cmd = defaultCommand;
		else {
			cmd = commands.get(args[0]);
			args = EText.trimArgs(args);
		}

		if (cmd.getPermission() == null || cmd.testConditions(sender) || sender.isOp())
			cmd.use(sender, args);
		else
			sender.sendMessage("Unknown command!");

		return true;
	}

	@Override public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> list = new ArrayList<>();
		if (command.getName().equalsIgnoreCase(label)) {
			if (args.length == 1) {
				list.add(getDefaultCommand().getName());
				for (final ECommand cmd : getRegisteredCommands())
					list.add(cmd.getName());

				return list;
			}
		}
		return null;
	}

	private static final class HelpCommand extends BasicCommand {

		public HelpCommand(BasicCommandHandler handler) {
			super(handler, "help", (s,args) -> {
				EText.send(s, "&c»------>&5[&6" + handler.plugin.getName() +"&5&l]");
				final List<ECommand> commands = new ArrayList<>(handler.getRegisteredCommands());
				commands.add(handler.getDefaultCommand());
				for (final ECommand command : commands)
					if (command.testConditions(s))
						EText.send(s,
								"&6" + command.getUsage() +
								" - &2" + command.getDescription() +
								(s.isOp() || s.hasPermission("*") ? " &f- &5" + command.getPermission() : ""));
				EText.send(s, "&c»------>&5[&6" + handler.plugin.getName() +"&5&l]");
			});
		}

	}

	private static final class ReloadCommand extends BasicCommand {

		private static Class<?> EJPLUGIN;
		private static Method reloadPlugin;

		static {
			try {
				EJPLUGIN = Class.forName("ru.aslcraft.ejcore.plugin.EJPlugin");
				reloadPlugin = EJPLUGIN.getMethod("reloadPlugin");
			} catch (final ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				EJPLUGIN = null;
				EText.warn("EJPlugin class not finded, maybe you has installed an built-in version of this API. Default reload command cannot be initialised.");
			}
		}

		public ReloadCommand(BasicCommandHandler handler) {
			super(handler, "reload", (s,args) -> {
				if (!EJPLUGIN.isAssignableFrom(handler.plugin.getClass())) {
					try {
						reloadPlugin.invoke(handler.plugin);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						EText.warn("Something went wrong while executing reloadPlugin method for plugin " + handler.plugin.getName() + ", maybe you has issues with ejCore below");
						e.printStackTrace();
					}
				} else {
					EText.warn("Plugin " + handler.plugin.getName() + " tried to use built-in ejCore reload command, but not extends from EJPlugin class");
				}
			});
			senderType = SenderType.ALL;
		}

	}

}
