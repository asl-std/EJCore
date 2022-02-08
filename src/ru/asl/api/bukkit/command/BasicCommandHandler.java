package ru.asl.api.bukkit.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import lombok.Getter;
import ru.asl.api.bukkit.command.interfaze.CommandHandler;
import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.api.ejcore.yaml.YAML;

public abstract class BasicCommandHandler implements CommandHandler, TabCompleter {

	protected Map<String, ECommand> commands = new HashMap<>();

	public Collection<ECommand> getRegisteredCommands() { return commands.values(); }

	private ECommand defCommand;

	@Getter protected YAML cmdFile;
	@Getter private String label;
	protected EJPlugin plugin;

	public BasicCommandHandler(EJPlugin plugin, String label) {
		this.plugin = plugin;
		cmdFile = new YAML(plugin.getDataFolder() + "/commands.yml", plugin);
		this.label = label; //cmdFile.getString("main-label", label, true);
	}

	@Override
	public ECommand getDefaultCommand() { return defCommand; }

	@Override
	public void registerCommand(ECommand command) {
		if (defCommand == null) { defCommand = command; return; }

		commands.put(command.getName(), command);
	}

	public void registerHandler() {
		plugin.getCommand(label).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ECommand cmd = null;

		if (args.length == 0 || commands.get(args[0]) == null) cmd = defCommand;
		else {
			cmd = commands.get(args[0]);
			args = EText.trimArgs(args);
		}

		if (cmd.getPermission() == null || sender.hasPermission(cmd.getPermission()) || sender.isOp())
			cmd.use(sender, args);
		else
			sender.sendMessage("Unknown command!");

		return true;
	}

}
