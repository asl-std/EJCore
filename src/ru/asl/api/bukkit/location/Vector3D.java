package ru.asl.api.bukkit.location;

import org.bukkit.Location;

import com.sk89q.worldedit.math.BlockVector3;

import ru.asl.api.ejcore.value.ValueUtil;

public class Vector3D implements Cloneable {

	private int x = 0, y = 0, z = 0;

	public Vector3D(int x, int y, int z) {
		this.x = x; this.y = y; this.z = z;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }

	public static Vector3D fromLocation(Location loc) {
		return new Vector3D(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public static Vector3D fromString(String loc) {
		if (loc == null) return null;
		String[] prep = loc.split(";");
		if (prep != null && prep.length < 3) return null;

		try {
			return new Vector3D(ValueUtil.parseInteger(prep[0]),ValueUtil.parseInteger(prep[1]),ValueUtil.parseInteger(prep[2]));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean compareIsPositive(Vector3D fst, Vector3D snd) {
		return fst.getX() < snd.getX() && fst.getZ() < snd.getZ();
	}

	public Vector3D setX(int x) { this.x = x; return this; }
	public Vector3D setY(int y) { this.y = y; return this; }
	public Vector3D setZ(int z) { this.z = z; return this; }

	public Vector3D incrementX() { return incrementX(1); }
	public Vector3D incrementX(int x) { this.x += x; return this; }

	public Vector3D incrementY() { return incrementY(1); }
	public Vector3D incrementY(int y) { this.y += y; return this; }

	public Vector3D incrementZ() { return incrementZ(1); }
	public Vector3D incrementZ(int z) { this.z += z; return this; }

	public Vector3D decrementX() { return decrementX(1); }
	public Vector3D decrementX(int x) { this.x -= x; return this; }

	public Vector3D decrementY() { return decrementY(1); }
	public Vector3D decrementY(int y) { this.y -= y; return this; }

	public Vector3D decrementZ() { return decrementZ(1); }
	public Vector3D decrementZ(int z) { this.z -= z; return this; }

	public Vector3D add(Vector3D vec) { x += vec.x; y += vec.y; z += vec.z; return this; }
	public Vector3D substract(Vector3D vec) { x -= vec.x; y -= vec.y; z -= vec.z; return this; }

	public Vector3D reverse() {x *= -1; y *= -1; z *= -1; return this; }

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Location) {
			Location loc = (Location) obj;
			return loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z;
		}

		if (obj instanceof Vector3D) return toString().equalsIgnoreCase(obj.toString());

		return super.equals(obj);
	}

	public Location toLocation(Location playerLocation) {
		Location copy = playerLocation;
		copy.setX(x+0.5);
		copy.setY(y);
		copy.setZ(z+0.5);

		return copy;
	}

	public BlockVector3 toBlockVector3() {
		return BlockVector3.at(x,y,z);
	}

	@Override
	public Vector3D clone() {
		return new Vector3D(x,y,z);
	}

	@Override
	public String toString() {
		return x + ";" + y + ";" + z;
	}
}
