package entities;

import math.Vector3f;

public class SpotLight {
	public Vector3f position;
	public Vector3f direction;
	public Vector3f ambient;
	public Vector3f diffuse;
	public Vector3f specular;
	public float cutOff;
	public float outerCutOff;
	public float constantValue;
	public float linearValue;
	public float quadraticValue;

	public SpotLight(Vector3f position, Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular, float cutOff, float outerCutOff, float constantValue, float linearValue, float quadraticValue) {
		this.position = position;
		this.direction = direction;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.cutOff = cutOff;
		this.outerCutOff = outerCutOff;
		this.constantValue = constantValue;
		this.linearValue = linearValue;
		this.quadraticValue = quadraticValue;
	}

}
