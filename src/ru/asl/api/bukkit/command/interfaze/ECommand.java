package ru.asl.api.bukkit.command.interfaze;

import org.bukkit.command.CommandSender;

public interface ECommand extends Perms {

	public SenderType getSenderType();

	String getDescription();

	String getName();

	String getUsage();

	void use(CommandSender sender, String[] args);
}
