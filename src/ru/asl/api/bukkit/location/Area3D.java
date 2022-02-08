package ru.asl.api.bukkit.location;

import org.bukkit.Location;

public class Area3D {

	private Vector3D from, to;

	public Area3D(Vector3D fPos, Vector3D sPos) {
		from = Vector3D.getMinimum(fPos, sPos);
		to = Vector3D.getMaximum(fPos, sPos);
	}

	public Vector3D getFirstPosition() { return from.clone(); }
	public Vector3D getSecondPosition() { return to.clone(); }

	public boolean isInArea(Location loc) {
		return isInArea(new Vector3D(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
	}

	public boolean isInArea(Vector3D point) {
		return 	point.getX() >= from.getX() &&
				point.getX() <= to.getX() &&
				point.getZ() >= from.getZ() &&
				point.getZ() <= to.getZ();
	}


}
