package entities;

import math.Vector3f;

public class PointLight {
	public Vector3f position;
	public Vector3f ambient;
	public Vector3f diffuse;
	public Vector3f specular;
	public float constantValue;
	public float linearValue;
	public float quadraticValue;

	public PointLight(Vector3f position, Vector3f ambient, Vector3f diffuse, Vector3f specular, float constantValue, float linearValue, float quadraticValue) {
		this.position = position;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.constantValue = constantValue;
		this.linearValue = linearValue;
		this.quadraticValue = quadraticValue;
	}

}
