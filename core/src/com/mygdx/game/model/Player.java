package com.mygdx.game.model;

public class Player {

    public String name;
    private Board board;
    private Board opponentBoard;

    /**
     * the constructor, sets the name and the boolean, creates a board that is 10x10 and has a sidemargin with size 10
     * @param name  the players username
     */
    public Player(String name){
        this.name = name;
    }

    public Board getBoard(){
        return board;
    }

    public String getName(){
        return name;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setOpponentBoard(Board opponentBoard){
        this.opponentBoard = opponentBoard;
    }

    public Board getOpponentBoard(){
        return opponentBoard;
    }

}
