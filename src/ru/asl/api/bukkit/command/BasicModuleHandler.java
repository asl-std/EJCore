package ru.asl.api.bukkit.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import ru.asl.api.bukkit.command.interfaze.CommandHandler;
import ru.asl.api.bukkit.command.interfaze.ECommand;
import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

public abstract class BasicModuleHandler extends BukkitCommand implements CommandHandler {

	public BasicModuleHandler(ECommand def, String name) {
		super(name);
		defCommand = def;
	}

	protected Map<String, ECommand> commands = new HashMap<>();

	public Collection<ECommand> getRegisteredCommands() {
		return commands.values();
	}

	private ECommand defCommand;

	@Override
	public ECommand getDefaultCommand() {
		return defCommand;
	}

	@Override
	public void registerCommand(ECommand command) {
		commands.put(command.getName(), command);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		ECommand cmd;
		if (args.length == 0 || commands.get(args[0]) == null) cmd = defCommand;
		else {
			cmd = commands.get(args[0]);
			args = EText.trimArgs(args);
		}

		if (cmd.getPermission() == null || sender.hasPermission(cmd.getPermission())) cmd.use(sender, args);
		else sender.sendMessage("Unknown command!");

		return true;
	}

	public static void registerModuleCommand(BasicModuleHandler moduleCommand, String mainAlias) {
		try {
			Method craftServer = Core.instance().getServer().getClass().getMethod("getCommandMap");
			craftServer.setAccessible(true);

			CommandMap cMap = (CommandMap) craftServer.invoke(Core.instance().getServer());

			cMap.register(mainAlias, moduleCommand.getUsage(), moduleCommand);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
