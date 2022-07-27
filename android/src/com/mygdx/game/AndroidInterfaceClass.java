package com.mygdx.game;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.controller.ScoreBoardController;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static android.content.ContentValues.TAG;



public class AndroidInterfaceClass implements FirebaseServices {
    DatabaseReference data;
    FirebaseDatabase database;
    DatabaseReference gameInfo;
    Integer turnPlayer;
    GameCodeHolder gameCodeHolder;
    private Player player;
    Integer playerId;
    private String id;
    static ArrayList<List<Integer>> opponentBoard;

    /**
     * DESIGN PATTERN: observer
     * The methods that use a ValueEventListener use the observer pattern.
     *
     * AndroidInterfaceClass contains all the logic to write and retrieve data from our firebase
     * realtime database. The class contains the singleton gameCodeHolder to save the gameId
     * and playerId throughout the game.
     */
    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://battleship-80dca-default-rtdb.firebaseio.com/");
        data = database.getReference();
        gameCodeHolder = GameCodeHolder.getInstance(this);
    }

    /**
     * Receive the player and pass it on to the waitingroom method.
     * @param player player object from model, contains board and name.
     */
    @Override
    public void addPlayer(Player player) {
        this.player = player;
        this.addWaitingroomListener();
    }

    /**
     * For the first player that is added to the waitingRoom the waitingRoom will be created and createGame() is called
     * to initialize a new game. WaitingRoomChildListener() is added to the player detected when another player is added to the game.
     * When the second player enters the waitingroom the player call initializeGame().
     * The gameId and playerId in the singleton class, gameCodeHolder, is set for both players.
     */
    private void addWaitingroomListener(){
        data.child("WaitingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pId="";
                    for (DataSnapshot player : snapshot.getChildren()){
                        pId = (String) player.getValue();
                    }
                    data.child("WaitingRoom").child(player.getName()).setValue(pId);
                    gameCodeHolder.setGameId(pId);
                    gameCodeHolder.setPlayerId(0);
                    int waiting = (int) snapshot.getChildrenCount() +1;
                    if(waiting > 1){
                        initializeGame();
                    }
                }else{
                    createGame();
                    gameCodeHolder.setGameId(id);
                    gameCodeHolder.setPlayerId(1);
                    DatabaseReference waitingRoom = data.child("WaitingRoom");
                    waitingRoom.child(player.getName()).setValue(id);
                    waitingRoomChildListener();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    /**
     * Create the game in firebase where both players will be added when there is two in waitingroom.
     * Initialize variables; players, LastShot, GameFinished, that are needed and will be updated later.
     */
    private void createGame(){
        this.id = this.generateGameId();
        this.gameInfo = data.child("GameState").child(id).child("GameInfo");
        gameInfo.child("GameId").setValue(id);
        gameInfo.child("Players").child("Player0").setValue("0");
        gameInfo.child("LastShot").child("0").setValue("0");
        gameInfo.child("LastShot").child("1").setValue("0");
        gameInfo.child("LastShot").child("2").setValue("0");
        gameInfo.child("GameFinished").setValue("False");
    }

    /**
     * Generates a random gameId for the new game that is created.
     * @return a String with the gameId.
     */
    private String generateGameId(){
        String possibleChar="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String gameId="";
        for(int i=0;i<20;i++){
            gameId+=possibleChar.charAt((int)Math.floor(Math.random()*possibleChar.length()));
        }
        return gameId;
    }

    /**
     *When both players are added to the waitingroom the last player initialize the game and move them self over to the game
     * and the turn will be this players turn the first round.
     * The variable, playersAdded, in loadingController is set to true.
     */
    public void initializeGame() {
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Players").child("Player0").setValue(player.getName());
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").setValue("0");
        data.child("WaitingRoom").child(player.getName()).removeValue();
        this.turnPlayer = 0;
        playerId = 0;
        GameStateController.playersAdded = true;
    }

    /**
     * A listener to when it is two players in the waitingRoom and when onChildRemoved() is fired the player
     * moves them self to the game. The LoadingController knows that both players are ready.
     * Remove the waitingRoom in firebase since there are no players left.
     */
    public void waitingRoomChildListener(){
        data.child("WaitingRoom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Players").child("Player1").setValue(player.getName());
                data.child("WaitingRoom").removeValue();
                turnPlayer = 0;
                playerId = 1;
                GameStateController.playersAdded = true;
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
    }

    /**
     * Checks witch players turn it is and changes it to the other player locally and in realtime database.
     */
    @Override
    public void changeTurn() {
        if (turnPlayer==0){
            turnPlayer=1;
            data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").setValue("1");
        }else{
            turnPlayer=0;
            data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").setValue("0");
        }
    }

    /**
     * Initialize a listener to the turn attribute in firebase first time it is called.
     * onDataChange() is called every time changeTurn() changes the turn variable after the initial call.
     * @return true if it is the local players turn and pass it on to PlayController
     */
    @Override
    public Boolean addTurnListener(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                turnPlayer = Integer.valueOf((String) snapshot.getValue());
                GameStateController.myTurn = turnPlayer.equals(playerId);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
        return turnPlayer.equals(playerId);
    }


    /**
     * Retrieves the opponents board when the game is initialized.
     * @return linked arraylist with the opponents board to PlayController
     */
    @Override
    public ArrayList<List<Integer>> getOpponentBoard() {
        int opponentId = 0;
        if (gameCodeHolder.getPlayerId() == 0){
            opponentId = 1;
        }
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Board").child("Player" + opponentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                opponentBoard = new ArrayList<List<Integer>>();
                Iterable<DataSnapshot> snap = snapshot.getChildren();
                for (DataSnapshot data : snap){
                    List<Integer> temp = new ArrayList<>();
                    Iterable<DataSnapshot> children = data.getChildren();
                    for (DataSnapshot child : children){
                        temp.add(Integer.parseInt(String.valueOf (child.getValue())));
                    }
                    opponentBoard.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return opponentBoard;
    }

    /**
     * Sets the initialize board to this player in firebase so the other player can retrieve it.
     * Calls getOpponentBoard() so the player can retrieve the other players board.
     * @param board the initial board to this player, sent from MakeBoardController
     */
    @Override
    public void sendBoard(ArrayList<List<Integer>> board) {
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Board").child("Player" + gameCodeHolder.getPlayerId()).setValue(board);
        getOpponentBoard();
    }

    /**
     * Checks if both players have initialized their board, so the Board attribute in firebase have more then one child.
     * The boolean variable, playersReady, in LoadingController is then set to true.
     */
    @Override
    public void boardListener(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 1){
                    GameStateController.playersReady = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
    }

    /**
     * Every time this players shoots sendShot is called from Board and updates the variables in firebase.
     * @param x the x-coordinate that is shot on.
     * @param y the y-coordinate that is shot on.
     * @param newValue states if the shot is a hit or miss.
     */
    @Override
    public void sendShot(int x, int y, int newValue) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(x, y, newValue));
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("LastShot").setValue(list);
    }

    /**
     * PlayController initialize the observer when the game starts. Every time the shot is changed the new values are retrieved and
     * passed on to the PlayController by changing a shotChanged variable to true.
     */
    @Override
    public void getOpponentsShot() {
        GameStateController.lastShot = new ArrayList<>(Arrays.asList(0,0,0));
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("LastShot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameStateController.lastShot = new ArrayList<>();
                Iterable<DataSnapshot> data = snapshot.getChildren();
                for(DataSnapshot value : data){
                    GameStateController.lastShot.add(Integer.parseInt(String.valueOf(value.getValue())));
                }
                GameStateController.shotChanged = true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /**
     * In GameFinishedView setScoreboard() is called when the players are finished with the game and the method
     * sets the score value in firebase.
     * @param scoreboard    the score and player that have finished the game.
     */
    @Override
    public void setScoreboard(ScoreBoard scoreboard){
        data.child("Scoreboard").child(scoreboard.getName()).setValue(scoreboard.getScore());
    }

    /**
     * When the scoreboard is updated with the scores for this game it is retrieved from firebase with the scores of other
     * games and sent to GameFinished Controller.
     */
    @Override
    public void retrieveScoreboard(){
        data.child("Scoreboard").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ScoreBoardController.printScoreboard = new HashMap<String, Integer>();
                Iterable<DataSnapshot> data = snapshot.getChildren();
                for (DataSnapshot score : data){
                    ScoreBoardController.printScoreboard.put(score.getKey(), Integer.parseInt(String.valueOf(score.getValue())));
                }
                getPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Help method used to store the opponents username in the gameCodeHolder class
     */
    public void getPlayers(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> players = snapshot.getChildren();
                for (DataSnapshot child : players){
                    if(!child.getKey().equals("Player" + gameCodeHolder.getPlayerId())){
                        gameCodeHolder.setOpponentName(child.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * When the game is finished the GameFinished variable in firebase is change to true with a call from PlayController.
     */
    @Override
    public void gameFinished(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("GameFinished").setValue("True");
        gameCodeHolder.setWonGame(true);
    }

    /**
     * When a game starts from PlayController it is added a listener to changes in the variable GameFinished, so
     * both players will receive information that a game has ended.
     */
    @Override
    public void gameFinsihedListener(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("GameFinished").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((String.valueOf(snapshot.getValue())).equals("True")){
                    GameStateController.finishedGame = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * In GameFinishedView setSinglePlayerScoreboard() is called when the player is finished with the game and the method
     * sets the score value in firebase.
     * @param scoreboard    the score and player that have finished the game.
     */
    @Override
    public void setSinglePLayerScoreboard(ScoreBoard scoreboard){
        data.child("SingleScoreboard").child(scoreboard.getName()).setValue(scoreboard.getScore());
    }

    /**
     * When the singleplayer scoreboard is updated with the scores for this game it is retrieved from firebase with the scores of other
     * games and sent to GameFinished Controller.
     */
    @Override
    public void retrieveSingleScoreboard(){
        data.child("SingleScoreboard").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ScoreBoardController.printScoreboard = new HashMap<String, Integer>();
                Iterable<DataSnapshot> data = snapshot.getChildren();
                for (DataSnapshot score : data){
                    ScoreBoardController.printScoreboard.put(score.getKey(), Integer.parseInt(String.valueOf(score.getValue())));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
