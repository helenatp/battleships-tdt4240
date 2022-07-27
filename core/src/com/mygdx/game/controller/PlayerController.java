package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerController {

    /**
     * creates a new player and a board for this player
     * @param name the name of the player
     * @return the player that is created
     */
    public Player createPlayer(String name){
        Player player = new Player(name);
        BoardController controller= new BoardController();
        Board board = controller.createBoard(10,10);
        player.setBoard(board);
        return player;
    }

    public void addPlayerFirebase(Player player){
        Battleships.firebaseConnector.addPlayer(player);
    }


}
