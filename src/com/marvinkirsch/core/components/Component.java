package com.marvinkirsch.core.components;

import com.marvinkirsch.core.GameContainer;
import com.marvinkirsch.core.Renderer;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public abstract class Component {

    protected String tag = null;
    public abstract void update(GameContainer gc, GameObject object, float dt);
    public abstract void render(GameContainer gc, Renderer r);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
