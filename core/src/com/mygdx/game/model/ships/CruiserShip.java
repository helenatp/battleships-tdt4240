package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class CruiserShip extends Ship{

    int sizeX = 3;
    int sizeY = 1;

    public CruiserShip(boolean horizontal){
        super(Color.GREEN, -3);
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
