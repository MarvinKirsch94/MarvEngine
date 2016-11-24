package test;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;
import com.marvinkirsch.core.components.Collider;
import com.marvinkirsch.core.components.GameObject;
import com.sun.glass.events.KeyEvent;

/**
 * @author Marvin Kirsch
 * @version 0.0
 *          created on 24.11.2016
 */
public class Enemy extends GameObject {

    private GameObject target = null;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        w = 16;
        h = 64;
        addComponent(new Collider());
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(target == null) {
           target = gc.getGame().peek().getManager().findObject("ball");
        }

        if(target.getY() + target.getH() / 2 > y - 2) {
            y += dt * 75;
        }

        if(target.getY() + target.getH() / 2 < y + 2) {
            y -= dt * 75;
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
