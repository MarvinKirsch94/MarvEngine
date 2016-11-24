package test;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;
import com.marvinkirsch.core.components.Collider;
import com.marvinkirsch.core.components.GameObject;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class Ball extends GameObject {

    boolean left = true;
    float speedY = 0;

    public Ball(int x, int y) {
        setTag("ball");
        this.x = x;
        this.y = y;
        w = 16;
        h = 16;
        addComponent(new Collider());
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(left) {
            x -= dt*50;
        } else {
            x += dt*50;
        }

        y += speedY;

        if(y < 0) {
            y = 0;
            speedY *= -1;
        }

        if(y + h > gc.getHeight())
        {
            y = gc.getHeight() - h;
            speedY *= -1;
        }

        updateComponents(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int)x, (int)y, (int)w, (int)h, 0xff00ff00);
    }

    @Override
    public void componentEvent(String name, GameObject object) {
        if(name.equalsIgnoreCase("collider")) {
            if(object.getX() < x) {
                left = false;
            } else {
                left = true;
            }

            speedY = -((object.getY() + (object.getH() / 2)) - (y + (h / 2))) / (object.getH() / 2);
        }
    }

    @Override
    public void dispose() {

    }
}
