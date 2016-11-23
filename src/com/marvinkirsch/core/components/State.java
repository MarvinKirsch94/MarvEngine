package com.marvinkirsch.core.components;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public abstract class State {

    protected ObjectManager manager = new ObjectManager();
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    public abstract void dispose();

    public ObjectManager getManager() {
        return manager;
    }

    public void setManager(ObjectManager manager) {
        this.manager = manager;
    }
}
