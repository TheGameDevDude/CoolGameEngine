package maingameloop;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import entities.Camera;
import entities.EntityManager;
import entities.Player;
import graphics.Loader;
import graphics.Renderer;
import input.Controls;
import levels.Level0;
import math.Matrix4f;
import math.Vector3f;
import model.EntityModels;

public class Main {
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	private long window;
	private GLFWVidMode vid;
	// load models
	private Loader loader = new Loader();
	// rendering models
	private Renderer renderer;
	private Camera camera;
	private Controls controls;
	private Player player;
	// contains all the models
	private EntityModels entityModels;
	// entityManager handles collision for different entities
	private List<EntityManager> entityManagers = new ArrayList<EntityManager>();
	private EntityManager entityManager;
	private Level0 level0;

	public Main() {
		initiateOpenGLWindow();
		renderer = new Renderer(loader);
		entityModels = new EntityModels(loader, renderer.textureWidth, renderer.textureHeight);
		controls = new Controls(window);
		camera = new Camera(new Vector3f(0.0f, 0.0f, 2.0f), 0.0f, 0.0f);

		// each level will have different entityManager because each level has different
		// collision box
		entityManagers.add(new EntityManager(4, 11, 11));
		entityManager = entityManagers.get(0);
		level0 = new Level0(entityModels, entityManager);

		player = new Player(new Vector3f(8.0f, -0.4f, 10.5f), entityModels);

		double prevTime = GLFW.glfwGetTime();
		while (!GLFW.glfwWindowShouldClose(window)) {
			double currentTime = GLFW.glfwGetTime();
			float deltaTime = (float) (currentTime - prevTime);
			prevTime = currentTime;

			// update game
			tick(deltaTime);
			// render game
			render();
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
		renderer.stop();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}

	private void tick(float deltaTime) {
		controls.tick();
		renderer.getShader().start();
		// update player
		player.tick(camera, controls, entityManager, entityModels, deltaTime);
		// update level
		level0.tick(camera, player, entityModels, entityManager, deltaTime);
		renderer.getShader().setMatrix4f("projection", Matrix4f.perspectiveProjection(70.0f, (float) WIDTH / (float) HEIGHT, 0.05f, 100.0f));
		// update camera in the shader
		renderer.getShader().setMatrix4f("view", camera.getViewMatrix());
		renderer.getShader().stop();
	}

	private void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glViewport(0, 0, vid.width(), vid.height());
		level0.render(renderer, camera, player);
		player.render(renderer);
	}

	private void initiateOpenGLWindow() {
		if (!GLFW.glfwInit()) {
			System.out.println("GLFW not initialized");
		}
		vid = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, vid.redBits());
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, vid.greenBits());
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, vid.blueBits());
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, vid.refreshRate());
		window = GLFW.glfwCreateWindow(vid.width(), vid.height(), "hello", GLFW.glfwGetPrimaryMonitor(), 0);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
		if (window == 0) {
			System.out.println("Failed to create window!");
		}
		GLFW.glfwShowWindow(window);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		GL.createCapabilities();
		GL11.glClearColor(0, 0, 1, 1);
	}

	public static void main(String[] args) {
		new Main();
	}
}
