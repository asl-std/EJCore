package ru.asl.api.bukkit.message;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import ru.asl.api.ejcore.utils.ServerVersion;
import ru.asl.core.Core;

public class EText {

	public static boolean enableConsoleColoring = true;

	public static final char COLOR_CHAR = '\u00A7';

	public static final String lineBreak = "&4######################################################################";
	public static final String halfLineBreak = "&4###################################";

	public static final String prefix = "EJC";

	public static void warn(String msg) { warn(msg, prefix); }

	public static void fine(String msg) { fine(msg, prefix); }

	public static void debug(String msg) { debug(msg, prefix); }

	public static void warn(String msg, String prefix) { send("&5[&2"+ prefix +"&5]&f: &4> " + msg); }

	public static void fine(String msg, String prefix) { send("&5[&2"+ prefix +"&5]&f: &3> &a" + msg); }

	public static void debug(String msg, String prefix) { if (Core.getCfg().DEBUG_RUNNING) send("&5[&2"+ prefix +"&5]&f: &3> &e" + msg); }

	public static DecimalFormat df;

	static {
		df = new DecimalFormat();

		df.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("en", "US")));
		// Default decimal separator: '.'
		df.getDecimalFormatSymbols().setDecimalSeparator('.');
		// Default decimals will rounded to 2 digits
		df.applyPattern("0.0#");

		// Don't change this
		df.setNegativePrefix("-");
		df.setPositivePrefix("");

		df.setRoundingMode(RoundingMode.CEILING);
	}

	/**
	 * Round decimals to 2 digits after separator
	 */
	public static String format(double value) { return df.format(value); }

	/**
	 * Send raw message to console.
	 */
	public static void sendRaw(Object msg) {
		if (msg == null)
			Bukkit.getConsoleSender().sendMessage("null");
		else
			Bukkit.getConsoleSender().sendMessage(msg.toString());
	}

	/**
	 * Send array with raw messages to console
	 * {@link EText#sendRaw(Object)}
	 */
	public static void send(Object[] msg) { for(final Object obj : msg) sendRaw(obj); }

	/**
	 * Send message to CommandSender (Player or Console)
	 *
	 * @param receiver can be {@link Player} or {@link ConsoleCommandSender}
	 */
	public static void send(Object receiver, String msg) {
		if (receiver instanceof Player)
			((Player)receiver).sendMessage(c(msg));

		if (receiver instanceof ConsoleCommandSender)
			send(msg);
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
	public static void sendLB() { send(lineBreak); }

	/**
	 * Send line with 32 '#' red chars
	 */
	public static void sendHLB() { send(halfLineBreak); }

	/**
	 * Colorises all '&' and hex colors
	 */
	public static String c(String msg) {
		msg = ChatColor.translateAlternateColorCodes('&', msg);

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_16)) {
			final Pattern hexPattern = Pattern.compile("\\&\\#([A-Fa-f0-9]{6})");
			final Matcher matcher = hexPattern.matcher(msg);
			final StringBuffer buffer = new StringBuffer(msg.length() + 4 * 8);
			while (matcher.find())
			{
				final String group = matcher.group(1);
				matcher.appendReplacement(buffer, COLOR_CHAR + "x"
						+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
						+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
						+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
						);
			}
			msg = matcher.appendTail(buffer).toString();
		}

		return msg;
	}

	/**
	 * Strips all '§' color codes
	 */
	public static String s(String msg) { return ChatColor.stripColor(msg); }

	/**
	 * Erases all '&' and '§' color codes
	 */
	public static String e(String msg) { return s(c(msg)); }

	public String translateHexColorCodes(String message) {
		final Pattern hexPattern = Pattern.compile("[#" + "([A-Fa-f0-9]{6})" + "]");
		final Matcher matcher = hexPattern.matcher(message);
		final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

		while (matcher.find())
		{
			final String group = matcher.group(1);
			matcher.appendReplacement(buffer, COLOR_CHAR + "x"
					+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
					+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
					+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
					);
		}
		return matcher.appendTail(buffer).toString();
	}

	/**
	 * Trims first argument and return all others
	 */
	public static String[] trimArgs(String[] args) {
		if (args.length == 0) return new String[0];
		final String[] ret = new String[args.length - 1];
		System.arraycopy(args, 1, ret, 0, args.length - 1);
		return ret;
	}

	public static String getSpaced(Object... args) {
		final StringBuffer buff = new StringBuffer();

		for (int i = 0 ; i < args.length ; i++) {

			if (args[i] instanceof Double) {
				args[i] = (int) Math.floor((Double)args[i]);
			}

			if (args[i] != null)
				buff.append(args[i] + (i != args.length-1 ? " " : ""));
		}

		return buff.toString();
	}
}
