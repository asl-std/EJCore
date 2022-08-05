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

/**
 * <p>EText class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class EText {

	/** Constant <code>enableConsoleColoring=true</code> */
	public static boolean enableConsoleColoring = true;

	/** Constant <code>COLOR_CHAR='\u00A7'</code> */
	public static final char COLOR_CHAR = '\u00A7';

	/** Constant <code>lineBreak="&amp;4#################################"{trunked}</code> */
	public static final String lineBreak = "&4######################################################################";
	/** Constant <code>halfLineBreak="&amp;4#################################"{trunked}</code> */
	public static final String halfLineBreak = "&4###################################";

	/** Constant <code>prefix="EJC"</code> */
	public static final String prefix = "EJC";

	/**
	 * <p>warn.</p>
	 *
	 * @param msg a {@link java.lang.String} object
	 */
	public static void warn(String msg) { warn(msg, prefix); }

	/**
	 * <p>fine.</p>
	 *
	 * @param msg a {@link java.lang.String} object
	 */
	public static void fine(String msg) { fine(msg, prefix); }

	/**
	 * <p>debug.</p>
	 *
	 * @param msg a {@link java.lang.String} object
	 */
	public static void debug(String msg) { debug(msg, prefix); }

	/**
	 * <p>warn.</p>
	 *
	 * @param msg a {@link java.lang.String} object
	 * @param prefix a {@link java.lang.String} object
	 */
	public static void warn(String msg, String prefix) { send("&5[&2"+ prefix +"&5]&f: &4> " + msg); }

	/**
	 * <p>fine.</p>
	 *
	 * @param msg a {@link java.lang.String} object
	 * @param prefix a {@link java.lang.String} object
	 */
	public static void fine(String msg, String prefix) { send("&5[&2"+ prefix +"&5]&f: &3> &a" + msg); }

	/**
	 * <p>debug.</p>
	 *
	 * @param msg a {@link java.lang.String} object
	 * @param prefix a {@link java.lang.String} object
	 */
	public static void debug(String msg, String prefix) { if (Core.getCfg().DEBUG_RUNNING) send("&5[&2"+ prefix +"&5]&f: &3> &e" + msg); }

	/** Constant <code>df</code> */
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
	 *
	 * @param value a double
	 * @return a {@link java.lang.String} object
	 */
	public static String format(double value) { return df.format(value); }

	/**
	 * Send raw message to console.
	 *
	 * @param msg a {@link java.lang.Object} object
	 */
	public static void sendRaw(Object msg) {
		if (msg == null)
			Bukkit.getConsoleSender().sendMessage("null");
		else
			Bukkit.getConsoleSender().sendMessage(msg.toString());
	}

	/**
	 * Send array with raw messages to console
	 * {@link ru.asl.api.bukkit.message.EText#sendRaw(Object)}
	 *
	 * @param msg an array of {@link java.lang.Object} objects
	 */
	public static void send(Object[] msg) { for(final Object obj : msg) sendRaw(obj); }

	/**
	 * Send message to CommandSender (Player or Console)
	 *
	 * @param receiver can be {@link org.bukkit.entity.Player} or {@link org.bukkit.command.ConsoleCommandSender}
	 * @param msg a {@link java.lang.String} object
	 */
	public static void send(Object receiver, String msg) {
		if (receiver instanceof Player)
			((Player)receiver).sendMessage(c(msg));

		if (receiver instanceof ConsoleCommandSender)
			send(msg);
	}

	/**
	 * Send formatted message to console
	 *
	 * @param msg a {@link java.lang.String} object
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
	 * Colorizes all "&amp;" and hex colors
	 *
	 * @param msg a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String c(String msg) {
		msg = ChatColor.translateAlternateColorCodes('&', msg);

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_16)) {
			final Pattern hexPattern = Pattern.compile("\\#([A-Fa-f0-9]{6})");
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
	 *
	 * @param msg a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String s(String msg) { return ChatColor.stripColor(msg); }

	/**
	 * Erases all '&amp;' and '§amp;' color codes
	 *
	 * @param msg a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String e(String msg) { return s(c(msg)); }

	/**
	 * <p>translateHexColorCodes.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
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
	 *
	 * @param args an array of {@link java.lang.String} objects
	 * @return an array of {@link java.lang.String} objects
	 */
	public static String[] trimArgs(String[] args) {
		if (args.length == 0) return new String[0];
		final String[] ret = new String[args.length - 1];
		System.arraycopy(args, 1, ret, 0, args.length - 1);
		return ret;
	}

	/**
	 * <p>getSpaced.</p>
	 *
	 * @param args a {@link java.lang.Object} object
	 * @return a {@link java.lang.String} object
	 */
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
