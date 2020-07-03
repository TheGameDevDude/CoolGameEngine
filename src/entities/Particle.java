package entities;

import math.Vector3f;
import model.Model;

public class Particle extends Entity {
	public Vector3f direction;
	public float speed;
	public float particleTime;

	public Particle(Vector3f position, Vector3f direction, float speed, float scale, Model model, float particleTime) {
		super(position, new Vector3f(), scale, model, null);
		this.direction = direction;
		this.speed = speed;
		this.particleTime = particleTime;
	}

	public void tick(float deltaTime, Camera camera) {
		rotation = new Vector3f(camera.pitch, camera.yaw, 0);
		position.Xpos += direction.Xpos * deltaTime * speed;
		position.Ypos += direction.Ypos * deltaTime * speed;
		position.Zpos += direction.Zpos * deltaTime * speed;
		particleTime -= deltaTime;
	}

}
