package ru.asl.api.bukkit.command.interfaze;

public interface Usable<C, A> {

	void execute(C sender, A args);

}
