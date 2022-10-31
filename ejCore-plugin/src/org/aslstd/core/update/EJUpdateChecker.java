package org.aslstd.core.update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.bukkit.value.util.ValueUtil;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

public class EJUpdateChecker {

	private static List<EJPlugin> registeredEJPlugins = new ArrayList<>();

	public static void checkUpdates() {

		for (final EJPlugin plugin : registeredEJPlugins) {

			getVersion(version -> {

				plugin.setLatestVersion(version);
				plugin.setLatestBuild(parseBuild(version, true));

			}, plugin);

		}

	}

	public static void sendUpdateMessage(CommandSender p) {
		if (p instanceof ConsoleCommandSender)
			EText.sendLB();

		final String prefix = ((p instanceof ConsoleCommandSender) ? "&5[&2EJC&5]&f: &3> " : "");

		if (p instanceof Player)
			EText.send(p, "&c»------>&5 ejCore Update Checker");

		for (final EJPlugin plugin : registeredEJPlugins) {

			final boolean isUpToDate = plugin.getBuild() >= plugin.getLatestBuild();

			if (isUpToDate)
				EText.send(p, prefix + " &2• " + plugin.getDescription().getFullName() + ": No new updates");
			else
				EText.send(p, prefix + " &2• " + plugin.getDescription().getFullName() + ": New Update - " + plugin.getLatestVersion());
		}
		EText.send(p, prefix + "&6You can download new versions here:");
		EText.send(p, prefix + "&3https://www.spigotmc.org/resources/authors/115181/");

		if (p instanceof Player)
			EText.send(p, "&c»------>&5 ejCore Update Checker");

		if (p instanceof ConsoleCommandSender)
			EText.sendLB();
	}

	private static void getVersion(final Consumer<String> consumer, EJPlugin plugin) {
		if (plugin.getResourceId() == -1)
			return;

		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + plugin.getResourceId()).openStream(); Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext())
					consumer.accept(scanner.next());

			} catch (final IOException exception) {
				EText.warn("Cannot look for updates (" + plugin.getDescription().getName() + "): " + exception.getMessage());
			}
		});
	}

	public static void registerEJPlugin(EJPlugin ejp) {

		final int build = parseBuild(ejp.getDescription().getVersion(), false);
		ejp.setBuild(build);

		if (ejp.getResourceId() == -1)
			return;

		registeredEJPlugins.add(ejp);

	}

	private static String cleanVersion(String version, boolean fromSite) {
		String prepaired = version.replaceAll("\\s", "");

		prepaired = prepaired.substring(0, (prepaired.indexOf("[") == -1 ? prepaired.length() : prepaired.indexOf("[")));
		prepaired = prepaired.substring(0, (prepaired.indexOf("-") == -1 ? prepaired.length() : prepaired.indexOf("-")));

		return prepaired;
	}

	private static int parseBuild(String version, boolean fromSite) {
		final String prepaired = cleanVersion(version, fromSite);

		return ValueUtil.parseInteger(prepaired.substring(prepaired.lastIndexOf(".")+1));
	}

}
