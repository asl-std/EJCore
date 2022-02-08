package ru.asl.api.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.asl.api.ejcore.equip.EquipSlot;

@AllArgsConstructor
public class EquipChangeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
	public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Getter private EquipSlot equipSlot;
    @Getter private ItemStack itemStack;
    @Getter private Player player;

}
