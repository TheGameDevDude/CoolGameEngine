package model;

import collision.AABB;
import graphics.Loader;
import math.Vector2f;
import math.Vector3f;

public class EntityModels {
	private int textureWidth;
	private int textureHeight;

	public Model wall;
	public Model brickWall;
	public Model glass;
	public Model floor;
	public Model ceiling;
	public Model table;
	public Model monitorCode;
	public Model monitorDesktop;
	public Model keyboard;
	public Model mouse;
	public Model CPU;
	public Model pistol;
	public Model zombie;
	public Model muzzleFlash;
	public Model particle;

	// 0 - floor
	// 1 - obstacle
	// 2 - ceiling
	int[] obstacleInfo = { 0, 1, 0 };
	int[] ceilingInfo = { 0, 0, 1 };
	int[] floorInfo = { 1, 0, 0 };

	public AABB wall_horAABB = new AABB(new Vector3f(-0.5f, 0.5f, -0.03f), new Vector3f(0.5f, -0.5f, 0.03f), obstacleInfo);
	public AABB wall_verAABB = new AABB(new Vector3f(-0.03f, 0.5f, -0.5f), new Vector3f(0.03f, -0.5f, 0.5f), obstacleInfo);
	public AABB floorAABB = new AABB(new Vector3f(-0.5f, 0, -0.5f), new Vector3f(0.5f, 0, 0.5f), floorInfo);
	public AABB ceilAABB = new AABB(new Vector3f(-0.5f, 0, -0.5f), new Vector3f(0.5f, 0, 0.5f), ceilingInfo);
	public AABB tableAABB = new AABB(new Vector3f(-0.5f * 2, 0, -0.5f * 0.4f), new Vector3f(0.5f * 2, -0.25f, 0.5f * 0.4f), obstacleInfo);
	public AABB monitorAABB = new AABB(new Vector3f(-0.5f * 2.0f * 0.3f, 0.5f * 0.3f, 0), new Vector3f(0.5f * 2.0f * 0.3f, -0.5f * 0.3f, 0), null);
	public AABB cpuAABB = new AABB(new Vector3f(-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f), new Vector3f(0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f), null);

	public EntityModels(Loader loader, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		createWall(loader);
		createBrickWall(loader);
		createGlass(loader);
		createFloor(loader);
		createCeiling(loader);
		createTable(loader);
		createMonitorCode(loader);
		createMonitorDesktop(loader);
		createKeyboard(loader);
		createMouse(loader);
		createCPU(loader);
		createPistol(loader);
		createZombie(loader);
		createMuzzleFlash(loader);
		createParticle(loader);
	}

	private void createWall(Loader loader) {
		Vector2f topL = new Vector2f((0 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((0 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((1 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((1 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		wall = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createBrickWall(Loader loader) {
		Vector2f topL = new Vector2f((1 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((1 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((2 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((2 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		brickWall = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createFloor(Loader loader) {
		Vector2f topL = new Vector2f((1 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((1 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((2 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((2 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f, -0.5f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		floor = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createCeiling(Loader loader) {
		Vector2f topL = new Vector2f((0 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((0 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((1 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((1 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f, -0.5f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		ceiling = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createTable(Loader loader) {
		Vector2f topL = new Vector2f((4 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((4 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((5 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((5 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		float[] positions = { -0.5f * 2, 0.0f, -0.5f * 0.4f, -0.5f * 2, 0.0f, 0.5f * 0.4f, 0.5f * 2, 0.0f, 0.5f * 0.4f, 0.5f * 2, 0.0f, -0.5f * 0.4f, -0.5f * 2, 0.0f, -0.5f * 0.4f, -0.5f * 2, -0.25f, -0.5f * 0.4f, -0.5f * 2, -0.25f, 0.5f * 0.4f, -0.5f * 2, 0.0f, 0.5f * 0.4f, 0.5f * 2, 0.0f, -0.5f * 0.4f, 0.5f * 2, 0.0f, 0.5f * 0.4f, 0.5f * 2, -0.25f, 0.5f * 0.4f, 0.5f * 2, -0.25f, -0.5f * 0.4f, -0.5f * 2, 0.0f, -0.5f * 0.4f, 0.5f * 2, 0.0f, -0.5f * 0.4f, 0.5f * 2, -0.25f, -0.5f * 0.4f, -0.5f * 2, -0.25f, -0.5f * 0.4f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos, topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos, topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos, topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14 };
		table = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createMonitorCode(Loader loader) {
		Vector2f topL = new Vector2f((2 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((2 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((4 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((4 * 16.0f) / textureWidth, (0 * 16.0f) / textureHeight);
		Vector2f topL1 = new Vector2f((4 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botL1 = new Vector2f((4 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botR1 = new Vector2f((5 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topR1 = new Vector2f((5 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		float[] positions = { -0.5f * 2.0f * 0.3f, 0.5f * 0.3f, 0.0f, -0.5f * 2.0f * 0.3f, -0.5f * 0.3f, 0.0f, 0.5f * 2.0f * 0.3f, -0.5f * 0.3f, 0.0f, 0.5f * 2.0f * 0.3f, 0.5f * 0.3f, 0.0f, -0.5f * 0.1f, (0.5f * 0.1f) - (0.2f), 0.0f, -0.5f * 0.1f, (-0.5f * 0.1f) - (0.2f), 0.0f, 0.5f * 0.1f, (-0.5f * 0.1f) - (0.2f), 0.0f, 0.5f * 0.1f, (0.5f * 0.1f) - (0.2f), 0.0f, -0.5f * 0.2f, -0.245f, -0.5f * 0.2f, -0.5f * 0.2f, -0.245f, 0.5f * 0.2f, 0.5f * 0.2f, -0.245f, 0.5f * 0.2f, 0.5f * 0.2f, -0.245f, -0.5f * 0.2f, -0.5f * 2.0f * 0.3f, -0.5f * 0.3f, -0.001f, 0.5f * 2.0f * 0.3f, -0.5f * 0.3f, -0.001f, 0.5f * 2.0f * 0.3f, 0.5f * 0.3f, -0.001f, -0.5f * 2.0f * 0.3f, 0.5f * 0.3f, -0.001f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos, topL1.Xpos, topL1.Ypos, botL1.Xpos, botL1.Ypos, botR1.Xpos, botR1.Ypos, topR1.Xpos, topR1.Ypos, topL1.Xpos, topL1.Ypos, botL1.Xpos, botL1.Ypos, botR1.Xpos, botR1.Ypos, topR1.Xpos, topR1.Ypos, topL1.Xpos, topL1.Ypos, botL1.Xpos, botL1.Ypos, botR1.Xpos, botR1.Ypos, topR1.Xpos, topR1.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, };
		int[] indices = { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14 };
		monitorCode = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createMonitorDesktop(Loader loader) {
		Vector2f topL = new Vector2f((2 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((2 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((4 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((4 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f topL1 = new Vector2f((4 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		Vector2f botL1 = new Vector2f((4 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botR1 = new Vector2f((5 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topR1 = new Vector2f((5 * 16.0f) / textureWidth, (1 * 16.0f) / textureHeight);
		float[] positions = { -0.5f * 2.0f * 0.3f, 0.5f * 0.3f, 0.0f, -0.5f * 2.0f * 0.3f, -0.5f * 0.3f, 0.0f, 0.5f * 2.0f * 0.3f, -0.5f * 0.3f, 0.0f, 0.5f * 2.0f * 0.3f, 0.5f * 0.3f, 0.0f, -0.5f * 0.1f, (0.5f * 0.1f) - (0.2f), 0.0f, -0.5f * 0.1f, (-0.5f * 0.1f) - (0.2f), 0.0f, 0.5f * 0.1f, (-0.5f * 0.1f) - (0.2f), 0.0f, 0.5f * 0.1f, (0.5f * 0.1f) - (0.2f), 0.0f, -0.5f * 0.2f, -0.245f, -0.5f * 0.2f, -0.5f * 0.2f, -0.245f, 0.5f * 0.2f, 0.5f * 0.2f, -0.245f, 0.5f * 0.2f, 0.5f * 0.2f, -0.245f, -0.5f * 0.2f, -0.5f * 2.0f * 0.3f, -0.5f * 0.3f, -0.001f, 0.5f * 2.0f * 0.3f, -0.5f * 0.3f, -0.001f, 0.5f * 2.0f * 0.3f, 0.5f * 0.3f, -0.001f, -0.5f * 2.0f * 0.3f, 0.5f * 0.3f, -0.001f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos, topL1.Xpos, topL1.Ypos, botL1.Xpos, botL1.Ypos, botR1.Xpos, botR1.Ypos, topR1.Xpos, topR1.Ypos, topL1.Xpos, topL1.Ypos, botL1.Xpos, botL1.Ypos, botR1.Xpos, botR1.Ypos, topR1.Xpos, topR1.Ypos, topL1.Xpos, topL1.Ypos, botL1.Xpos, botL1.Ypos, botR1.Xpos, botR1.Ypos, topR1.Xpos, topR1.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, };
		int[] indices = { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14 };
		monitorDesktop = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createKeyboard(Loader loader) {
		Vector2f topL = new Vector2f((2 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((2 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((4 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((4 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		float[] positions = { -0.5f * 2.0f * 0.2f, 0.0f, -0.5f * 0.2f, -0.5f * 2.0f * 0.2f, 0.0f, 0.5f * 0.2f, 0.5f * 2.0f * 0.2f, 0.0f, 0.5f * 0.2f, 0.5f * 2.0f * 0.2f, 0.0f, -0.5f * 0.2f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		keyboard = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createMouse(Loader loader) {
		Vector2f topL = new Vector2f((0 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((0 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((1 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((1 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		float[] positions = { -0.5f * 0.05f, 0.0f, -0.5f * 0.1f, -0.5f * 0.05f, 0.0f, 0.5f * 0.1f, 0.5f * 0.05f, 0.0f, 0.5f * 0.1f, 0.5f * 0.05f, 0.0f, -0.5f * 0.1f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		mouse = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createCPU(Loader loader) {

		Vector2f topLf = new Vector2f((1 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topRf = new Vector2f((2 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botRf = new Vector2f((2 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botLf = new Vector2f((1 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);

		Vector2f topLt = new Vector2f((0 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f topRt = new Vector2f((1 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botRt = new Vector2f((1 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botLt = new Vector2f((0 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);

		Vector2f topLb = new Vector2f((1 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f topRb = new Vector2f((2 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botRb = new Vector2f((2 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botLb = new Vector2f((1 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);

		Vector2f topLs = new Vector2f((4 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f topRs = new Vector2f((5 * 16.0f) / textureWidth, (2 * 16.0f) / textureHeight);
		Vector2f botRs = new Vector2f((5 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botLs = new Vector2f((4 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);

		float[] positions = {
				// front
				-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, 0.5f * 0.3f, // v0
				0.5f * 0.3f * 0.5f, 0.5f * 0.3f, 0.5f * 0.3f, // v1
				0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f, // v2
				-0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f, // v3

				// back
				-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f, // v4
				0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f, // v5
				0.5f * 0.3f * 0.5f, -0.5f * 0.3f, -0.5f * 0.3f, // v6
				-0.5f * 0.3f * 0.5f, -0.5f * 0.3f, -0.5f * 0.3f, // v7

				// right
				0.5f * 0.3f * 0.5f, 0.5f * 0.3f, 0.5f * 0.3f, // v8
				0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f, // v9
				0.5f * 0.3f * 0.5f, -0.5f * 0.3f, -0.5f * 0.3f, // v10
				0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f, // v11

				// left
				-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, 0.5f * 0.3f, // v12
				-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f, // v13
				-0.5f * 0.3f * 0.5f, -0.5f * 0.3f, -0.5f * 0.3f, // v14
				-0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f, // v15

				// top
				0.5f * 0.3f * 0.5f, 0.5f * 0.3f, 0.5f * 0.3f, // v16
				-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, 0.5f * 0.3f, // v17
				-0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f, // v18
				0.5f * 0.3f * 0.5f, 0.5f * 0.3f, -0.5f * 0.3f, // v19

				// bottom
				-0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f, // v20
				0.5f * 0.3f * 0.5f, -0.5f * 0.3f, 0.5f * 0.3f, // v21
				0.5f * 0.3f * 0.5f, -0.5f * 0.3f, -0.5f * 0.3f, // v22
				-0.5f * 0.3f * 0.5f, -0.5f * 0.3f, -0.5f * 0.3f// v23
		};

		float[] texCoords = {
				// front
				topLf.Xpos, topLf.Ypos, // t0
				topRf.Xpos, topRf.Ypos, // t1
				botRf.Xpos, botRf.Ypos, // t2
				botLf.Xpos, botLf.Ypos, // t3

				// back
				topLb.Xpos, topLb.Ypos, // t4
				topRb.Xpos, topRb.Ypos, // t5
				botRb.Xpos, botRb.Ypos, // t6
				botLb.Xpos, botLb.Ypos, // t7

				// right
				topLs.Xpos, topLs.Ypos, // t8
				topRs.Xpos, topRs.Ypos, // t9
				botRs.Xpos, botRs.Ypos, // t10
				botLs.Xpos, botLs.Ypos, // t11

				// left
				topLs.Xpos, topLs.Ypos, // t12
				topRs.Xpos, topRs.Ypos, // t13
				botRs.Xpos, botRs.Ypos, // t14
				botLs.Xpos, botLs.Ypos, // t15

				// top
				topLt.Xpos, topLt.Ypos, // t16
				topRt.Xpos, topRt.Ypos, // t17
				botRt.Xpos, botRt.Ypos, // t18
				botLt.Xpos, botLt.Ypos, // t19

				// bottom
				topLt.Xpos, topLt.Ypos, // t20
				topRt.Xpos, topRt.Ypos, // t21
				botRt.Xpos, botRt.Ypos, // t22
				botLt.Xpos, botLt.Ypos// t23
		};

		float[] normals = {
				// front
				0, 0, 1, // n0
				0, 0, 1, // n1
				0, 0, 1, // n2
				0, 0, 1, // n3

				// back
				0, 0, -1, // n4
				0, 0, -1, // n5
				0, 0, -1, // n6
				0, 0, -1, // n7

				// right
				1, 0, 0, // n8
				1, 0, 0, // n9
				1, 0, 0, // n10
				1, 0, 0, // n11

				// left
				-1, 0, 0, // n12
				-1, 0, 0, // n13
				-1, 0, 0, // n14
				-1, 0, 0, // n15

				// top
				0, 1, 0, // n16
				0, 1, 0, // n17
				0, 1, 0, // n18
				0, 1, 0, // n19

				// bottom
				0, -1, 0, // n20
				0, -1, 0, // n21
				0, -1, 0, // n22
				0, -1, 0// n23
		};

		int[] indices = {
				// front
				0, 3, 1, // i1
				1, 3, 2, // i2

				// back
				5, 6, 4, // i1
				4, 6, 7, // i2

				// right
				8, 11, 9, // i1
				9, 11, 10, // i2

				// left
				13, 14, 12, // i1
				12, 14, 15, // i2

				// top
				18, 17, 19, // i1
				19, 17, 16, // i2

				// bottom
				20, 23, 21, // i1
				21, 23, 22// i2
		};
		CPU = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createGlass(Loader loader) {
		Vector2f topL = new Vector2f((2 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((2 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((3 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((3 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		glass = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createPistol(Loader loader) {
		Vector2f topL = new Vector2f((3 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((3 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((4 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((4 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		pistol = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createZombie(Loader loader) {
		Vector2f topL = new Vector2f((4 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((4 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((5 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((5 * 16.0f) / textureWidth, (3 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		zombie = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createMuzzleFlash(Loader loader) {
		Vector2f topL = new Vector2f((0 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((0 * 16.0f) / textureWidth, (5 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((1 * 16.0f) / textureWidth, (5 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((1 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		muzzleFlash = loader.loadToVAO(positions, texCoords, normals, indices);
	}

	private void createParticle(Loader loader) {
		Vector2f topL = new Vector2f((1 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		Vector2f botL = new Vector2f((1 * 16.0f) / textureWidth, (5 * 16.0f) / textureHeight);
		Vector2f botR = new Vector2f((2 * 16.0f) / textureWidth, (5 * 16.0f) / textureHeight);
		Vector2f topR = new Vector2f((2 * 16.0f) / textureWidth, (4 * 16.0f) / textureHeight);
		float[] positions = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] texCoords = { topL.Xpos, topL.Ypos, botL.Xpos, botL.Ypos, botR.Xpos, botR.Ypos, topR.Xpos, topR.Ypos };
		float[] normals = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		particle = loader.loadToVAO(positions, texCoords, normals, indices);
	}
}
