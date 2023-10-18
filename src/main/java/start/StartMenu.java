package start;

import engine.TextManagerGL;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * This is the StartMenu
 * **Add poll event put in init() if needed
 * **All update should be done in loop() !!!
 * **Except the part of ***************, others created by default
 */
public class StartMenu {

    private long menuWindow;
    private int bgTextureID;
    private SoundLoaderGL bgSoundLoader,butSonLoader;
    private long device, context;
    private boolean startClickable =false;
    private boolean firstTimePlay = true;
    private StartButton startButton =new StartButton(
            380,200,200,50);

    private TextManagerGL textManagerGL;

    public void run() {
        //System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();
        cleanupOpenAL();
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(menuWindow);
        glfwDestroyWindow(menuWindow);
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        initOpenAL();
        // Create the window
        menuWindow = glfwCreateWindow(960, 540, "Game Launcher", NULL, NULL);
        if (menuWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(menuWindow, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(menuWindow, pWidth, pHeight);
            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            // Center the window
            glfwSetWindowPos(
                    menuWindow,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        //****************
        //Cursor on button
        glfwSetCursorPosCallback(menuWindow, (window, xpos, ypos) -> {
            if (startButton.isHovered((float)xpos, (float)ypos)) {
                // Change the appearance of the button to indicate it is clickable
                startClickable = true;
            } else {
                // Change the appearance of the button back to normal
                startClickable = false;
            }
        });
        //Mouse click button
        glfwSetMouseButtonCallback(menuWindow,new GLFWMouseButtonCallback(){
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
                    double[] xpos = new double[1];
                    double[] ypos = new double[1];
                    glfwGetCursorPos(menuWindow, xpos, ypos);
                    if (startButton.isIncluded((float)xpos[0], (float)ypos[0])) {
                        //go to main scene
                        System.out.println("Game Start !");
                        Main.StartGame=true;
                        glfwSetWindowShouldClose(window, true);
                    }else
                        System.out.println("Click the green button to start !");
                }
            }
        });
        //**************
        // Make the OpenGL context current
        glfwMakeContextCurrent(menuWindow);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        textManagerGL = new TextManagerGL("ressources/fontTest.png", "ressources/fontTest.fnt");

        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(menuWindow);
    }

    private void initOpenAL() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        if (device == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to open the default device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        if (!deviceCaps.OpenALC10) {
            throw new IllegalStateException("OpenAL 1.0 not supported on device.");
        }
        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        if (context == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create an OpenAL context.");
        }
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    private void cleanupOpenAL() {
        ALC10.alcCloseDevice(device);
        ALC10.alcDestroyContext(context);
    }

    public void loadBgTexture(String imagePath) {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        //STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = STBImage.stbi_load(imagePath, w, h, comp, 4);
        if (image == null) {
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + STBImage.stbi_failure_reason());
        }
        bgTextureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, bgTextureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w.get(), h.get(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

        STBImage.stbi_image_free(image);
    }
    private void drawBackground() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, bgTextureID);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);

        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(960, 0);

        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(960, 540);

        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, 540);

        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }


    private void loop() {

        //set position and projection mode
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 960, 540, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        loadBgTexture("ressources/jeuBg.jpg");
        bgSoundLoader = new SoundLoaderGL("ressources/MenuBGM.wav");
        bgSoundLoader.play();
        butSonLoader = new SoundLoaderGL("ressources/buttonSound.wav");

        // Run until close the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(menuWindow)) {
            // Set the clear color
            glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //******************** //All update doing here !!!

            GL11.glColor3f(1.0f, 1.0f, 1.0f);// Use initial color
            drawBackground();

            if (startClickable){
                if (firstTimePlay && !butSonLoader.isPlaying()){
                    butSonLoader.play();
                    firstTimePlay = false;
                    /*  Use this to control sound's play time if need
                    new java.util.Timer().schedule(new java.util.TimerTask(){
                        @Override
                        public void run() {
                            butSonLoader.stop();
                        }
                    }, 100);
                     */
                }

                startButton.drawButtonWithBorder();
                GL11.glColor3f(1.0f, 0, 1.0f);
                textManagerGL.renderText("Start Game", 380, 200); //Rendu du texte
            }
            else {
                butSonLoader.stop();
                firstTimePlay = true;
                startButton.drawButton();
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
                textManagerGL.renderText("Start Game", 380, 200); //Rendu du texte
            }


            //*******************

            glfwSwapBuffers(menuWindow); // swap the color buffers
            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }
        butSonLoader.cleanup();
        bgSoundLoader.cleanup();
    }
}