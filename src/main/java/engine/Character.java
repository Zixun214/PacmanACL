package engine;

public class Character {
    public int x, y, width, height, xoffset, yoffset, xadvance;

    public Character(int x, int y, int width, int height, int xoffset, int yoffset, int xadvance) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.xadvance = xadvance;
    }
}