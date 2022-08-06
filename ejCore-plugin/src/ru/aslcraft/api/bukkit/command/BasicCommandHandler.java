package ru.aslcraft.api.bukkit.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import lombok.Getter;
import ru.aslcraft.api.bukkit.command.interfaze.CommandHandler;
import ru.aslcraft.api.bukkit.command.interfaze.ECommand;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.bukkit.plugin.EJPlugin;
import ru.aslcraft.api.ejcore.yaml.YAML;

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
	public Collection<ECommand> getRegisteredCommands() { return commands.values(); }

	private ECommand defCommand;

	@Getter protected YAML cmdFile;
	@Getter private String label;
	protected EJPlugin plugin;

	/**
	 * <p>Constructor for BasicCommandHandler.</p>
	 *
	 * @param plugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 * @param label a {@link java.lang.String} object
	 */
	public BasicCommandHandler(EJPlugin plugin, String label) {
		this.plugin = plugin;
		cmdFile = new YAML(plugin.getDataFolder() + "/commands.yml", plugin);
		this.label = label; //cmdFile.getString("main-label", label, true);
	}

	/** {@inheritDoc} */
	@Override
	public ECommand getDefaultCommand() { return defCommand; }

	/** {@inheritDoc} */
	@Override
	public void registerCommand(ECommand command) {
		if (defCommand == null) { defCommand = command; return; }

		commands.put(command.getName(), command);
	}

	/**
	 * <p>registerHandler.</p>
	 */
	public void registerHandler() {
		if (plugin.getCommand(label).getExecutor() != null)
			plugin.getCommand(label).setExecutor(null);

		plugin.getCommand(label).setExecutor(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ECommand cmd = null;

		if (args.length == 0 || commands.get(args[0]) == null) cmd = defCommand;
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

}
