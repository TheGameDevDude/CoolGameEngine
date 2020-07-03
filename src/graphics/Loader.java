package graphics;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import model.Model;

public class Loader {

	public int textureWidth;
	public int textureHeight;

	public Model loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {

		// create vertex array object
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		// create position buffer
		int positionBufferID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferID);
		FloatBuffer positionBufferData = storeDataInFloatBuffer(positions);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBufferData, GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// create texture coordinate buffer
		int textureCoordsBuffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureCoordsBuffer);
		FloatBuffer textureCoordsData = storeDataInFloatBuffer(textureCoords);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordsData, GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// create normal coordinate buffer
		int normalsBufferID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalsBufferID);
		FloatBuffer normalsBufferData = storeDataInFloatBuffer(normals);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBufferData, GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// create indices buffer
		int indicesBufferID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		IntBuffer indicesBufferData = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferData, GL15.GL_STATIC_DRAW);

		GL30.glBindVertexArray(0);
		GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);

		return new Model(vaoID, indices.length);
	}

	public int loadTexture(String filePath) {
		int id = 0;
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(filePath))) {
			PNGDecoder decoder = new PNGDecoder(is);
			int width = decoder.getWidth(), height = decoder.getHeight();
			textureWidth = width;
			textureHeight = height;
			ByteBuffer pixelData = BufferUtils.createByteBuffer(4 * width * height);
			decoder.decode(pixelData, 4 * width, Format.RGBA);
			pixelData.flip();
			id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelData);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
