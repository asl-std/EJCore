package org.aslstd.api.openlib.event.player;

import org.aslstd.api.bukkit.location.Vector3D;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>PlayerBlockMoveEvent class.</p>
 *
 * @author Snoop1CattZ69
 */
public class PlayerBlockMoveEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	/** {@inheritDoc} */
	@Override public HandlerList getHandlers() { return HANDLERS; }
	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter @Setter private Vector3D from;
	@Getter  private Vector3D to;

	@Getter @Setter private boolean cancelled;

	@Getter private boolean locChanged;

	/**
	 * <p>Constructor for PlayerBlockMoveEvent.</p>
	 *
	 * @param who a {@link org.bukkit.entity.Player} object
	 * @param from a {@link org.bukkit.Location} object
	 * @param to a {@link org.bukkit.Location} object
	 */
	public PlayerBlockMoveEvent(Player who, Location from, Location to) {
		super(who);
		this.from = Vector3D.fromLocation(from);
		this.to = Vector3D.fromLocation(to);
	}

	/**
	 * <p>Setter for the field <code>to</code>.</p>
	 *
	 * @param to a {@link org.aslstd.api.bukkit.location.Vector3D} object
	 */
	public void setTo(Vector3D to) {
		this.to = to;
		locChanged = true;
	}

}
