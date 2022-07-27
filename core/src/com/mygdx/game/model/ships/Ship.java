package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Ship {
    private int sizex;  //the size of the ship in x-direction
    private int sizey;  //the size of the ship in y-direction
    private Color color;    //the color the ship should be drawn in
    private Boolean isSunk; //keeps track of whether the whole ship is hit
    private ArrayList<List<Integer>> location;  //the different cells the ships occupies on the board
    private ArrayList<List<Integer>> shotCoordinates;
    private int shipNr;

    /**
     * QUALITY ATTRIBUTE: Modifiability
     *          Tactic: Abstract common services
     *
     * Sets the color of the ships, creates a new list for the shotCoordinates and sets isSunk to false
     * @param color The color of the ship
     */
    protected Ship(Color color, int shipNr){
        this.color = color;
        this.shipNr = shipNr;
        isSunk = false;
        shotCoordinates = new ArrayList<List<Integer>>();
        this.location = new ArrayList<List<Integer>>();
    }

    public int getSizex() {
        return sizex;
    }

    public int getSizey() {
        return sizey;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getSunk() {
        return isSunk;
    }

    public void setSunk(Boolean sunk) {
        isSunk = sunk;
    }


    public void setLocation(ArrayList<List<Integer>> location) {
        this.location = location;
    }

    public ArrayList<List<Integer>> getShotCoordinates() {
        return shotCoordinates;
    }

    /**
     *
     * @return the ships location
     */
    public ArrayList<List<Integer>> getLocation() {
        return this.location;
    }

    public void addLocation(int row, int col){
        this.location.add(Arrays.asList(col, row));
    }

    public Color getColor(){
        return color;
    }

    /**
     * sets the size of the ship in x-direction
     * @param size  the number of cells the ship should occupie in x-direction
     */
    protected void setSizex(int size){
        this.sizex = size;
    }

    /**
     * sets the size of the ship in y-direction
     * @param size  the number of cells the ship should occupie in y-direction
     */
    protected void setSizey(int size){
        this.sizey = size;
    }

    public int getShipNr(){  return shipNr; }

}
