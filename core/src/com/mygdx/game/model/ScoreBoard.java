package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private ArrayList<List<Integer>> boardList;
    private Player player;
    private int opponentScore;
    private int score;


    public ScoreBoard(Player player){
        this.boardList = player.getOpponentBoard().getBoard();
        this.player = player;
        this.score = 0;
    }

    public ScoreBoard(Player player, Boolean singlePayer){
        this.player = player;
        this.score = 0;
    }


    public String getName(){
        return player.getName();
    }

    public int getScore(){
        return score;
    }

    public int getOpponentScore(){
        return this.opponentScore;
    }

    public void setOpponentScore(int score){
        this.opponentScore = score;
    }

    public ArrayList<List<Integer>> getBoardList() {
        return boardList;
    }

    public void setBoardList(ArrayList<List<Integer>> boardList) {
        this.boardList = boardList;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
