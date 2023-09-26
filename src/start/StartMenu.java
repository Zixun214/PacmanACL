package start;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
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
    private boolean startClickable =false;
    private StartButton startButton =new StartButton(
            380,200,200,100);

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();
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
        // Create the window
        menuWindow = glfwCreateWindow(960, 540, "Start Menu", NULL, NULL);
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
                startClickable =true;
            } else {
                // Change the appearance of the button back to normal
                startClickable =false;
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
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(menuWindow);
    }

    private void loop() {
        GL.createCapabilities();

//set position and projection mode
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 960, 540, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
//
        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        // Run until close the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(menuWindow)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//********************
            //All update doing here !!!

            if (startClickable)
                startButton.drawButtonWithBorder();
            else
                startButton.drawButton();

//*******************
            glfwSwapBuffers(menuWindow); // swap the color buffers
            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }
    }
}
