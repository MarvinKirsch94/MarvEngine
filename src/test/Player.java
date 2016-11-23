package test;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;
import com.marvinkirsch.core.components.GameObject;
import com.sun.glass.events.KeyEvent;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class Player extends GameObject {

    public Player(int x, int y) {
        setTag("player");
        this.x = x;
        this.y = y;
        w = 16;
        h = 64;
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
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRect((int)x, (int)y, (int)w, (int)h, 0xffffffff);
    }

    @Override
    public void dispose() {

    }
}
