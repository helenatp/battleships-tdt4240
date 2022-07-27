package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;
import com.mygdx.game.view.ViewComponents.Feedback;
import com.mygdx.game.view.ViewComponents.FeedbackDelay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameStateController {

    private PlayerController playerController;
    private BoardController boardController;
    private ShipController shipController;
    private ScoreBoardController scoreBoardController;

    private Player player;
    private Board board;
    private Board opponentBoard;
    private ScoreBoard scoreBoard;
    private Boolean singlePlayer = false;

    public static Boolean playersAdded = false;
    public static Boolean playersReady = false;
    public static Boolean finishedGame = false;




    public static boolean myTurn = false;
    private boolean singleTurn = true;
    private boolean canShoot = true;
    public static boolean shotChanged = false;
    public static ArrayList<Integer> lastShot;
    private static Collection<FeedbackDelay> feedbackDelayListeners = new ArrayList<FeedbackDelay>();
    private static Collection<Feedback> feedbackListeners = new ArrayList<>();

    /**
     * DESIGN PATTERN: Observer
     * Feedback listeners and feedback delay listener use the observer pattern
     */

    public  GameStateController(){
        this.playerController = new PlayerController();
        this.boardController = new BoardController();
        this.scoreBoardController = new ScoreBoardController();
        this.shipController = new ShipController();
    }

    public void initilializeGameFirebase(){
        this.opponentBoard = boardController.createBoardFromOpponent(Battleships.firebaseConnector.getOpponentBoard(), player.getBoard().getSidemargin());
        setMyTurn(Battleships.firebaseConnector.addTurnListener());
        setCanShoot(true);
        Battleships.firebaseConnector.getOpponentsShot();
        Battleships.firebaseConnector.gameFinsihedListener();
        player.setOpponentBoard(opponentBoard);
        this.scoreBoard = scoreBoardController.createNewScoreBoard(player);
    }

    public PlayerController getPlayerController() {
        return playerController;
    }


    public BoardController getBoardController() {
        return boardController;
    }


    public ShipController getShipController() {
        return shipController;
    }

    public ScoreBoardController getScoreBoardController() {
        return scoreBoardController;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        setBoard(player.getBoard());
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public static void setMyTurn(boolean myTurn) {
        GameStateController.myTurn = myTurn;
    }

    /**
     * Updates the players board after opponent has shot, so that the shot appears on the screen
     */
    public void updateShot(){
        if(shotChanged && !myTurn){
            boardController.updateBoard(board, lastShot.get(0), lastShot.get(1), lastShot.get(2));
        }
        shotChanged = false;
    }

    /**
     * computes the index in a double-linked-list from two coordinates
     * finds the cell a person were trying to touch from on a drawn board
     * does not check if the indexes is inside the board
     * @param x_pos the x_coordinate
     * @param y_pos the y_coordinate
     * @return      the indexes for the cell you were trying to touch
     */
    public ArrayList<Integer> getIndex(float x_pos, float y_pos){
        x_pos = x_pos -player.getBoard().getSidemargin();
        y_pos = y_pos -player.getBoard().getSidemargin();
        ArrayList<Integer>  indexes = new ArrayList<>();
        float t_width = player.getBoard().getWidth();
        float cell_width = t_width / player.getBoard().getBoard().size();
        float cell_height = t_width / player.getBoard().getBoard().size();

        indexes.add((int) (x_pos / cell_width));
        indexes.add((int) (y_pos / cell_height));
        return indexes;
    }

    public void shootSingle(ArrayList<Integer> indexes) {
        if (boardController.singleShoot(board, indexes.get(0), indexes.get(1))) {
            setCanShoot(false);
        }
        else{
            setCanShoot(true);
        }
    }

    public void shoot(ArrayList<Integer> indexes){
        if (myTurn && canShoot){
            if(boardController.shoot(opponentBoard, indexes.get(0), indexes.get(1))) {
                setCanShoot(false);
                FeedbackDelay();
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        changeCurrentPlayer();
                        setCanShoot(true);
                    }
                };
                executor.schedule(task, 2, TimeUnit.SECONDS);
            }
            else{
                setCanShoot(true);
                FeedbackDelay();
            }
        }
    }

    public void setCanShoot(boolean canShoot){
        this.canShoot = canShoot;
        FeedbackDelay();
    }

    public Board getSingleBoard(){
        return board;
    }

    public Board getBoard(){
        if (!myTurn){
            return board;
        }
        return opponentBoard;
    }

    public boolean isFinished(){
        if (boardController.isFinished(opponentBoard)){
            Battleships.firebaseConnector.gameFinished();
        }
        return finishedGame;
    }


    public void changeCurrentPlayer(){
        Battleships.firebaseConnector.changeTurn();
    }

    public String turn(){
        if (myTurn){
            return "Now you can shoot!";
        }
        return "Opponents turn!";
    }


    public Boolean checkPlayersAdded(){
        return playersAdded;
    }

    public Boolean checkPlayersReady(){
        playersAdded = false;
        return playersReady;
    }

    public void createInitalizeOpponentBoard(){
        boardController.makeInitalizeOpponentBoard(board);
        System.out.println("Nå ser initialize Opponent board sånn ut: " + board.getInitializeOpponentBoard());
    }

   public void moveShip(int new_x, int new_y, ArrayList<List<Integer>> location){
       // finds the index based on the coordinates of the touch
       ArrayList<Integer> index= getIndex(new_x,new_y);
       // saves the first position of the current (old) location of the markedShip
       List<Integer> first_position = shipController.getMarkedShip().getLocation().get(0);
       //removes the ship from the board (changes the 1-value into 0)
       boardController.removeShipPosition(board, shipController.getMarkedShip().getLocation());
       //adds the new position to the board (replaces the 0-values on the cells with 1)
       shipController.createNewPosition(shipController.getMarkedShip(), index.get(0), index.get(1));
       // checks if the new location of the markedShip is inside the board
       if(!boardController.isInsideBoard(shipController.getMarkedShip().getLocation())){
           firefeedbackFalse();
           shipController.createNewPosition(shipController.getMarkedShip(), first_position.get(0),first_position.get(1));
       }
       // checks if the ship can be moved to the new location
       else if(!boardController.isValidLocation(board, shipController.getMarkedShip().getLocation())){
           firefeedbackFalse();
           shipController.createNewPosition(shipController.getMarkedShip(), first_position.get(0),first_position.get(1));
       }

       else {
           firefeedbackTrue();
       }

       boardController.addShipPosition(board, shipController.getMarkedShip().getLocation());
       //sets the markedShip value to null so we can select new ships to move :D
       shipController.setMarkedShip(null);
   }




    public static void firefeedbackTrue() {
        for (Feedback feedbackListener: feedbackListeners) {
            feedbackListener.fireAction(true);
        }
    }
    public void firefeedbackFalse() {
        for (Feedback feedbackListener: feedbackListeners) {
            feedbackListener.fireAction(false);
        }
    }



    public ArrayList<List<Integer>> getOpponentBoardFromFirebase(){
        return Battleships.firebaseConnector.getOpponentBoard();
    }


    public void sendBoard(){
        createInitalizeOpponentBoard();
        Battleships.firebaseConnector.sendBoard(board.getOpponentBoard());
        Battleships.firebaseConnector.boardListener();
    }

    public static void addFeedbackListener(Feedback feedbackListener){
        feedbackListeners.add(feedbackListener);
    }


    public static void addFeedbackDelayListener(FeedbackDelay feedbackDelayListener) {
        feedbackDelayListeners.add(feedbackDelayListener);
    }

    public static void firefeedbackDelayBoolean(boolean feedback) {
        for (FeedbackDelay feedbackDelayListener: feedbackDelayListeners) {
            feedbackDelayListener.fireActionDelay(feedback);
        }
    }

    public void FeedbackDelay(){
        if(!this.canShoot){
            firefeedbackDelayBoolean(true);
        }
        else{
            firefeedbackDelayBoolean(false);
        }
    }

    public ScoreBoard getScoreBoard(){
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard){
        this.scoreBoard = scoreBoard;
    }

    public Boolean getSingleTurn(){
        return this.singleTurn;
    }

    public void setSinglePlayer(Boolean singlePlayer){
        this.singlePlayer = singlePlayer;
    }

    public Boolean getSinglePlayer(){
        return this.singlePlayer;
    }

}
