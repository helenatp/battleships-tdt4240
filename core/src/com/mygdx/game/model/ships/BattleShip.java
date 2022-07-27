package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class BattleShip extends Ship{

    int sizeX = 2;
    int sizeY = 2;

    /**
     * the constructor for the ship, sets a given color for the ship an sets sizeX and sizeY according to the boolean horizontal
     * @param horizontal    a boolean for deciding what size the ship should have in x-direction and y-direction
     */


    public BattleShip(boolean horizontal){

        super(Color.BROWN, -1);
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
