package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import collision.AABB;
import collision.Ray;
import graphics.Renderer;
import input.Controls;
import math.Vector3f;
import model.EntityModels;

public class Player {
	public Vector3f position;
	//ray from the camera to the intersection of ray and a AABB box
	public Vector3f hitPoint;
	//players collision box
	private AABB playerAABB;
	public Weapon pistol;
	private Random random;

	private float PLAYER_HEIGHT = 0.5f;
	private float SPEED = 1.2f;
	private float GRAVITY = 5.0f;
	private float JUMP_FORCE = 1.7f;

	//to check whether the player is on ground
	private boolean isGrounded;
	private boolean right;
	private boolean left;
	private boolean front;
	private boolean back;
	private boolean up;
	private boolean down;

	//accelerate on y axis for gravity and jump
	private float acceleration;
	//grid position of player in uniform grid
	private int xGrid_pos;
	private int zGrid_pos;

	//damage the player can do using a pistol
	public float damage;
	public boolean fire = false;

	//particle for pistol when shot
	private float particleFireTimer = 0.0f;
	private List<Particle> particles = new ArrayList<Particle>();

	public Player(Vector3f position, EntityModels entityModels) {
		this.position = position;
		pistol = new Weapon(new Vector3f(), new Vector3f(0.0f, -90.0f, 0.0f), 0.2f, entityModels.pistol, entityModels.muzzleFlash, 100.0f, 0.3f);
		hitPoint = new Vector3f();
		acceleration = 0;
		isGrounded = true;
		right = left = front = back = up = down = false;
		random = new Random();
		playerAABB = new AABB(new Vector3f(position.Xpos - 0.1f, position.Ypos + 0.25f, position.Zpos - 0.1f), new Vector3f(position.Xpos + 0.1f, position.Ypos - 0.25f, position.Zpos + 0.1f), null);
	}

	public void tick(Camera camera, Controls controls, EntityManager entityManager, EntityModels entityModels, float deltaTime) {
		//update grid position in uniform grid for collision
		getGridPos(entityManager);
		//controls and collision response
		playerKeyboardControl(camera, controls, entityManager, deltaTime);
		camera.mouseControl(controls);
		hitPoint = new Ray(camera.position, camera.direction).getHitPoint(entityManager.staticEntities);
		//update weapon
		pistol.tick(deltaTime, camera, controls);
		damage = pistol.damage;
		fire = pistol.fire;
	}

	public void render(Renderer renderer) {
		pistol.render(renderer);
		if (particles.size() != 0) {
			renderer.renderParticles(particles);
		}
	}

	//collision check and response in x direction
	private void checkCollisionX(EntityManager entityManager) {
		//getting all the aabbs in that chuck where the player is, for minimal collision check
		for (Entity entity : entityManager.chunks.get(xGrid_pos + zGrid_pos * entityManager.getSizeX()).staticEntities) {
			if (entity.aabb.check(playerAABB) == true) {
				if (left == true) {
					position.Xpos += (entity.aabb.nearRight.Xpos - playerAABB.farLeft.Xpos) + 0.001f;
				} else if (right == true) {
					position.Xpos -= (playerAABB.nearRight.Xpos - entity.aabb.farLeft.Xpos) + 0.001f;
				}
				return;
			}
		}
	}

	//collision check and response in y direction
	private void checkCollisionY(EntityManager entityManager) {
		//getting all the aabbs in that chuck where the player is, for minimal collision check
		for (Entity entity : entityManager.chunks.get(xGrid_pos + zGrid_pos * entityManager.getSizeX()).staticEntities) {
			if (entity.aabb.check(playerAABB) == true) {
				if (down == true) {
					position.Ypos += (entity.aabb.farLeft.Ypos - playerAABB.nearRight.Ypos) + 0.001f;
					acceleration = 0;
					isGrounded = true;
				} else if (up == true) {
					position.Ypos -= (playerAABB.farLeft.Ypos - entity.aabb.nearRight.Ypos) + 0.001f;
					acceleration += (playerAABB.farLeft.Ypos - entity.aabb.nearRight.Ypos) + JUMP_FORCE + 0.001f;
				}
				return;
			}
		}
	}

	//collision check and response in z direction
	private void checkCollisionZ(EntityManager entityManager) {
		//getting all the aabbs in that chuck where the player is, for minimal collision check
		for (Entity entity : entityManager.chunks.get(xGrid_pos + zGrid_pos * entityManager.getSizeX()).staticEntities) {
			if (entity.aabb.check(playerAABB) == true) {
				if (front == true) {
					position.Zpos += (entity.aabb.nearRight.Zpos - playerAABB.farLeft.Zpos) + 0.001f;
				} else if (back == true) {
					position.Zpos -= (playerAABB.nearRight.Zpos - entity.aabb.farLeft.Zpos) + 0.001f;
				}
				return;
			}
		}
	}

	private void playerKeyboardControl(Camera camera, Controls controls, EntityManager aabbManager, float deltaTime) {
		float sin = (float) Math.sin(Math.toRadians(camera.yaw));
		float cos = (float) Math.cos(Math.toRadians(camera.yaw));
		float Zdir = 0;
		float Xdir = 0;

		if (controls.forward == true) {
			Zdir = -SPEED * deltaTime * cos;
			Xdir = -SPEED * deltaTime * sin;
		} else if (controls.backward == true) {
			Zdir = SPEED * deltaTime * cos;
			Xdir = SPEED * deltaTime * sin;
		}

		if (Zdir < 0) {
			front = true;
			back = false;
		} else {
			front = false;
			back = true;
		}
		position.Zpos += Zdir;
		updatePlayerAABB();
		checkCollisionZ(aabbManager);
		updatePlayerAABB();

		if (Xdir < 0) {
			left = true;
			right = false;
		} else {
			right = true;
			left = false;
		}

		position.Xpos += Xdir;
		updatePlayerAABB();
		checkCollisionX(aabbManager);
		updatePlayerAABB();

		if (controls.left == true) {
			Zdir = SPEED * deltaTime * sin;
			Xdir = -SPEED * deltaTime * cos;
		} else if (controls.right == true) {
			Zdir = -SPEED * deltaTime * sin;
			Xdir = SPEED * deltaTime * cos;
		}

		if (Zdir < 0) {
			front = true;
			back = false;
		} else {
			front = false;
			back = true;
		}
		position.Zpos += Zdir;
		updatePlayerAABB();
		checkCollisionZ(aabbManager);
		updatePlayerAABB();

		if (Xdir < 0) {
			left = true;
			right = false;
		} else {
			right = true;
			left = false;
		}

		position.Xpos += Xdir;
		updatePlayerAABB();
		checkCollisionX(aabbManager);
		updatePlayerAABB();

		//jump
		if (controls.jump == true && isGrounded == true) {
			isGrounded = false;
			position.Ypos += 0.01f;
		}

		if (isGrounded == false) {
			position.Ypos += JUMP_FORCE * deltaTime;
			updatePlayerAABB();
			down = false;
			up = true;
			checkCollisionY(aabbManager);
			updatePlayerAABB();
		}

		//player falling due to gravity
		acceleration += GRAVITY * deltaTime;
		position.Ypos -= acceleration * deltaTime;
		updatePlayerAABB();
		down = true;
		up = false;
		checkCollisionY(aabbManager);
		updatePlayerAABB();

		//changing height when you sneak
		if (controls.sneak == true) {
			PLAYER_HEIGHT -= deltaTime;
			SPEED = 0.5f;
			if (PLAYER_HEIGHT <= 0.4f) {
				PLAYER_HEIGHT = 0.4f;
			}
		} else {
			PLAYER_HEIGHT += deltaTime;
			SPEED = 1.2f;
			if (PLAYER_HEIGHT >= 0.5f) {
				PLAYER_HEIGHT = 0.5f;
			}
		}
		if (position.Ypos <= -0.5f) {
			position.Ypos = -0.5f;
			acceleration = 0;
			isGrounded = true;
		}

		camera.position = new Vector3f(position.Xpos, position.Ypos + PLAYER_HEIGHT, position.Zpos);
	}

	public void weaponParticle(Camera camera, float deltaTime, EntityModels entityModels) {
		particleFireTimer -= deltaTime;
		if (particleFireTimer <= 0) {
			particleFireTimer = 0;
		}

		if (fire == true) {
			particleFireTimer = 0.02f;
		}

		if (hitPoint != null && particleFireTimer != 0) {
			Vector3f direction = new Vector3f(-camera.direction.Xpos + random.nextInt(10) - 5.0f, -camera.direction.Ypos + random.nextInt(10) - 5.0f, -camera.direction.Zpos + random.nextInt(10) - 5.0f);
			direction.normalize();
			//particle at the hit point where the gun fires
			particles.add(new Particle(hitPoint, direction, 0.5f, 0.03f, entityModels.particle, random.nextFloat()));
		}

		//updating particles
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).tick(deltaTime, camera);
			if (particles.get(i).particleTime <= 0) {
				particles.remove(i);
			}
		}

	}

	private void updatePlayerAABB() {
		playerAABB = new AABB(new Vector3f(position.Xpos - 0.1f, position.Ypos + PLAYER_HEIGHT + 0.1f, position.Zpos - 0.1f), new Vector3f(position.Xpos + 0.1f, position.Ypos, position.Zpos + 0.1f), null);
	}

	//checking whether the grid position is within the uniform grid
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
