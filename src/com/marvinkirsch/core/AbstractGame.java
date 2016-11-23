package com.marvinkirsch.core;

import com.marvinkirsch.core.components.State;

import java.util.Stack;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public abstract class AbstractGame {

    private Stack<State> states = new Stack<State>();

    public abstract void init(GameContainer gc);
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer r);

    public State peek() {
        return states.peek();
    }

    public void push(State s) {
        states.push(s);
    }

    public void pop() {
        states.peek().dispose();
        states.pop();
    }

    public void setStates(State s) {
        states.pop();
        states.push(s);
    }
}
