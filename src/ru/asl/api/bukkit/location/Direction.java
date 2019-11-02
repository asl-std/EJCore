package ru.asl.api.bukkit.location;


public enum Direction {
	XZ(new Vector3D(1,0,1)),
	XZR(new Vector3D(1,0,-1)),
	X(new Vector3D(1,0,0)),
	Y(new Vector3D(0,1,0)),
	Z(new Vector3D(0,0,1)),
	NULL;

	Vector3D vec = new Vector3D(0,0,0);

	Direction() {}
	Direction(Vector3D vec) { this.vec = vec; }

	public Vector3D getVector3D() { return vec.clone(); }

	public static Direction getBy(String key) {
		switch(key.toUpperCase()) {
			case "UP":
			case "DOWN":
			case "Y":
				return Y;
			case "X":
				return X;
			case "Z":
				return Z;
			case "DIAGONAL":
				return XZ;
			case "REVERSE_DIAGONAL":
			case "DIAGONAL_REVERSE":
				return XZR;
			default: return NULL;
		}
	}


}
