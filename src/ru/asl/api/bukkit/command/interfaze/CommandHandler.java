package ru.asl.api.bukkit.command.interfaze;

import org.bukkit.command.CommandExecutor;

public interface CommandHandler extends CommandExecutor {

	ECommand getDefaultCommand();

	void registerCommand(ECommand command);
}
