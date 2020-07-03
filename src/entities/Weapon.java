package entities;

import org.lwjgl.opengl.GL11;

import graphics.Renderer;
import input.Controls;
import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;
import model.Model;

public class Weapon extends Entity {

	private Vector3f weaponPosWRTCamera;

	public boolean fire = false;
	public float fireRateTimer;
	private float fireTimer = 0.0f;
	private float zOffset = 0.0f;
	private Entity muzzleFlashEntity;
	public float damage;

	private float ft = 0.0f;

	public Weapon(Vector3f position, Vector3f rotation, float scale, Model gunModel, Model muzzleFlash, float damage, float fireRateTimer) {
		super(position, rotation, scale, gunModel, null);
		this.fireRateTimer = fireRateTimer;
		fireTimer = fireRateTimer;
		this.damage = damage;
		weaponPosWRTCamera = new Vector3f(0.2f, -0.1f, -0.3f);
		muzzleFlashEntity = new Entity(new Vector3f(), new Vector3f(), 0.15f, muzzleFlash, null);
	}

	public void tick(float deltaTime, Camera camera, Controls controls) {
		// moving weapon back and forth and firing
		/*fireTimer -= deltaTime;
		if (controls.leftClick == true) {
			if (fireTimer <= 0) {
				fire = true;
				zOffset += deltaTime * 2;
				if (zOffset >= 0.2f) {
					zOffset = 0.2f;
				}
				fireTimer = fireRateTimer;
			} else {
				fire = false;
				zOffset -= deltaTime;
				if (zOffset <= 0.0f) {
					zOffset = 0.0f;
				}
			}
		} else {
			fire = false;
			zOffset = 0.0f;
		}

		if (fireTimer <= 0) {
			fireTimer = 0;
		}
*/
		fireTimer += deltaTime;
		if (controls.leftClick && ft <= fireTimer) {
			fire = true;
			ft = fireTimer + 0.5f;
		} else {
			fire = false;
		}

		// orienting weapon with respect to camera
		orientation(camera);
	}

	public void render(Renderer renderer) {
		if (fire == true) {
			renderer.renderMuzzleFlash(muzzleFlashEntity);
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		renderer.render(this);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	// orienting weapon with respect to camera
	private void orientation(Camera camera) {
		Matrix4f rotateX = Matrix4f.rotateX(-camera.pitch);
		Matrix4f rotateY = Matrix4f.rotateY(-camera.yaw);
		Vector4f orientWeaponPosWRTCamera = new Vector4f(weaponPosWRTCamera.Xpos, weaponPosWRTCamera.Ypos, weaponPosWRTCamera.Zpos + zOffset, 1.0f).multiply(rotateX).multiply(rotateY);
		position = new Vector3f(camera.position.Xpos + orientWeaponPosWRTCamera.Xpos, camera.position.Ypos + orientWeaponPosWRTCamera.Ypos, camera.position.Zpos + orientWeaponPosWRTCamera.Zpos);
		rotation.Ypos = camera.yaw - 90.0f;
		rotation.Zpos = -camera.pitch;
		Vector4f orientMuzzleFlashPosWRTCamera = new Vector4f(weaponPosWRTCamera.Xpos, weaponPosWRTCamera.Ypos + 0.025f, weaponPosWRTCamera.Zpos - 0.1f, 1.0f).multiply(rotateX).multiply(rotateY);
		muzzleFlashEntity.position = new Vector3f(camera.position.Xpos + orientMuzzleFlashPosWRTCamera.Xpos, camera.position.Ypos + orientMuzzleFlashPosWRTCamera.Ypos, camera.position.Zpos + orientMuzzleFlashPosWRTCamera.Zpos);
		muzzleFlashEntity.rotation.Ypos = camera.yaw;
		muzzleFlashEntity.rotation.Xpos = camera.pitch;
	}
}
