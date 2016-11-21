package test;

import com.marvinkirsch.core.AbstractGame;
import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Input;
import com.marvinkirsch.core.Renderer;
import com.marvinkirsch.core.fx.Image;
import com.marvinkirsch.core.fx.Light;
import com.marvinkirsch.core.fx.ShadowType;
import com.marvinkirsch.core.fx.SoundClip;

import java.awt.event.KeyEvent;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class Game extends AbstractGame {

    private Image image = new Image("/test.png");
    private Image image2 = new Image("/test2.png");

    private Light light0 = new Light(0xffff0000, 60);
    private Light light1 = new Light(0xffffffff, 200);
    private Light light2 = new Light(0xff0000ff, 60);
    private SoundClip clip = new SoundClip("/coin pickup.wav");

    public static void main(String args[]) {
        GameContainer gc = new GameContainer(new Game());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.setScale(3);
        gc.start();
    }

    float x = 0;

    public Game() {
        image2.shadowType = ShadowType.FADE;
    }

    @Override
    public void update(GameContainer gc, float dt) {

        if(Input.isKeyPressed(KeyEvent.VK_A)) {
            if(!clip.isRunning())
                clip.loop();
            else {
                clip.stop();
            }
        }

        if(Input.isKeyPressed(KeyEvent.VK_V)) {
            clip.play();
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {

        r.drawLight(light1, Input.getMouseX(), Input.getMouseY());
        r.drawImage(image, 0, 0);
        r.drawImage(image2, 0, 0);
    }
}
