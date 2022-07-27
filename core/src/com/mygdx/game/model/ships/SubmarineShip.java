package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class SubmarineShip extends Ship{

    int sizeX = 3;
    int sizeY = 2;

    public SubmarineShip(boolean horizontal){

        super(Color.RED, -6);
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
