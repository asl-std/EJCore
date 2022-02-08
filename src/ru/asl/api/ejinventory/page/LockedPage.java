package ru.asl.api.ejinventory.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.asl.api.ejinventory.Element;
import ru.asl.api.ejinventory.Page;
import ru.asl.api.ejinventory.element.SimpleElement;

public class LockedPage implements Page {
	public static final Element emptyElement() { return new SimpleElement(new ItemStack(Material.AIR), true); }

	private final Element[][] elements;
	private final boolean[][] unlockedSlots;

	private List<Integer> unlocked = new ArrayList<>();

	@Setter @Getter protected String title;

	public LockedPage(int height) {
		elements = new Element[9][height];
		unlockedSlots = new boolean[9][height];
		fill(LockedPage.emptyElement());
	}

	public LockedPage(int height, @NonNull String title) {
		this(height);
		this.title = title;
	}

	public Element get(int locX, int locY) {
		return elements[locX][locY];
	}

	public void unlockAll() {
		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (!isUnlocked(x+y*9)) {
					unlockedSlots[x][y] = true;
					unlocked.add(x+y*9);
				}
	}

	public void lockAll() {
		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (isUnlocked(x+y*9)) {
					unlockedSlots[x][y] = true;
					unlocked.remove(x+y*9);
				}
	}

	public void unlock(int x, int y) {
		if (isUnlocked(x+y*9)) return;

		unlockedSlots[x][y] = true;
		unlocked.add(x+y*9);
	}

	public void lock(int x, int y) {
		if (!isUnlocked(x+y*9)) return;

		unlockedSlots[x][y] = false;
		unlocked.remove(x+y*9);
	}

	public List<Integer> getUnlocked() {
		List<Integer> copy = new ArrayList<>();
		copy.addAll(unlocked);
		return copy;
	}

	@Override
	public Element[] add(Element... elements) {
		List<Element> elem = new ArrayList<>();

		for (Element e : elements)
			if (!this.add(e)) elem.add(e);

		return elem.toArray(new Element[] {});
	}

	@Override
	public boolean add(Element element) {
		Objects.requireNonNull(element);

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (elements[x][y].equals(LockedPage.emptyElement())) {
					elements[x][y] = element;
					return true;
				}
		return false;
	}

	@Override
	public boolean add(Element element, int locX, int locY, boolean force) {
		if (isInBounds(locX, locY))
			if (elements[locX][locY].equals(LockedPage.emptyElement()) && !force) {
				elements[locX][locY] = element;
				return true;
			} else {
				elements[locX][locY] = element;
				return true;
			}
		return false;
	}

	@Override
	public boolean contains(ItemStack icon) {
		if (icon == null) return false;

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (elements[x][y].equals(icon)) return true;

		return false;
	}

	public Element getElement(ItemStack icon) {
		if (icon == null) return null;

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (elements[x][y].equals(icon)) return elements[x][y];

		return null;
	}

	@Override
	public void display(Inventory inv) {
		if (inv == null) return;
		for (int x = 0; isInBounds(x, 0); x++)
			for (int y = 0; isInBounds(x, y); y++)
				elements[x][y].placeOn(inv, x, y);
	}

	@Override
	public void fill(Element element) {
		Objects.requireNonNull(element);

		for (int y = 0; y < height(); y++)
			for (int x = 0; x < width(); x++)
				elements[x][y] = element;
	}

	public void fillRow(int row, Element element) { fillRow(row, element, false); }

	public void fillRow(int row, Element element, boolean force) {
		for (int x = 0 ; isInBounds(x, row) ; x++)
			add(element, x, row, force);
	}

	public void fillColumn(int col, Element element) { fillColumn(col, element, false); }

	public void fillColumn(int col, Element element, boolean force) {
		for (int y = 0 ; isInBounds(col, y) ; y++)
			add(element, col, y, force);
	}

	@Override
	public boolean fire(InventoryClickEvent event) {
		Objects.requireNonNull(event);

		int x = event.getSlot()%9;
		int y = event.getSlot()/9;

		if (x + y * 9 == event.getSlot()) {
			if (isUnlocked(x+y*9)) return false;
			Element elem = elements[x][y];

			if (elem.equals(event.getCursor()) || elem.equals(LockedPage.emptyElement())) return false;

			if (elem.equals(event.getCurrentItem())) {
				elem.accept(event);
				return true;
			}
		}

		return false;
	}

	public boolean isUnlocked(int slot) {
		return unlocked.contains(slot);
	}

	@Override
	public int height() { return elements[0].length; }
	@Override
	public int width() { return elements.length; }

	private boolean isInBounds(int locX, int locY) {
		return locX < width() && locY < height() && locX >= 0 && locY >= 0;
	}

	@Override
	public void remove(int locX, int locY) {
		elements[locX][locY] = LockedPage.emptyElement();
	}

	@Override
	public void remove(ItemStack stack) {
		Objects.requireNonNull(stack);

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (!elements[x][y].equals(LockedPage.emptyElement()))
					if (elements[x][y].equals(stack)) {
						elements[x][y] = LockedPage.emptyElement();
						return;
					}
	}

}
