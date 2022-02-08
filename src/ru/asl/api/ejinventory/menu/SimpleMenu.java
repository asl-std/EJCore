package ru.asl.api.ejinventory.menu;

import ru.asl.api.ejinventory.page.LockedPage;
import ru.asl.api.ejinventory.pane.SimplePane;

public class SimpleMenu extends SimplePane {

	public LockedPage getPage() { return (LockedPage) page; }

	public SimpleMenu(String title, int size) {
		super(title, size, new LockedPage(size/9, title));
	}

}
