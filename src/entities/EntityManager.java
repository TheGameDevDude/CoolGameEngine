package entities;

import java.util.ArrayList;
import java.util.List;

import collision.Chunk;

public class EntityManager {

	//for entities that are static 
	public List<Entity> staticEntities = new ArrayList<Entity>();
	public List<Chunk> chunks = new ArrayList<Chunk>();

	private int CHUNK_SIZE;
	private int sizeX;
	private int sizeZ;

	public EntityManager(int CHUNK_SIZE, int sizeX, int sizeZ) {
		this.CHUNK_SIZE = CHUNK_SIZE;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
	}

	public void getAABBFromEntities(List<Entity> entities) {
		//adding entities in static entities array
		for (Entity entity : entities) {
			this.staticEntities.add(entity);
		}
	}

	//storing all entities in a uniform grid (a cell is a chunk)
	public void placeAllEntitiesInChunks() {
		List<Entity> tempEntities = new ArrayList<Entity>();
		for (int z = 0; z < sizeZ; z++) {
			for (int x = 0; x < sizeX; x++) {

				float xCenterGrid = (x * (float) CHUNK_SIZE) + (float) CHUNK_SIZE / 2;
				float zCenterGrid = (z * (float) CHUNK_SIZE) + (float) CHUNK_SIZE / 2;

				for (Entity entity : staticEntities) {

					if (entity.aabb != null) {
						float xDist = Math.abs(xCenterGrid - entity.aabb.getCenter().Xpos);
						float zDist = Math.abs(zCenterGrid - entity.aabb.getCenter().Zpos);

						float xx = ((float) CHUNK_SIZE / 2) + ((entity.aabb.nearRight.Xpos - entity.aabb.farLeft.Xpos) / 2);
						float zz = ((float) CHUNK_SIZE / 2) + ((entity.aabb.nearRight.Zpos - entity.aabb.farLeft.Zpos) / 2);

						if ((xDist <= xx) && (zDist <= zz)) {
							tempEntities.add(entity);
						}
					}
				}
				chunks.add(new Chunk(CHUNK_SIZE, tempEntities));
				tempEntities.clear();
			}
		}
	}

	public int getChunkSize() {
		return CHUNK_SIZE;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeZ() {
		return sizeZ;
	}
}
