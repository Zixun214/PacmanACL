package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextManagerGL {
    private int textureId;
    private Map<java.lang.Character, Character> characters = new HashMap<java.lang.Character, Character>();
    private static final int TEXTURE_WIDTH = 512;
    private static final int TEXTURE_HEIGHT = 512;

    public TextManagerGL(String fontTexturePath, String fontMetadataPath) {
        this.textureId = loadTexture(fontTexturePath);
        initializeCharacters(fontMetadataPath);
    }

    public void renderText(String text, float x, float y) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        for (char c : text.toCharArray()) {
            Character character = characters.get(c);
            if (character == null) {
                System.err.println("Unknown character: " + c);
                continue; // skip this character
            }
            float u = character.x / (float) TEXTURE_WIDTH;
            float v = character.y / (float) TEXTURE_HEIGHT;
            float charWidth = character.width / (float) TEXTURE_WIDTH;
            float charHeight = character.height / (float) TEXTURE_HEIGHT;

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(u, v);
            GL11.glVertex2f(x + character.xoffset, y + character.yoffset);
            GL11.glTexCoord2f(u + charWidth, v);
            GL11.glVertex2f(x + character.xoffset + character.width, y + character.yoffset);
            GL11.glTexCoord2f(u + charWidth, v + charHeight);
            GL11.glVertex2f(x + character.xoffset + character.width, y + character.yoffset + character.height);
            GL11.glTexCoord2f(u, v + charHeight);
            GL11.glVertex2f(x + character.xoffset, y + character.yoffset + character.height);
            GL11.glEnd();

            x += character.xadvance;
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

    }

    private void initializeCharacters(String fontMetadataPath) {
        characters.clear();
        parseFntFile(fontMetadataPath);
    }

    private void parseFntFile(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (line.startsWith("char ")) {
                    Map<String, String> values = parseValues(line);
                    if (!values.keySet().containsAll(Arrays.asList("id", "x", "y", "width", "height", "xoffset", "yoffset", "xadvance"))) {
                        // Log error and continue with the next line
                        System.err.println("Malformed char definition in .fnt file: " + line);
                        continue;
                    }
                    try {
                        char id = (char) Integer.parseInt(values.get("id"));
                        int x = Integer.parseInt(values.get("x"));
                        int y = Integer.parseInt(values.get("y"));
                        int width = Integer.parseInt(values.get("width"));
                        int height = Integer.parseInt(values.get("height"));
                        int xoffset = Integer.parseInt(values.get("xoffset"));
                        int yoffset = Integer.parseInt(values.get("yoffset"));
                        int xadvance = Integer.parseInt(values.get("xadvance"));
                        characters.put(id, new Character(x, y, width, height, xoffset, yoffset, xadvance));
                    } catch (NumberFormatException e) {
                        System.err.println("Non-integer value in .fnt file: " + e.getMessage());
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the .fnt file", e);
        }
    }

    private Map<String, String> parseValues(String line) {
        Map<String, String> values = new HashMap<>();
        for (String part : line.split(" ")) {
            String[] valuePair = part.split("=");
            if (valuePair.length == 2) {
                values.put(valuePair[0], valuePair[1]);
            }
        }
        return values;
    }

    private int loadTexture(String path) {
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        // Charger l'image
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load a texture file!"
                        + System.lineSeparator() + STBImage.stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        // Paramètres de texture OpenGL
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);


        STBImage.stbi_image_free(image);

        return textureId;
    }

    public void cleanup() {
        GL11.glDeleteTextures(textureId);
    }

}