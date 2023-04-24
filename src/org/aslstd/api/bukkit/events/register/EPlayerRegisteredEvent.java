package org.aslstd.api.bukkit.events.register;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * <p>EPlayerRegisteredEvent class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class EPlayerRegisteredEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() { return HANDLERS; }

	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter private EPlayer player;

	/**
	 * <p>Constructor for EPlayerRegisteredEvent.</p>
	 *
	 * @param who a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
	 */
	public EPlayerRegisteredEvent(EPlayer who) {
		player = who;
	}

}
