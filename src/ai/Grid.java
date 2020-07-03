package ai;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.EntityManager;
import math.Vector3f;

public class Grid {
	public List<Node> grid = new ArrayList<Node>();

	public float NODE_SIZE;
	public int sizeX;
	public int sizeZ;

	//figuring out obstacles in the grid by using all entities collision box
	public Grid(float NODE_SIZE, int sizeX, int sizeZ, EntityManager entityManager) {
		this.NODE_SIZE = NODE_SIZE;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		for (int z = 0; z < sizeZ; z++) {
			for (int x = 0; x < sizeX; x++) {

				float xCenterGrid = (x * NODE_SIZE) + NODE_SIZE / 2;
				float zCenterGrid = (z * NODE_SIZE) + NODE_SIZE / 2;

				Vector3f worldPosition = new Vector3f(xCenterGrid, 0, zCenterGrid);
				Node node = new Node(true, worldPosition, x, z);
				grid.add(node);

				for (Entity entity : entityManager.staticEntities) {
					float xDist = Math.abs(xCenterGrid - entity.aabb.getCenter().Xpos);
					float zDist = Math.abs(zCenterGrid - entity.aabb.getCenter().Zpos);

					float xx = (NODE_SIZE / 2) + ((entity.aabb.nearRight.Xpos - entity.aabb.farLeft.Xpos) * 1.1f / 2);
					float zz = (NODE_SIZE / 2) + ((entity.aabb.nearRight.Zpos - entity.aabb.farLeft.Zpos) * 1.1f / 2);

					if ((xDist <= xx) && (zDist <= zz)) {
						if (entity.aabb.info != null) {
							//if aabbs info is 1 then mark the grid as not walkable or an obstacle
							if (entity.aabb.info[1] == 1) {
								grid.get(x + z * sizeX).walkable = false;
							}
						}
					}
				}
			}
		}
	}

	public List<Node> getNeighbours(Node node) {
		List<Node> neighbours = new ArrayList<Node>();
		for (int z = -1; z <= 1; z++) {
			for (int x = -1; x <= 1; x++) {
				if (x == 0 && z == 0) {
					continue;
				}
				int gridZ = z + node.gridZ;
				int gridX = x + node.gridX;
				if (gridZ >= 0 && gridZ < sizeZ && gridX >= 0 && gridX < sizeX) {
					neighbours.add(grid.get(gridX + gridZ * sizeX));
				}
			}
		}
		return neighbours;
	}

	public Node nodeFromWorldPoint(Vector3f position) {
		int xGrid_pos = (int) Math.floor(position.Xpos / NODE_SIZE);
		int zGrid_pos = (int) Math.floor(position.Zpos / NODE_SIZE);
		if (xGrid_pos < 0) {
			xGrid_pos = 0;
		}
		if (zGrid_pos < 0) {
			zGrid_pos = 0;
		}
		if (xGrid_pos >= sizeX - 1) {
			xGrid_pos = sizeX - 1;
		}
		if (zGrid_pos >= sizeZ - 1) {
			zGrid_pos = sizeZ - 1;
		}
		return grid.get(xGrid_pos + zGrid_pos * sizeX);
	}
}
