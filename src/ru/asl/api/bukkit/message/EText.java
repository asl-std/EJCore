package ru.asl.api.bukkit.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class EText {

	public static boolean enableConsoleColoring = true;

	public static final String lineBreak = "&4################################################################";
	public static final String halfLineBreak = "&4################################";

	public static final String prefix = "&5[&2EJC&5]&f:";

	public static void warn(String msg) { sendRaw(c(prefix + " &4> " + msg)); }

	public static void fine(String msg) { sendRaw(c(prefix + " &3> &a" + msg)); }

	public static void debug(String msg) { sendRaw(c(prefix + " &3> &e" + msg)); }

	/**
	 * Send raw message to console.
	 */
	public static void sendRaw(Object msg) { Bukkit.getConsoleSender().sendMessage(msg.toString()); }

	/**
	 * Send array with raw messages to console
	 * {@link EText#sendRaw(Object)}
	 */
	public static void sendRaw(Object[] msg) { for(Object obj : msg) sendRaw(obj); }

	/**
	 * Send message to CommandSender (Player or Console)
	 *
	 * @param obj can be {@link Player} or {@link ConsoleCommandSender}
	 */
	public static void send(Object obj, String msg) {
		if (obj instanceof Player)
			((Player)obj).sendMessage(c(msg));
		else
			if (obj instanceof ConsoleCommandSender)
				((ConsoleCommandSender)obj).sendMessage(enableConsoleColoring ? c(msg) : e(msg));
	}

	/**
	 * Send formatted message to console
	 */
	public static void send(String msg) {
		sendRaw(enableConsoleColoring ? c(msg) : e(msg));
	}

	/**
	 * Send line with 64 '#' red chars
	 */
	public static void sendLB() { send(c(lineBreak)); }

	/**
	 * Send line with 32 '#' red chars
	 */
	public static void sendHLB() { send(c(halfLineBreak)); }

	/**
	 * Apply all '&' at color.
	 */
	public static String c(String msg) { return ChatColor.translateAlternateColorCodes('&', msg); }

	/**
	 * Strips all '§' color codes
	 */
	public static String s(String msg) { return ChatColor.stripColor(msg); }

	/**
	 * Erases all '&' and '§' color codes;
	 */
	public static String e(String msg) { return s(c(msg)); }

	/**
	 * @return Given args array without first arg
	 */
	public static String[] trimArgs(String[] args) {
		if (args.length == 0) return new String[0];
		String[] ret = new String[args.length - 1];
		System.arraycopy(args, 1, ret, 0, args.length - 1);
		return ret;
	}
}
