package entities;

import collision.AABB;
import math.Matrix4f;
import math.Vector3f;
import model.Model;

public class Entity {
	public Vector3f position;
	public Vector3f rotation;
	public float scale;
	private Model model;
	//collision box for every entity
	public AABB aabb;

	public Entity(Vector3f position, Vector3f rotation, float scale, Model model, AABB aabb) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.model = model;
		if (aabb == null) {
			this.aabb = null;
		} else {
			Vector3f farLeft = new Vector3f((aabb.farLeft.Xpos * scale) + position.Xpos, (aabb.farLeft.Ypos * scale) + position.Ypos, (aabb.farLeft.Zpos * scale) + position.Zpos);
			Vector3f nearRight = new Vector3f((aabb.nearRight.Xpos * scale) + position.Xpos, (aabb.nearRight.Ypos * scale) + position.Ypos, (aabb.nearRight.Zpos * scale) + position.Zpos);
			this.aabb = new AABB(farLeft, nearRight, aabb.info);
		}
	}

	public Matrix4f calculateModelMatrixZYX() {
		Matrix4f scaleMatrix = Matrix4f.scale(scale);
		Matrix4f rotX = Matrix4f.rotateX(rotation.Xpos);
		Matrix4f rotY = Matrix4f.rotateY(rotation.Ypos);
		Matrix4f rotZ = Matrix4f.rotateZ(rotation.Zpos);
		Matrix4f translation = Matrix4f.translate(position);
		return scaleMatrix.multiply(rotZ).multiply(rotY).multiply(rotX).multiply(translation);
	}

	public Matrix4f calculateModelMatrixXYZ() {
		Matrix4f scaleMatrix = Matrix4f.scale(scale);
		Matrix4f rotX = Matrix4f.rotateX(rotation.Xpos);
		Matrix4f rotY = Matrix4f.rotateY(rotation.Ypos);
		Matrix4f rotZ = Matrix4f.rotateZ(rotation.Zpos);
		Matrix4f translation = Matrix4f.translate(position);
		return scaleMatrix.multiply(rotX).multiply(rotY).multiply(rotZ).multiply(translation);
	}

	public Model getModel() {
		return model;
	}
}
