package test;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;
import com.marvinkirsch.core.components.ObjectManager;
import com.marvinkirsch.core.components.State;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class PlayState extends State {

    public PlayState() {
        manager = new ObjectManager();
        manager.addObject(new Player(0, 0));
        manager.addObject(new Ball(156, 116));
        manager.addObject(new Enemy(304, 0));
    }

    @Override
    public void update(GameContainer gc, float dt) {
        manager.updateObjects(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        manager.renderObjects(gc, r);
    }

    @Override
    public void dispose() {
        manager.disposeObjects();
    }

    public void setManager(ObjectManager manager) {
        this.manager = manager;
    }

    public ObjectManager getManager() {
        return manager;
    }
}
