package ru.asl.api.ejinventory.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PageReturnItemsEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
	public HandlerList getHandlers() {
        return PageReturnItemsEvent.HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return PageReturnItemsEvent.HANDLERS;
    }


}
