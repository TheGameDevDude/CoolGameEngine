package ai;

import math.Vector3f;

public class Node {
	public boolean walkable;
	public Vector3f worldPosition;
	public int gridX;
	public int gridZ;
	public int gCost = 0;
	public int hCost = 0;
	public Node parent;

	public Node(boolean walkable, Vector3f worldPosition, int gridX, int gridZ) {
		this.walkable = walkable;
		this.worldPosition = worldPosition;
		this.gridX = gridX;
		this.gridZ = gridZ;
	}

	public float fCost() {
		return gCost + hCost;
	}
}
