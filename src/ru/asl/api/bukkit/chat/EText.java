package ru.asl.api.bukkit.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class EText {

	public static final String lineBreak = "&4################################################################";
	public static final String halfLineBreak = "&4################################";

	/**
	 * Send line with 64 '#' red chars
	 */
	public void sendLB() { Bukkit.getConsoleSender().sendMessage(c(lineBreak)); }

	/**
	 * Send line with 32 '#' red chars
	 */
	public void sendHLB() { Bukkit.getConsoleSender().sendMessage(c(halfLineBreak)); }

	/**
	 * Apply all '&' at color.
	 */
	public String c(String msg) { return ChatColor.translateAlternateColorCodes('&', msg); }

	/**
	 * Strips all '§' color codes
	 */
	public String s(String msg) { return ChatColor.stripColor(msg); }

	/**
	 * Erases all '&' and '§' color codes;
	 */
	public String e(String msg) { return s(c(msg)); }

}
