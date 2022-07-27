package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class DestroyerShip extends Ship{
    int sizeX = 4;
    int sizeY = 1;

    public DestroyerShip(boolean horizontal) {
        super(Color.PINK, -4);
        if (horizontal) {
            setSizey(sizeY);
            setSizex(sizeX);
        }
        else{
            setSizey(sizeX);
            setSizex(sizeY);
        }
    }
}
