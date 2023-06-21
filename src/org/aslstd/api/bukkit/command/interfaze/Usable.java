package org.aslstd.api.bukkit.command.interfaze;

/**
 * <p>Usable interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface Usable<C, A> {

	/**
	 * <p>execute.</p>
	 *
	 * @param sender a C object
	 * @param args a A object
	 */
	String execute(C sender, A args);

}
