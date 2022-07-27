package com.mygdx.game;

import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface FirebaseServices {

    public void gameFinished();
    public void addPlayer(Player player);
    public void changeTurn();
    public Boolean addTurnListener();
    public ArrayList<List<Integer>> getOpponentBoard();
    public void sendBoard(ArrayList<List<Integer>> board);
    public void boardListener();
    public void sendShot(int x, int y, int newValue);
    public void getOpponentsShot();
    public void setScoreboard(ScoreBoard scoreboard);
    public void retrieveScoreboard();
    public void gameFinsihedListener();
    public void setSinglePLayerScoreboard(ScoreBoard scoreboard);
    public void retrieveSingleScoreboard();
}
