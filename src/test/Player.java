package test;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;
import com.marvinkirsch.core.components.Collider;
import com.marvinkirsch.core.components.GameObject;
import com.sun.glass.events.KeyEvent;

/**
 * @author Marvin Kirsch
 * @version 0.0
 * created on 24.11.2016
 */
public class Player extends GameObject {

    public Player(int x, int y) {
        setTag("player");
        this.x = x;
        this.y = y;
        w = 16;
        h = 64;
        addComponent(new Collider());
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isKey(KeyEvent.VK_W)) {
            y -= (75 * dt);

            if(y < 0) {
                y = 0;
            }
        }

        if(gc.getInput().isKey(KeyEvent.VK_S)) {
            y += (75 * dt);

            if(y + h > gc.getHeight()) {
                y = gc.getHeight() - h;
            }
        }

        updateComponents(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int)x, (int)y, (int)w, (int)h, 0xffffffff);
    }

    @Override
    public void componentEvent(String name, GameObject object) {

    }

    @Override
    public void dispose() {

    }
}
