package ru.aslcraft.api.bukkit.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import ru.aslcraft.api.bukkit.command.interfaze.CommandHandler;
import ru.aslcraft.api.bukkit.command.interfaze.ECommand;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.core.Core;

/**
 * <p>Abstract BasicModuleHandler class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class BasicModuleHandler extends BukkitCommand implements CommandHandler {

	/**
	 * <p>Constructor for BasicModuleHandler.</p>
	 *
	 * @param def a {@link ru.aslcraft.api.bukkit.command.interfaze.ECommand} object
	 * @param name a {@link java.lang.String} object
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
	public void registerCommand(ECommand command) {
		commands.put(command.getName(), command);
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
			args = EText.trimArgs(args);
		}

		if (cmd.getPermission() == null || sender.hasPermission(cmd.getPermission())) cmd.use(sender, args);
		else sender.sendMessage("Unknown command!");

		return true;
	}

	/**
	 * <p>registerModuleCommand.</p>
	 *
	 * @param moduleCommand a {@link ru.aslcraft.api.bukkit.command.BasicModuleHandler} object
	 * @param mainAlias a {@link java.lang.String} object
	 */
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
