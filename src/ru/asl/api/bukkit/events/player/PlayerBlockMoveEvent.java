package ru.asl.api.bukkit.events.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import lombok.Getter;
import lombok.Setter;
import ru.asl.api.bukkit.location.Vector3D;

public class PlayerBlockMoveEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	@Override public HandlerList getHandlers() { return HANDLERS; }
	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter @Setter private Vector3D from;
	@Getter  private Vector3D to;

	@Getter @Setter private boolean cancelled;

	@Getter private boolean locChanged;

	public PlayerBlockMoveEvent(Player who, Location from, Location to) {
		super(who);
		this.from = Vector3D.fromLocation(from);
		this.to = Vector3D.fromLocation(to);
	}

	public void setTo(Vector3D to) {
		this.to = to;
		locChanged = true;
	}

}
