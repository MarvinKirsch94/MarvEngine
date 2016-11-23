package test;

import com.marvinkirsch.core.AbstractGame;
import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class GameManager extends AbstractGame {

    public static void main(String args[]) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.setScale(3);
        gc.setClearScreen(true);
        gc.setDynamicLights(false);
        gc.setLightEnable(false);
        gc.start();
    }

    public GameManager() {
        push(new PlayState());
    }

    @Override
    public void init(GameContainer gc) {

    }

    @Override
    public void update(GameContainer gc, float dt) {
        peek().update(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        peek().render(gc, r);
    }
}
