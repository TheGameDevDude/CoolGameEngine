package entities;

import ai.PathFinding;
import collision.AABB;
import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;
import model.Model;

public class Zombie extends Entity {
	public Vector3f direction;

	private int xGrid_pos;
	private int zGrid_pos;

	private float SPEED = 1.2f;
	private float GRAVITY = 5.0f;
	private float JUMP_FORCE = 1.7f;

	public float health = 10;

	private float acceleration;

	private boolean isGrounded;
	private boolean right;
	private boolean left;
	private boolean front;
	private boolean back;
	private boolean up;
	private boolean down;

	public Zombie(Vector3f position, Vector3f rotation, float scale, Model model, AABB aabb) {
		super(position, rotation, scale, model, aabb);
		aabb = new AABB(new Vector3f(position.Xpos - 0.1f, position.Ypos + 0.25f, position.Zpos - 0.1f), new Vector3f(position.Xpos + 0.1f, position.Ypos - 0.25f, position.Zpos + 0.1f), null);
		acceleration = 0;
		isGrounded = false;
		right = left = front = back = up = down = false;
		direction = new Vector3f(-1, 0, 0);
	}

	public void tick(Player player, Camera camera, PathFinding pathFinding, EntityManager entityManager, float deltaTime) {
		getGridPos(entityManager);
		lookAtPlayer(camera);
		moveToPlayer(player, deltaTime, pathFinding, entityManager);
		jump(false, deltaTime, entityManager);
	}

	public void dealDamage(Player player, float deltaTime) {
		if (player.hitPoint != null) {
			if (player.hitPoint.Xpos >= aabb.farLeft.Xpos && player.hitPoint.Xpos <= aabb.nearRight.Xpos && player.hitPoint.Ypos >= aabb.nearRight.Ypos && player.hitPoint.Ypos <= aabb.farLeft.Ypos && player.hitPoint.Zpos >= aabb.farLeft.Zpos && player.hitPoint.Zpos <= aabb.nearRight.Zpos && player.fire == true) {
				health -= player.damage * deltaTime;
			}
		}
	}

	private void checkCollisionX(EntityManager entityManager) {
		for (Entity entity : entityManager.chunks.get(xGrid_pos + zGrid_pos * entityManager.getSizeX()).staticEntities) {
			if (entity.aabb.check(aabb) == true) {
				if (left == true) {
					position.Xpos += (entity.aabb.nearRight.Xpos - aabb.farLeft.Xpos) + 0.001f;
				} else if (right == true) {
					position.Xpos -= (aabb.nearRight.Xpos - entity.aabb.farLeft.Xpos) + 0.001f;
				}
				return;
			}
		}
	}

	private void checkCollisionY(EntityManager entityManager) {
		for (Entity entity : entityManager.chunks.get(xGrid_pos + zGrid_pos * entityManager.getSizeX()).staticEntities) {
			if (entity.aabb.check(aabb) == true) {
				if (down == true) {
					position.Ypos += (entity.aabb.farLeft.Ypos - aabb.nearRight.Ypos) + 0.001f;
					acceleration = 0;
					isGrounded = true;
				} else if (up == true) {
					position.Ypos -= (aabb.farLeft.Ypos - entity.aabb.nearRight.Ypos) + 0.001f;
					acceleration += (aabb.farLeft.Ypos - entity.aabb.nearRight.Ypos) + JUMP_FORCE + 0.001f;
				}
				return;
			}
		}
	}

	private void checkCollisionZ(EntityManager entityManager) {
		for (Entity entity : entityManager.chunks.get(xGrid_pos + zGrid_pos * entityManager.getSizeX()).staticEntities) {
			if (entity.aabb.check(aabb) == true) {
				if (front == true) {
					position.Zpos += (entity.aabb.nearRight.Zpos - aabb.farLeft.Zpos) + 0.001f;
				} else if (back == true) {
					position.Zpos -= (aabb.nearRight.Zpos - entity.aabb.farLeft.Zpos) + 0.001f;
				}
				return;
			}
		}
	}

	private void updateZombieAABB() {
		aabb = new AABB(new Vector3f(position.Xpos - 0.1f, position.Ypos + 0.25f, position.Zpos - 0.1f), new Vector3f(position.Xpos + 0.1f, position.Ypos - 0.25f, position.Zpos + 0.1f), null);
	}

	private void lookAtPlayer(Camera camera) {
		Vector3f lookDireection = new Vector3f(0.0f, 0.0f, -1.0f);
		Vector3f zWRTCamera = new Vector3f(camera.position.Xpos - position.Xpos, 0.0f, camera.position.Zpos - position.Zpos);
		zWRTCamera.normalize();
		Vector3f cross = new Vector3f().cross(zWRTCamera, lookDireection);
		if (cross.Ypos > 0) {
			rotation.Ypos = (float) Math.toDegrees(Math.acos(zWRTCamera.Zpos));
		} else {
			rotation.Ypos = 360.0f - (float) Math.toDegrees(Math.acos(zWRTCamera.Zpos));
		}
	}

	private void moveToPlayer(Player player, float deltaTime, PathFinding pathFinding, EntityManager entityManager) {
		// using A* pathfinding
		pathFinding.findPath(position, player.position);
		if (pathFinding.reversePath.size() >= 2) {
			int Xpos1 = pathFinding.reversePath.get(0).gridX;
			int Zpos1 = pathFinding.reversePath.get(0).gridZ;
			int Xpos2 = pathFinding.reversePath.get(1).gridX;
			int Zpos2 = pathFinding.reversePath.get(1).gridZ;

			// path direction from first 2 nodes
			Vector3f pathDirection = new Vector3f(Xpos2 - Xpos1, 0, Zpos2 - Zpos1);
			pathDirection.normalize();

			float rotDir = new Vector3f().cross(pathDirection, direction).Ypos;
			float dot = pathDirection.Xpos * direction.Xpos + pathDirection.Ypos * direction.Ypos + pathDirection.Zpos * direction.Zpos;
			float angleDir = (float) Math.toDegrees(Math.acos(dot));

			// updating direction of zombie by rotating zombie to the path direction from A*
			Vector4f zombieDirection = new Vector4f(direction.Xpos, direction.Ypos, direction.Zpos, 1.0f);
			if (rotDir > 0) {
				Matrix4f rotation = Matrix4f.rotateY(deltaTime * angleDir * 3.0f);
				zombieDirection = zombieDirection.multiply(rotation);
			} else {
				Matrix4f rotation = Matrix4f.rotateY(-deltaTime * angleDir * 3.0f);
				zombieDirection = zombieDirection.multiply(rotation);
			}

			direction = new Vector3f(zombieDirection.Xpos, zombieDirection.Ypos, zombieDirection.Zpos);
		}

		// if the target or start node is not walkable on grid then path direction is
		// shortest between player and zombie
		Vector3f pathDirection = new Vector3f(player.position.Xpos - position.Xpos, player.position.Ypos - position.Ypos, player.position.Zpos - position.Zpos);
		if (pathFinding.targetNode.walkable == false || pathFinding.startNode.walkable == false) {
			pathDirection.normalize();
			direction = pathDirection;
		}

		if (pathDirection.getMagnitude() < 1.0f) {
			pathDirection.normalize();
			direction = pathDirection;
		}

		if (direction.getMagnitude() >= 0.1f) {
			direction.normalize();
		}

		position.Xpos += direction.Xpos * SPEED * deltaTime;
		updateZombieAABB();
		if (direction.Xpos > 0) {
			right = true;
			left = false;
		} else {
			right = false;
			left = true;
		}
		checkCollisionX(entityManager);
		updateZombieAABB();

		position.Zpos += direction.Zpos * SPEED * deltaTime;
		updateZombieAABB();
		if (direction.Zpos > 0) {
			back = true;
			front = false;
		} else {
			front = true;
			back = false;
		}
		checkCollisionZ(entityManager);
		updateZombieAABB();
	}

	private void jump(boolean jump, float deltaTime, EntityManager entityManager) {
		if (jump == true) {
			isGrounded = false;
		} else {
			isGrounded = true;
		}
		if (isGrounded == false) {
			position.Ypos += JUMP_FORCE * deltaTime;
			updateZombieAABB();
			down = false;
			up = true;
			checkCollisionY(entityManager);
			updateZombieAABB();
		}
		acceleration += GRAVITY * deltaTime;
		position.Ypos -= acceleration * deltaTime;
		up = false;
		down = true;
		updateZombieAABB();
		checkCollisionY(entityManager);
		updateZombieAABB();
	}

	// checking whether the grid position is within the uniform grid
	private void getGridPos(EntityManager entityManager) {
		xGrid_pos = (int) Math.floor(position.Xpos / entityManager.getChunkSize());
		zGrid_pos = (int) Math.floor(position.Zpos / entityManager.getChunkSize());

		if (xGrid_pos < 0) {
			xGrid_pos = 0;
		}
		if (zGrid_pos < 0) {
			zGrid_pos = 0;
		}
		if (xGrid_pos >= entityManager.getSizeX() - 1) {
			xGrid_pos = entityManager.getSizeX() - 1;
		}
		if (zGrid_pos >= entityManager.getSizeZ() - 1) {
			zGrid_pos = entityManager.getSizeZ() - 1;
		}
	}
}
