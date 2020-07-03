package collision;

import math.Vector3f;

public class AABB {
	public Vector3f farLeft;
	public Vector3f nearRight;
	public float xLength;
	public float yHeight;
	public float zWidth;
	//info for whether its an obstacle or a ceiling or floor for path finding 
	public int[] info;

	public AABB(Vector3f farLeft, Vector3f nearRight, int[] info) {
		float tlXpos, tlYpos, tlZpos;
		float brXpos, brYpos, brZpos;
		if (farLeft.Xpos < nearRight.Xpos) {
			tlXpos = farLeft.Xpos;
			brXpos = nearRight.Xpos;
		} else {
			brXpos = farLeft.Xpos;
			tlXpos = nearRight.Xpos;
		}

		if (farLeft.Ypos > nearRight.Ypos) {
			tlYpos = farLeft.Ypos;
			brYpos = nearRight.Ypos;
		} else {
			brYpos = farLeft.Ypos;
			tlYpos = nearRight.Ypos;
		}

		if (farLeft.Zpos < nearRight.Zpos) {
			tlZpos = farLeft.Zpos;
			brZpos = nearRight.Zpos;
		} else {
			brZpos = farLeft.Zpos;
			tlZpos = nearRight.Zpos;
		}
		this.farLeft = new Vector3f(tlXpos, tlYpos, tlZpos);
		this.nearRight = new Vector3f(brXpos, brYpos, brZpos);
		xLength = Math.abs((nearRight.Xpos - farLeft.Xpos) / 2);
		yHeight = Math.abs((nearRight.Ypos - farLeft.Ypos) / 2);
		zWidth = Math.abs((nearRight.Zpos - farLeft.Zpos) / 2);
		this.info = info;
	}

	//check collision between two AABB
	public boolean check(AABB aabb) {
		Vector3f thisAABB = getCenter();
		Vector3f otherAABB = aabb.getCenter();
		float xDist = Math.abs(thisAABB.Xpos - otherAABB.Xpos);
		float yDist = Math.abs(thisAABB.Ypos - otherAABB.Ypos);
		float zDist = Math.abs(thisAABB.Zpos - otherAABB.Zpos);
		if (checkX(xDist, aabb) && checkY(yDist, aabb) && checkZ(zDist, aabb)) {
			return true;
		}
		return false;
	}

	private boolean checkX(float xDist, AABB aabb) {
		if (xDist < (xLength + aabb.xLength)) {
			return true;
		}
		return false;
	}

	private boolean checkY(float yDist, AABB aabb) {
		if (yDist < (yHeight + aabb.yHeight)) {
			return true;
		}
		return false;
	}

	private boolean checkZ(float zDist, AABB aabb) {
		if (zDist < (zWidth + aabb.zWidth)) {
			return true;
		}
		return false;
	}

	//get center of AABB
	public Vector3f getCenter() {
		Vector3f result = new Vector3f();
		result.Xpos = farLeft.Xpos + (nearRight.Xpos - farLeft.Xpos) / 2;
		result.Ypos = farLeft.Ypos + (nearRight.Ypos - farLeft.Ypos) / 2;
		result.Zpos = farLeft.Zpos + (nearRight.Zpos - farLeft.Zpos) / 2;
		return result;
	}
}
