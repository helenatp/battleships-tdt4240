package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.model.ships.Ship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int sidemargin;
    private ArrayList<List<Integer>> board;
    private ArrayList<Ship> ships;
    private ArrayList<List<Integer>> initializeOpponentBoard;
    private Cell cell;
    private Texture texture; //the board texture
    private ShapeRenderer shapeRenderer;
    private float width; //the width of the board, board is a square -> width = height

    public void setBoard(ArrayList<List<Integer>> board) {
        this.board = board;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public ArrayList<List<Integer>> getInitializeOpponentBoard() {
        return initializeOpponentBoard;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public ArrayList<List<Integer>> getOpponentBoard() {
        return initializeOpponentBoard;
    }

    public void setSidemargin(int sidemargin) {
        this.sidemargin = sidemargin;
    }

    public void createNewShipsList() {
        ships = new ArrayList<Ship>();
    }

    public void createNewBoardList() {
        board = new ArrayList<List<Integer>>();
    }

    public void createNewInitializeOpponentBoardList() {
        initializeOpponentBoard = new ArrayList<List<Integer>>();
    }


    /**
     * @return the list that keeps track of the values on every cell on the board
     */
    public ArrayList<List<Integer>> getBoard() {
        return board;
    }

    /**
     * @return the distance the board should have from the endge of the device when its drawn
     */
    public int getSidemargin() {
        return sidemargin;
    }


    /**
     * @return the width the board should have when its drawn
     */
    public float getWidth() {
        return width;
    }

    /**
     * @return every ship that is placed on the board
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }
}