package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.GameCodeHolder;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ScoreBoardController {

    public static HashMap<String, Integer> printScoreboard = new HashMap<>();

    public void calculateScore(ScoreBoard scoreBoard){
        scoreBoard.setScore(0);
        for (List<Integer> board : scoreBoard.getBoardList()){
            for (Integer b : board){
                if (b == 2){
                    scoreBoard.setScore(scoreBoard.getScore() - 20);
                }else if(b == 3){
                    scoreBoard.setScore(scoreBoard.getScore() + 100);
                }
            }
        }
    }

    public ScoreBoard createNewScoreBoard(Player player){
        ScoreBoard scoreboard = new ScoreBoard(player);
        return scoreboard;
    }

    public ScoreBoard createNewSingleScoreBoard(Player player, Boolean singlePlayer){
        ScoreBoard scoreboard = new ScoreBoard(player, singlePlayer);
        return scoreboard;
    }


    public void updateScoreboard(ScoreBoard scoreboard){
        calculateScore(scoreboard);
        Battleships.firebaseConnector.setScoreboard(scoreboard);
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        Battleships.firebaseConnector.retrieveScoreboard();
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.opponentScore(scoreboard);
        this.sortScoreboard();
    }

    public void updateSingleScoreboard(ScoreBoard scoreboard){
        calculateScore(scoreboard);
        Battleships.firebaseConnector.setSinglePLayerScoreboard(scoreboard);
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        Battleships.firebaseConnector.retrieveSingleScoreboard();
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.sortScoreboard();
    }

    private void opponentScore(ScoreBoard scoreboard) {
        GameCodeHolder gameCodeHolder = GameCodeHolder.getInstance(Battleships.firebaseConnector);
        scoreboard.setOpponentScore(printScoreboard.get(gameCodeHolder.getOpponentName()));
    }

    public void updateScore(ScoreBoard scoreboard){
        calculateScore(scoreboard);
    }

    private void sortScoreboard(){
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(printScoreboard.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        if (list.size() >= 5){
            for (int i = 0; i < 5 ; i++) {
                temp.put(list.get(i).getKey(), list.get(i).getValue());
            }
        }else {
            for (Map.Entry<String, Integer> aa : list) {
                temp.put(aa.getKey(), aa.getValue());
            }
        }
        printScoreboard = temp;
    }

    public HashMap<String, Integer> getScoreboard(){
        return printScoreboard;
    }


}
