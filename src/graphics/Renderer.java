package graphics;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

import entities.Entity;
import entities.Particle;
import entities.PointLight;
import entities.SpotLight;
import entities.Zombie;
import shaders.ShaderProgram;

public class Renderer {
	private ShaderProgram shader;
	private int diffuseTexture;
	private int specularTexture;
	private int emissiveTexture;
	public int textureWidth;
	public int textureHeight;

	public Renderer(Loader loader) {
		shader = new ShaderProgram("src/shaders/VertexShader.txt", "src/shaders/FragmentShader.txt");
		shader.start();
		diffuseTexture = loader.loadTexture("res/textures/spritesheet_diff.png");
		specularTexture = loader.loadTexture("res/textures/spritesheet_spec.png");
		emissiveTexture = loader.loadTexture("res/textures/spritesheet_em.png");
		textureWidth = loader.textureWidth;
		textureHeight = loader.textureHeight;
		shader.setFloat("material.shininess", 10.0f);
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
		GL30.glBindTexture(GL11.GL_TEXTURE_2D, diffuseTexture);
		shader.setInt("material.diffuse", 0);
		GL30.glActiveTexture(GL30.GL_TEXTURE1);
		GL30.glBindTexture(GL11.GL_TEXTURE_2D, specularTexture);
		shader.setInt("material.specular", 1);
		GL30.glActiveTexture(GL30.GL_TEXTURE2);
		GL30.glBindTexture(GL11.GL_TEXTURE_2D, emissiveTexture);
		shader.setInt("material.emission", 2);
		shader.stop();
	}

	public void pointLightLighting(List<PointLight> pointLights) {
		shader.start();
		shader.setInt("numberOfLights.numberOfPointLights", pointLights.size());

		for (int i = 0; i < pointLights.size(); i++) {
			shader.setVector3f("pointLight[" + i + "].position", pointLights.get(i).position);
			shader.setVector3f("pointLight[" + i + "].ambient", pointLights.get(i).ambient);
			shader.setVector3f("pointLight[" + i + "].diffuse", pointLights.get(i).diffuse);
			shader.setVector3f("pointLight[" + i + "].specular", pointLights.get(i).specular);
			shader.setFloat("pointLight[" + i + "].constantValue", pointLights.get(i).constantValue);
			shader.setFloat("pointLight[" + i + "].linearValue", pointLights.get(i).linearValue);
			shader.setFloat("pointLight[" + i + "].quadraticValue", pointLights.get(i).quadraticValue);
		}
		shader.stop();
	}

	public void spotLightLighting(List<SpotLight> spotLights) {
		shader.start();
		shader.setInt("numberOfLights.numberOfSpotLights", spotLights.size());

		for (int i = 0; i < spotLights.size(); i++) {
			shader.setVector3f("spotLight[" + i + "].position", spotLights.get(i).position);
			shader.setVector3f("spotLight[" + i + "].direction", spotLights.get(i).direction);
			shader.setFloat("spotLight[" + i + "].cutOff", spotLights.get(i).cutOff);
			shader.setFloat("spotLight[" + i + "].outerCutOff", spotLights.get(i).outerCutOff);
			shader.setVector3f("spotLight[" + i + "].ambient", spotLights.get(i).ambient);
			shader.setVector3f("spotLight[" + i + "].diffuse", spotLights.get(i).diffuse);
			shader.setVector3f("spotLight[" + i + "].specular", spotLights.get(i).specular);
			shader.setFloat("spotLight[" + i + "].constantValue", spotLights.get(i).constantValue);
			shader.setFloat("spotLight[" + i + "].linearValue", spotLights.get(i).linearValue);
			shader.setFloat("spotLight[" + i + "].quadraticValue", spotLights.get(i).quadraticValue);
		}
		shader.stop();
	}

	public void render(List<Entity> entities) {
		shader.start();
		for (int i = 0; i < entities.size(); i++) {
			shader.setMatrix4f("model[" + i + "]", entities.get(i).calculateModelMatrixZYX());
		}
		shader.setBool("isParticle", false);
		GL30.glBindVertexArray(entities.get(0).getModel().getVaoID());
		GL33.glDrawElementsInstanced(GL11.GL_TRIANGLES, entities.get(0).getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0, entities.size());
		shader.stop();
	}

	public void render(Entity entity) {
		shader.start();
		shader.setMatrix4f("model[0]", entity.calculateModelMatrixZYX());
		shader.setBool("isParticle", false);
		GL30.glBindVertexArray(entity.getModel().getVaoID());
		GL33.glDrawElementsInstanced(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0, 1);
		shader.stop();
	}

	public void renderZombies(List<Zombie> zombies) {
		shader.start();
		for (int i = 0; i < zombies.size(); i++) {
			shader.setMatrix4f("model[" + i + "]", new Entity(zombies.get(i).position, zombies.get(i).rotation, zombies.get(i).scale, zombies.get(i).getModel(), zombies.get(i).aabb).calculateModelMatrixZYX());
		}
		shader.setBool("isParticle", false);
		GL30.glBindVertexArray(new Entity(zombies.get(0).position, zombies.get(0).rotation, zombies.get(0).scale, zombies.get(0).getModel(), zombies.get(0).aabb).getModel().getVaoID());
		GL33.glDrawElementsInstanced(GL11.GL_TRIANGLES, new Entity(zombies.get(0).position, zombies.get(0).rotation, zombies.get(0).scale, zombies.get(0).getModel(), zombies.get(0).aabb).getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0, zombies.size());
		shader.stop();
	}

	public void renderMuzzleFlash(Entity entity) {
		shader.start();
		shader.setMatrix4f("model[0]", entity.calculateModelMatrixXYZ());
		GL30.glBindVertexArray(entity.getModel().getVaoID());
		GL33.glDrawElementsInstanced(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0, 1);
		shader.stop();
	}

	public void renderParticles(List<Particle> particles) {
		shader.start();
		for (int i = 0; i < particles.size(); i++) {
			shader.setMatrix4f("model[" + i + "]", new Entity(particles.get(i).position, particles.get(i).rotation, particles.get(i).scale, particles.get(i).getModel(), particles.get(i).aabb).calculateModelMatrixXYZ());
		}
		shader.setBool("isParticle", true);
		GL30.glBindVertexArray(new Entity(particles.get(0).position, particles.get(0).rotation, particles.get(0).scale, particles.get(0).getModel(), particles.get(0).aabb).getModel().getVaoID());
		GL33.glDrawElementsInstanced(GL11.GL_TRIANGLES, new Entity(particles.get(0).position, particles.get(0).rotation, particles.get(0).scale, particles.get(0).getModel(), particles.get(0).aabb).getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0, particles.size());
		shader.stop();
	}

	public void stop() {
		shader.stop();
		shader.cleanUp();
	}

	public ShaderProgram getShader() {
		return shader;
	}

}
