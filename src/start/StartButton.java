package start;

import static org.lwjgl.opengl.GL11.*;

/**
 * Il n'y a pas de Button dans lwjgl
 * Donc il faut créer à la main
 */
public class StartButton {

    private float x, y, width, height;
    float borderWidth = 2; // border width
    float borderColor[] = {0,0,255}; // border color
    float buttonColor[] = {0,200,0};// button color

    public StartButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isIncluded(float mx, float my){//mouse x and y
        return mx>=x && my>=y && mx<=(x+width) && my<=(y+height);
    }

    public void drawButton(){
        glColor3f(buttonColor[0],buttonColor[1],buttonColor[2]);
        glRectf(x,y,x+width,y+height);
    }

    public boolean isHovered(float mx, float my) {
        return mx >=x && mx <=(x+width) && my >=y && my <=(y+height);
    }
    public void drawButtonWithBorder() {
        glColor3f(borderColor[0], borderColor[1], borderColor[2]);
        glRectf(x-borderWidth,y-borderWidth,x+width+borderWidth,y+height+borderWidth);
        drawButton();
    }
}
