package com.mygdx.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.controller.GameStateController;

public abstract class State {

    /**
     *  QUALITY ATTRIBUTE: MODIFIABILITY
     *           tactic: Abstract common services
     *
     *   State is an abstract class that is implemented in all the View classes. With the gsm we can control
     * which state that is showing on the screen.
     */
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected GameStateController gsc;

    protected State(GameStateManager gsm, GameStateController gsc){
        this.gsc = gsc;
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}