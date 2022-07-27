package com.mygdx.game.model;

public class Cell {

    public static int SHIP = 1;
    public static int MISS = 2;
    public static int EMPTY = 0;
    public static int HIT = 3;

    /**
     * cheks if a cell has already been shot at
     * @param value the value the cell contains
     * @return  false if the cell has already been shot at
     */
    public boolean isValidMove(int value){
        if  (value == HIT || value == MISS) {
            System.out.println("Already shot here");
            return false;
        }
        return true;
    }

    /**
     * checks if a cell that gets shot at contains a ship
     * @param value the value the cell contains
     * @return  true if there is a ship occupying this cell, false if not
     */
    public boolean isHit(int value){
        return (value == SHIP);
    }

    /**
     * gives the new value the cell should get
     * @param value the value the cell contains
     * @return   the new value the cell should get
     */
    public int setCell(int value){
        int newValue;
        if (isHit(value)){
            newValue = HIT;
        }
        else {
            newValue = MISS;
        }
        return newValue;
    }
}
