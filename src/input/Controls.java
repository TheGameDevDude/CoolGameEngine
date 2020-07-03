package input;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Controls {
	public boolean forward;
	public boolean backward;
	public boolean left;
	public boolean right;
	public boolean flyUp;
	public boolean sneak;
	public boolean jump;

	public boolean leftClick;

	public double mouseX;
	public double mouseY;
	public double mouseDX;
	public double mouseDY;

	public int windowWidth;
	public int windowHeight;

	private long window;

	public Controls(long window) {
		this.window = window;
		forward = false;
		backward = false;
		left = false;
		right = false;
		flyUp = false;
		sneak = false;
		jump = false;
		leftClick = false;
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(window, w, h);
		windowWidth = w.get(0);
		windowHeight = h.get(0);
		GLFW.glfwSetCursorPos(window, windowWidth / 2, windowHeight / 2);

	}

	public void tick() {
		mouse();
		keyboard();
	}

	private void mouse() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(window, w, h);
		windowWidth = w.get(0);
		windowHeight = h.get(0);
		DoubleBuffer mx = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer my = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, mx, my);
		mouseX = mx.get(0);
		mouseY = my.get(0);
		mouseDX = mouseX - windowWidth / 2;
		mouseDY = -(mouseY - windowHeight / 2);
		GLFW.glfwSetCursorPos(window, windowWidth / 2, windowHeight / 2);

		int state = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT);
		if (state == GLFW.GLFW_PRESS) {
			leftClick = true;
		} else {
			leftClick = false;
		}

	}

	private void keyboard() {
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
			forward = true;
			backward = false;
		} else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
			forward = false;
			backward = true;
		} else {
			forward = false;
			backward = false;
		}

		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
			left = true;
			right = false;
		} else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
			right = true;
			left = false;
		} else {
			right = false;
			left = false;
		}

		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
			jump = true;
		} else {
			jump = false;
		}

		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) {
			sneak = true;
		} else {
			sneak = false;
		}
	}
}
