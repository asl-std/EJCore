package org.aslstd.api.bukkit.events.equipment;

import org.aslstd.api.bukkit.equip.EquipSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
