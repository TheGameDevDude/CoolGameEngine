package levels;

import model.EntityModels;

import java.util.ArrayList;
import java.util.List;

import ai.Grid;
import ai.PathFinding;
import entities.Camera;
import entities.Entity;
import entities.EntityManager;
import entities.MobManager;
import entities.Player;
import entities.PointLight;
import entities.SpotLight;
import graphics.Renderer;
import math.Vector3f;

public class Level0 {

	//lighting
	private List<PointLight> pointLights = new ArrayList<PointLight>();
	private List<SpotLight> spotLights = new ArrayList<SpotLight>();

	//placing all entities
	private List<Entity> wallEntities = new ArrayList<Entity>();
	private List<Entity> glassEntities = new ArrayList<Entity>();
	private List<Entity> floorEntities = new ArrayList<Entity>();
	private List<Entity> ceilEntities = new ArrayList<Entity>();
	private List<Entity> tablesEntities = new ArrayList<Entity>();
	private List<Entity> monitorCodeEntities = new ArrayList<Entity>();
	private List<Entity> monitorDesktopEntities = new ArrayList<Entity>();
	private List<Entity> keyboardEntities = new ArrayList<Entity>();
	private List<Entity> mouseEntities = new ArrayList<Entity>();
	private List<Entity> cpuEntities = new ArrayList<Entity>();

	//handles mobs
	private MobManager mobManager;
	private Grid grid;
	private PathFinding pathFinding;

	public Level0(EntityModels entityModels, EntityManager entityManager) {
		createPointLights();
		createSpotLights();
		//gets the AABB collsion box for each entity and place them in a grid or a chuck (using special grid algorithm)
		createWallEntities(entityModels, entityManager);
		createGlassEntities(entityModels, entityManager);
		createFloorEntities(entityModels, entityManager);
		createCeilingEntities(entityModels, entityManager);
		createTableEntities(entityModels, entityManager);
		createMonitorCode(entityModels, entityManager);
		createMonitorDesktopEntities(entityModels, entityManager);
		createKeyboardEntities(entityModels, entityManager);
		createMouseEntities(entityModels, entityManager);
		createCPUEntities(entityModels, entityManager);
		entityManager.placeAllEntitiesInChunks();

		mobManager = new MobManager(entityModels);
		//creating a new grid for A* path finding for obstacles 
		grid = new Grid(0.08f,400, 400, entityManager);
		pathFinding = new PathFinding(grid);
	}

	private void createPointLights() {
		pointLights.add(new PointLight(new Vector3f(7.0f, 0.0f, 8.0f), new Vector3f(0.2f, 0.2f, 0.2f), new Vector3f(1.0f, 0.5f, 0.3f), new Vector3f(1.0f, 0.5f, 0.3f), 1.5f, 0.1f, 0.09f));
	}

	private void createSpotLights() {
		spotLights.add(new SpotLight(new Vector3f(7.0f, 0.3f, 7.0f), new Vector3f(0.0f, -1.0f, 0.0f), new Vector3f(0.2f, 0.2f, 0.2f), new Vector3f(0.1f, 0.1f, 0.1f), new Vector3f(0.1f, 0.1f, 0.1f), (float) Math.cos(Math.toRadians(12.5f)), (float) Math.cos(Math.toRadians(17.5f)), 0.5f, 0.09f, 0.032f));
	}

	private void createWallEntities(EntityModels entityModels, EntityManager entityManager) {
		wallEntities.add(new Entity(new Vector3f(1, 0, 0), new Vector3f(0, 0, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		wallEntities.add(new Entity(new Vector3f(1.5f, 0, 0.5f), new Vector3f(0, -90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(1.5f, 0, 1.5f), new Vector3f(0, -90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(1.5f, 0, 2.5f), new Vector3f(0, -90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(0.5f, 0, 0.5f), new Vector3f(0, 90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(0.5f, 0, 1.5f), new Vector3f(0, 90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(0.5f, 0, 2.5f), new Vector3f(0, 90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(0.5f, 0, 3.5f), new Vector3f(0, 90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(2.0f, 0, 3.0f), new Vector3f(0, 0, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		wallEntities.add(new Entity(new Vector3f(3.0f, 0, 3.0f), new Vector3f(0, 0, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		wallEntities.add(new Entity(new Vector3f(4.0f, 0, 3.0f), new Vector3f(0, 0, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		wallEntities.add(new Entity(new Vector3f(4.5f, 0, 3.5f), new Vector3f(0, -90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(4.5f, 0, 4.5f), new Vector3f(0, -90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		wallEntities.add(new Entity(new Vector3f(4.0f, 0, 11.0f), new Vector3f(0, 180, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		for (float x = 1.0f; x <= 3; x++) {
			wallEntities.add(new Entity(new Vector3f(x, 0, 4.0f), new Vector3f(0, 180, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		}
		for (float x = 5.0f; x <= 10.0f; x++) {
			wallEntities.add(new Entity(new Vector3f(x, 0, 5.0f), new Vector3f(0, 0, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
			wallEntities.add(new Entity(new Vector3f(x, 0, 11.0f), new Vector3f(0, 180, 0), 1.0f, entityModels.brickWall, entityModels.wall_horAABB));
		}
		for (float z = 4.5f; z <= 11.0f; z++) {
			wallEntities.add(new Entity(new Vector3f(3.5f, 0, z), new Vector3f(0, 90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		}
		for (float z = 5.5f; z <= 11.0f; z++) {
			wallEntities.add(new Entity(new Vector3f(10.5f, 0, z), new Vector3f(0, -90, 0), 1.0f, entityModels.brickWall, entityModels.wall_verAABB));
		}
		entityManager.getAABBFromEntities(wallEntities);
	}

	private void createGlassEntities(EntityModels entityModels, EntityManager entityManager) {
		for (float x = 6.0f; x <= 10.0f; x++) {
			glassEntities.add(new Entity(new Vector3f(x, 0.0f, 9.0f), new Vector3f(), 1.0f, entityModels.glass, entityModels.wall_horAABB));
		}

		entityManager.getAABBFromEntities(glassEntities);
	}

	private void createFloorEntities(EntityModels entityModels, EntityManager entityManager) {
		floorEntities.add(new Entity(new Vector3f(1, -0.5f, 0.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(1, -0.5f, 1.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(1, -0.5f, 2.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(1, -0.5f, 3.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(2.0f, -0.5f, 3.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(3.0f, -0.5f, 3.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(4.0f, -0.5f, 3.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		floorEntities.add(new Entity(new Vector3f(4.0f, -0.5f, 4.5f), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
		for (float x = 4.0f; x <= 10.0f; x++) {
			for (float z = 5.5f; z <= 10.5f; z++) {
				floorEntities.add(new Entity(new Vector3f(x, -0.5f, z), new Vector3f(0, 0, 0), 1.0f, entityModels.floor, entityModels.floorAABB));
			}
		}

		entityManager.getAABBFromEntities(floorEntities);
	}

	private void createCeilingEntities(EntityModels entityModels, EntityManager entityManager) {
		ceilEntities.add(new Entity(new Vector3f(1, 0.5f, 0.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(1, 0.5f, 1.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(1, 0.5f, 2.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(1, 0.5f, 3.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(2.0f, 0.5f, 3.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(3.0f, 0.5f, 3.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(4.0f, 0.5f, 3.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		ceilEntities.add(new Entity(new Vector3f(4.0f, 0.5f, 4.5f), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
		for (float x = 4.0f; x <= 10.0f; x++) {
			for (float z = 5.5f; z <= 10.5f; z++) {
				ceilEntities.add(new Entity(new Vector3f(x, 0.5f, z), new Vector3f(0, 0, 180), 1.0f, entityModels.ceiling, entityModels.ceilAABB));
			}
		}
		entityManager.getAABBFromEntities(ceilEntities);
	}

	private void createTableEntities(EntityModels entityModels, EntityManager entityManager) {
		tablesEntities.add(new Entity(new Vector3f(8, -0.25f, 10.0f), new Vector3f(0, 0, 0), 1.0f, entityModels.table, entityModels.tableAABB));
		entityManager.getAABBFromEntities(tablesEntities);
	}

	private void createMonitorCode(EntityModels entityModels, EntityManager entityManager) {
		monitorCodeEntities.add(new Entity(new Vector3f(7.6f, 0.0f, 9.898f), new Vector3f(0, 0, 0), 1.0f, entityModels.monitorCode, entityModels.monitorAABB));
		entityManager.getAABBFromEntities(monitorCodeEntities);
	}

	private void createMonitorDesktopEntities(EntityModels entityModels, EntityManager entityManager) {
		monitorDesktopEntities.add(new Entity(new Vector3f(8.2f, 0.0f, 9.898f), new Vector3f(0, 0, 0), 1.0f, entityModels.monitorDesktop, entityModels.monitorAABB));
		entityManager.getAABBFromEntities(monitorDesktopEntities);
	}

	private void createKeyboardEntities(EntityModels entityModels, EntityManager entityManager) {
		keyboardEntities.add(new Entity(new Vector3f(7.9f, -0.245f, 10.07f), new Vector3f(), 1.0f, entityModels.keyboard, null));
	}

	private void createMouseEntities(EntityModels entityModels, EntityManager entityManager) {
		mouseEntities.add(new Entity(new Vector3f(8.15f, -0.245f, 10.07f), new Vector3f(), 1.0f, entityModels.mouse, null));
	}

	private void createCPUEntities(EntityModels entityModels, EntityManager entityManager) {
		cpuEntities.add(new Entity(new Vector3f(7.2f, -0.1f, 10.0f), new Vector3f(), 1.0f, entityModels.CPU, entityModels.cpuAABB));
		entityManager.getAABBFromEntities(cpuEntities);
	}

	public void tick(Camera camera, Player player, EntityModels entityModels, EntityManager entityManager, float deltaTime) {
		spotLights.get(0).position = camera.position;
		spotLights.get(0).direction = camera.direction;
		mobManager.tick(camera, player, entityModels, entityManager, pathFinding, deltaTime);
		player.weaponParticle(camera, deltaTime, entityModels);
	}

	public void render(Renderer renderer, Camera camera, Player player) {
		renderer.pointLightLighting(pointLights);
		renderer.spotLightLighting(spotLights);

		//render all entities
		renderer.render(wallEntities);
		renderer.render(glassEntities);
		renderer.render(floorEntities);
		renderer.render(ceilEntities);
		renderer.render(tablesEntities);
		renderer.render(monitorCodeEntities);
		renderer.render(monitorDesktopEntities);
		renderer.render(keyboardEntities);
		renderer.render(mouseEntities);
		renderer.render(cpuEntities);

		mobManager.render(renderer);

	}
}
