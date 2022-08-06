package ru.aslcraft.api.bukkit.command.interfaze;

/**
 * <p>Usable interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Usable<C, A> {

	/**
	 * <p>execute.</p>
	 *
	 * @param sender a C object
	 * @param args a A object
	 */
	void execute(C sender, A args);

}
