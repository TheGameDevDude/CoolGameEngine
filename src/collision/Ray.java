package collision;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Zombie;
import math.Vector3f;

public class Ray {
	public Vector3f position;
	public Vector3f direction;

	public Ray(Vector3f position, Vector3f direction) {
		this.position = position;
		this.direction = direction;
	}

	//ray with AABB intersection
	public Vector3f getHitPoint(List<Entity> entities) {
		float t = 0;
		List<Vector3f> hitPoints = new ArrayList<Vector3f>();
		for (Entity entity : entities) {
			List<Vector3f> frontPoints = new ArrayList<Vector3f>();
			// front
			t = (entity.aabb.nearRight.Zpos - position.Zpos) / direction.Zpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointZfront = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Zpos < 0) {
				frontPoints.add(pointZfront);
			}

			// back
			t = (entity.aabb.farLeft.Zpos - position.Zpos) / direction.Zpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointZBack = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Zpos > 0) {
				frontPoints.add(pointZBack);
			}

			// left
			t = (entity.aabb.farLeft.Xpos - position.Xpos) / direction.Xpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointXLeft = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Xpos > 0) {
				frontPoints.add(pointXLeft);
			}

			// right
			t = (entity.aabb.nearRight.Xpos - position.Xpos) / direction.Xpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointXRight = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Xpos < 0) {
				frontPoints.add(pointXRight);
			}

			// up
			t = (entity.aabb.farLeft.Ypos - position.Ypos) / direction.Ypos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointYUp = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Ypos < 0) {
				frontPoints.add(pointYUp);
			}

			// down
			t = (entity.aabb.nearRight.Ypos - position.Ypos) / direction.Ypos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointYDown = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Ypos > 0) {
				frontPoints.add(pointYDown);
			}

			for (Vector3f hitPoint : frontPoints) {
				if (hitPoint.Xpos >= entity.aabb.farLeft.Xpos && hitPoint.Xpos <= entity.aabb.nearRight.Xpos && hitPoint.Ypos >= entity.aabb.nearRight.Ypos && hitPoint.Ypos <= entity.aabb.farLeft.Ypos && hitPoint.Zpos >= entity.aabb.farLeft.Zpos && hitPoint.Zpos <= entity.aabb.nearRight.Zpos) {
					hitPoints.add(hitPoint);
				}
			}
		}

		Vector3f result = new Vector3f();
		if (hitPoints.size() != 0) {
			float min = 100000.0f;
			for (Vector3f minHitPoint : hitPoints) {
				float dist = new Vector3f(minHitPoint.Xpos - position.Xpos, minHitPoint.Ypos - position.Ypos, minHitPoint.Zpos - position.Zpos).getMagnitude();
				if (dist < min) {
					min = dist;
					result = minHitPoint;
				}
			}
		} else {
			result = null;
		}
		return result;
	}

	//ray with zombie intersection
	public Vector3f getHitPointZombie(List<Zombie> zombies) {
		float t = 0;
		List<Vector3f> hitPoints = new ArrayList<Vector3f>();
		for (Zombie zombie : zombies) {
			List<Vector3f> frontPoints = new ArrayList<Vector3f>();
			// front
			t = (zombie.aabb.nearRight.Zpos - position.Zpos) / direction.Zpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointZfront = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Zpos < 0) {
				frontPoints.add(pointZfront);
			}

			// back
			t = (zombie.aabb.farLeft.Zpos - position.Zpos) / direction.Zpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointZBack = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Zpos > 0) {
				frontPoints.add(pointZBack);
			}

			// left
			t = (zombie.aabb.farLeft.Xpos - position.Xpos) / direction.Xpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointXLeft = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Xpos > 0) {
				frontPoints.add(pointXLeft);
			}

			// right
			t = (zombie.aabb.nearRight.Xpos - position.Xpos) / direction.Xpos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointXRight = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Xpos < 0) {
				frontPoints.add(pointXRight);
			}

			// up
			t = (zombie.aabb.farLeft.Ypos - position.Ypos) / direction.Ypos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointYUp = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Ypos < 0) {
				frontPoints.add(pointYUp);
			}

			// down
			t = (zombie.aabb.nearRight.Ypos - position.Ypos) / direction.Ypos;
			if (t < 0) {
				t = -t;
			}
			Vector3f pointYDown = new Vector3f(position.Xpos + direction.Xpos * t, position.Ypos + direction.Ypos * t, position.Zpos + direction.Zpos * t);
			if (direction.Ypos > 0) {
				frontPoints.add(pointYDown);
			}

			for (Vector3f hitPoint : frontPoints) {
				if (hitPoint.Xpos >= zombie.aabb.farLeft.Xpos && hitPoint.Xpos <= zombie.aabb.nearRight.Xpos && hitPoint.Ypos >= zombie.aabb.nearRight.Ypos && hitPoint.Ypos <= zombie.aabb.farLeft.Ypos && hitPoint.Zpos >= zombie.aabb.farLeft.Zpos && hitPoint.Zpos <= zombie.aabb.nearRight.Zpos) {
					hitPoints.add(hitPoint);
				}
			}
		}

		Vector3f result = new Vector3f();
		if (hitPoints.size() != 0) {
			float min = 100000.0f;
			for (Vector3f minHitPoint : hitPoints) {
				float dist = new Vector3f(minHitPoint.Xpos - position.Xpos, minHitPoint.Ypos - position.Ypos, minHitPoint.Zpos - position.Zpos).getMagnitude();
				if (dist < min) {
					min = dist;
					result = minHitPoint;
				}
			}
		} else {
			result = null;
		}
		return result;
	}
}
