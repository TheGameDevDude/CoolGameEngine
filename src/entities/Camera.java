package entities;

import input.Controls;
import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;

public class Camera {
	public Vector3f position;
	public Vector3f direction;
	public float pitch;
	public float yaw;

	public static float MOUSE_SENSITIVTY = 0.07f;
	public static float SPEED = 2.0f;

	public Camera(Vector3f position, float pitch, float yaw) {
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public void tick(Controls controls, float deltaTime) {
		mouseControl(controls);
		keyboardControl(controls, deltaTime);
	}

	public void mouseControl(Controls controls) {
		yaw -= controls.mouseDX * MOUSE_SENSITIVTY;
		pitch += controls.mouseDY * MOUSE_SENSITIVTY;
		if (pitch > 90.0f) {
			pitch = 90.0f;
		} else if (pitch < -90.0f) {
			pitch = -90.0f;
		}

		Matrix4f rotX = Matrix4f.rotateX(-pitch);
		Matrix4f rotY = Matrix4f.rotateY(-yaw);
		Vector4f dir_result = new Vector4f(0.0f, 0.0f, -1.0f, 1.0f).multiply(rotX).multiply(rotY);
		direction = new Vector3f(dir_result.Xpos, dir_result.Ypos, dir_result.Zpos);
		direction.normalize();
	}

	public void keyboardControl(Controls controls, float deltaTime) {
		if (controls.forward == true) {
			position.Zpos -= SPEED * deltaTime * Math.cos(Math.toRadians(yaw));
			position.Xpos -= SPEED * deltaTime * Math.sin(Math.toRadians(yaw));
		} else if (controls.backward == true) {
			position.Zpos += SPEED * deltaTime * Math.cos(Math.toRadians(yaw));
			position.Xpos += SPEED * deltaTime * Math.sin(Math.toRadians(yaw));
		}

		if (controls.left == true) {
			position.Zpos += SPEED * deltaTime * Math.sin(Math.toRadians(yaw));
			position.Xpos -= SPEED * deltaTime * Math.cos(Math.toRadians(yaw));
		} else if (controls.right == true) {
			position.Zpos -= SPEED * deltaTime * Math.sin(Math.toRadians(yaw));
			position.Xpos += SPEED * deltaTime * Math.cos(Math.toRadians(yaw));
		}

		if (controls.flyUp == true) {
			position.Ypos += SPEED * deltaTime;
		} else if (controls.sneak == true) {
			position.Ypos -= SPEED * deltaTime;
		}
	}

	public Matrix4f getViewMatrix() {
		Matrix4f rotateX = Matrix4f.rotateX(-pitch);
		Matrix4f rotateY = Matrix4f.rotateY(-yaw);
		Vector3f oppPosition = new Vector3f(-position.Xpos, -position.Ypos, -position.Zpos);
		Matrix4f translate = Matrix4f.translate(oppPosition);
		return translate.multiply(rotateY).multiply(rotateX);
	}
}
