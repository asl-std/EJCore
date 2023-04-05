package org.aslstd.api.bukkit.location;


/**
 * <p>Direction class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public enum Direction {
	XZ(new Vector3D(1,0,1)),
	XZR(new Vector3D(1,0,-1)),
	ZX(new Vector3D(-1,0,1)),
	ZXR(new Vector3D(-1,0,-1)),
	X(new Vector3D(1,0,0)),
	Y(new Vector3D(0,1,0)),
	Z(new Vector3D(0,0,1)),
	NULL;

	Vector3D vec = new Vector3D(0,0,0);

	Direction() {}
	Direction(Vector3D vec) { this.vec = vec; }

	/**
	 * <p>getVector3D.</p>
	 *
	 * @return a {@link org.aslstd.api.bukkit.location.Vector3D} object
	 */
	public Vector3D getVector3D() { return vec.clone(); }

}
