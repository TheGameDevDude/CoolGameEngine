package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ai.PathFinding;
import collision.Ray;
import graphics.Renderer;
import math.Vector3f;
import model.EntityModels;

public class MobManager {
	public List<Zombie> zombies = new ArrayList<Zombie>();

	public MobManager(EntityModels entityModels) {
		zombies.add(new Zombie(new Vector3f(9.0f, -0.25f, 10.5f), new Vector3f(), 0.5f, entityModels.zombie, null));
	}

	public void tick(Camera camera, Player player, EntityModels entityModels, EntityManager entityManager, PathFinding pathFinding, float deltaTime) {
		for (Zombie zombie : zombies) {
			zombie.tick(player, camera, pathFinding, entityManager, deltaTime);
		}

		dealDamageToZombie(camera, player, deltaTime);

	}

	private void dealDamageToZombie(Camera camera, Player player, float deltaTime) {
		// there are two hit points one for static entities and other for zombies
		// if zombie is behind a static entity then the zombie wont deal damage
		if (zombies.size() != 0) {
			Vector3f hitPoint = new Ray(camera.position, camera.direction).getHitPointZombie(zombies);
			if (hitPoint != null && player.hitPoint != null) {
				float distanceStaticEntity = new Vector3f(camera.position.Xpos - player.hitPoint.Xpos, camera.position.Ypos - player.hitPoint.Ypos, camera.position.Zpos - player.hitPoint.Zpos).getMagnitude();
				float distanceZombieEntity = new Vector3f(camera.position.Xpos - hitPoint.Xpos, camera.position.Ypos - hitPoint.Ypos, camera.position.Zpos - hitPoint.Zpos).getMagnitude();
				if (distanceZombieEntity < distanceStaticEntity) {
					player.hitPoint = hitPoint;
				}
			}
		}

		// update zombie health and delete zombie 0 health
		if (zombies.size() != 0) {
			Iterator<Zombie> iterator = zombies.iterator();
			while (iterator.hasNext()) {
				Zombie z = iterator.next();
				z.dealDamage(player, deltaTime);
				if (z.health <= 0) {
					iterator.remove();
				}
			}
		}
	}

	public void render(Renderer renderer) {
		if (zombies.size() != 0) {
			renderer.renderZombies(zombies);
		}
	}

}
