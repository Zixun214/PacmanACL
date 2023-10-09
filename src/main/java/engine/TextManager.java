package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextManager {

    private String text;
    private Font font;
    private Color color;

    public TextManager(String text, Font font, Color color) {
        this.text = text;
        this.font = font;
        this.color = color;
    }

    public void drawText(Graphics g, int x, int y) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void setText(String text) {
        this.text = text;
    }

    // Additional methods to update font, color, etc. can be added as per requirements
}