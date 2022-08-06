package ru.aslcraft.api.bukkit.events.equipment;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.aslcraft.api.bukkit.equip.EquipSlot;

/**
 * <p>EquipChangeEvent class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public class EquipChangeEvent extends Event {
	private static final HandlerList HANDLERS = new HandlerList();
	/** {@inheritDoc} */
	@Override public HandlerList getHandlers() { return HANDLERS; }
	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter private EquipSlot equipSlot;
	@Getter private ItemStack itemStack;
	@Getter private Player player;

}
