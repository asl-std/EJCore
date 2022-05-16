package ru.asl.api.ejinventory.menu;

import ru.asl.api.ejinventory.page.LockedPage;
import ru.asl.api.ejinventory.pane.SimplePane;

/**
 * <p>SimpleMenu class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class SimpleMenu extends SimplePane {

	/**
	 * <p>getPage.</p>
	 *
	 * @return a {@link ru.asl.api.ejinventory.page.LockedPage} object
	 */
	public LockedPage getPage() { return (LockedPage) page; }

	/**
	 * <p>Constructor for SimpleMenu.</p>
	 *
	 * @param title a {@link java.lang.String} object
	 * @param size a int
	 */
	public SimpleMenu(String title, int size) {
		super(title, size, new LockedPage(size/9, title));
	}

}
