package math;

public class Vector3f {
	public float Xpos;
	public float Ypos;
	public float Zpos;

	public Vector3f() {
		Xpos = 0.0f;
		Ypos = 0.0f;
		Zpos = 0.0f;
	}

	public Vector3f(float Xpos, float Ypos, float Zpos) {
		this.Xpos = Xpos;
		this.Ypos = Ypos;
		this.Zpos = Zpos;
	}

	public Vector3f cross(Vector3f a, Vector3f b) {
		Xpos = a.Ypos * b.Zpos - a.Zpos * b.Ypos;
		Ypos = a.Zpos * b.Xpos - a.Xpos * b.Zpos;
		Zpos = a.Xpos * b.Ypos - a.Ypos * b.Xpos;
		return this;
	}

	public void normalize() {
		float magnitude = getMagnitude();
		Xpos = Xpos / magnitude;
		Ypos = Ypos / magnitude;
		Zpos = Zpos / magnitude;
	}

	public void scale(float value) {
		Xpos *= value;
		Ypos *= value;
		Zpos *= value;
	}

	public float getMagnitude() {
		return (float) Math.sqrt(Xpos * Xpos + Ypos * Ypos + Zpos * Zpos);
	}
}
