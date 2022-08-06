package ru.aslcraft.api.ejinventory.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * <p>PageReturnItemsEvent class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class PageReturnItemsEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    /** {@inheritDoc} */
    @Override
	public HandlerList getHandlers() {
        return PageReturnItemsEvent.HANDLERS;
    }

    /**
     * <p>getHandlerList.</p>
     *
     * @return a {@link org.bukkit.event.HandlerList} object
     */
    public static HandlerList getHandlerList() {
        return PageReturnItemsEvent.HANDLERS;
    }


}
