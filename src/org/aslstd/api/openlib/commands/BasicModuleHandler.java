package org.aslstd.api.openlib.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.aslstd.api.bukkit.command.interfaze.CommandHandler;
import org.aslstd.api.bukkit.command.interfaze.ECommand;
import org.aslstd.api.bukkit.message.Text;
import org.aslstd.core.OpenLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

/**
 * <p>Abstract BasicModuleHandler class.</p>
 *
 * @author Snoop1CattZ69
 */
public abstract class BasicModuleHandler extends BukkitCommand implements CommandHandler {

	/**
	 * <p>Constructor for BasicModuleHandler.</p>
	 *
	 * @param def a {@link org.aslstd.api.bukkit.command.interfaze.ECommand} object
	 * @param name a {@link String} object
	 */
	public BasicModuleHandler(ECommand def, String name) {
		super(name);
		defCommand = def;
	}

	protected Map<String, ECommand> commands = new HashMap<>();

	/**
	 * <p>getRegisteredCommands.</p>
	 *
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<ECommand> getRegisteredCommands() {
		return commands.values();
	}

	private ECommand defCommand;

	/** {@inheritDoc} */
	@Override
	public ECommand getDefaultCommand() {
		return defCommand;
	}

	/** {@inheritDoc} */
	@Override
	public BasicModuleHandler registerCommand(ECommand command) {
		commands.put(command.getName(), command);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		ECommand cmd;
		if (args.length == 0 || commands.get(args[0]) == null) cmd = defCommand;
		else {
			cmd = commands.get(args[0]);
			args = Text.trimArgs(args);
		}

		if (cmd.getPermission() == null || sender.hasPermission(cmd.getPermission())) cmd.use(sender, args);
		else sender.sendMessage("Unknown command!");

		return true;
	}

	/**
	 * <p>registerModuleCommand.</p>
	 *
	 * @param moduleCommand a {@link org.aslstd.api.openlib.commands.BasicModuleHandler} object
	 * @param mainAlias a {@link String} object
	 */
	public static void registerModuleCommand(BasicModuleHandler moduleCommand, String mainAlias) {
		try {
			final Method craftServer = OpenLib.instance().getServer().getClass().getMethod("getCommandMap");
			craftServer.setAccessible(true);

			final CommandMap cMap = (CommandMap) craftServer.invoke(OpenLib.instance().getServer());

			cMap.register(mainAlias, moduleCommand.getUsage(), moduleCommand);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
