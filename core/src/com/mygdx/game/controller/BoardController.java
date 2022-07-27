package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Cell;
import com.mygdx.game.model.ships.BattleShip;
import com.mygdx.game.model.ships.CarrierShip;
import com.mygdx.game.model.ships.CruiserShip;
import com.mygdx.game.model.ships.DestroyerShip;
import com.mygdx.game.model.ships.PatrolShip;
import com.mygdx.game.model.ships.Ship;
import com.mygdx.game.model.ships.SubmarineShip;

import java.util.ArrayList;
import java.util.List;

public class BoardController {



    /**
     * generates a new board sets the sidemargin  and the size of the board
     * sets the width of the board based on the size on the device
     *
     * @param size       the size the board should have, how many cells it should contain in x- and y-direction
     *                   the board is a square -> size = 10 would mean a 10x10 board -> 100 cells on the board
     * @param sidemargin the distance the board should have from the edge of the device when its drawn
     */

    public Board createBoard(int size, int sidemargin){
        Board board = new Board();
        board.setSidemargin(sidemargin);
        board.setCell(new Cell());
        board.setShips(new ArrayList<Ship>());
        if (Battleships.WIDTH > Battleships.HEIGHT) {
            board.setWidth(Battleships.HEIGHT - (2 * sidemargin));
        } else {
            board.setWidth(Battleships.WIDTH - (2 * sidemargin));
        }
        makeBoard(board, size);
        initShips(board, new ShipController());
        return board;
    }

    /**
     * generates a new board, which is going to be the opponent board, from a boardlist retried from firebase
     *
     * @param initializeOpponentBoard The opponents board retrieved from firebase
     * @param sidemargin the distance the board should have from the edge of the device when its drawn
     */
    public Board createBoardFromOpponent(ArrayList<List<Integer>> initializeOpponentBoard, int sidemargin) {
        Board board = new Board();
        board.setCell(new Cell());
        board.setSidemargin(sidemargin);
        if (Battleships.WIDTH > Battleships.HEIGHT) {
            board.setWidth(Battleships.HEIGHT - (2 * sidemargin));
        } else {
            board.setWidth(Battleships.WIDTH - (2 * sidemargin));
        }
        makeBoard(board, 10);
        createOpponentLists(board, initializeOpponentBoard);
        return board;
    }

    /**
     * Adds ships to the opponents board and creates the shiplist to the opponent
     *
     * @param board The opponents board
     * @param initializeOpponentBoard The opponents board retrieved from firebase
     */
    public void createOpponentLists(Board board, ArrayList<List<Integer>> initializeOpponentBoard){
        board.createNewShipsList();
        board.addShip(new DestroyerShip(true));
        board.addShip(new CarrierShip(true));
        board.addShip(new CruiserShip(false));
        board.addShip(new SubmarineShip(true));
        board.addShip(new BattleShip(true));
        board.addShip(new PatrolShip(true));
        for(int row = 0; row < board.getInitializeOpponentBoard().size(); row++){
            for(int col = 0; col < board.getInitializeOpponentBoard().size(); col++){
                if(initializeOpponentBoard.get(row).get(col) < 0){
                    updateBoard(board, col, row, board.getCell().SHIP);
                    for(Ship ship : board.getShips()){
                        if(initializeOpponentBoard.get(row).get(col) == ship.getShipNr()){
                            ship.addLocation(row, col);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * creates a board with the given size
     * the board is a double-linked-list wit the same amounts of rows and columns
     * where each element in the list is a "cell"
     * every cell is given the value EMPTY (0) when the board is created
     * @param size  the size the board should have, how many cells it should contain in x- and y-direction
     *              the board is a square -> size = 10 would mean a 10x10 board -> 100 cells on the board
     */
    private void makeBoard(Board board, int size) {
        board.createNewBoardList();
        board.createNewInitializeOpponentBoardList();
        for (int row = 0; row < size; row++) {
            List<Integer> kolonne = new ArrayList<>();
            List<Integer> kolonne2 = new ArrayList<>();

            for (int column = 0; column < size; column++) {
                kolonne.add(board.getCell().EMPTY);
                kolonne2.add(board.getCell().EMPTY);
            }
            board.getBoard().add(kolonne);
            board.getInitializeOpponentBoard().add(kolonne2);
        }
    }


    public void makeInitalizeOpponentBoard(Board board){
        for (Ship ship: board.getShips()){
            for (List<Integer> coordinate : ship.getLocation()) {
                int x = coordinate.get(0);
                int y = coordinate.get(1);
                updateInitalizeOpponentBoard(board, x, y, ship.getShipNr());
            }
        }
    }

    /**
     * adds ships to the list of ships and creates a random location for every ship
     * the ships cannot overlap
     * every cell the different ships occupies will get the value SHIP (0) on the board (ArrayList<List<Integer>)
     */
    private void initShips(Board board, ShipController controller){
        board.addShip(new DestroyerShip(true));
        board.addShip(new CarrierShip(true));
        board.addShip(new CruiserShip(false));
        board.addShip(new SubmarineShip(true));
        board.addShip(new BattleShip(true));
        board.addShip(new PatrolShip(true));
        for (Ship ship : board.getShips()){
            controller.createRandomLocation(ship);
        }
        for (Ship ship: board.getShips()){
            while (!isValidLocation(board, ship.getLocation())) {
                controller.createRandomLocation(ship);
            }
            for (List<Integer> coordinate : ship.getLocation()) {
                int x = coordinate.get(0);
                int y = coordinate.get(1);
                updateBoard(board, x, y, board.getCell().SHIP);
            }
        }
    }


    /**
     * checks of the coordinates of a move is valid
     * checks both if the coordinates (indexes) is on the board and of the chosen cell is valid to shoot at
     * @param x the x-coordinate for the move
     * @param y the y-coordinate for the move
     * @return  a boolean that tells if the move is valid or not
     *          true if valid, false if not
     */
    private boolean isValidMove(Board board, int x, int y){
        if (( 0 <= x && x < 10) && (0 <= y && y < 10) ){
            int value = board.getBoard().get(y).get(x);
            return board.getCell().isValidMove(value);
        }
        return false;
    }

    /**
     * checks if the whole position for a ship is inside the board
     * @param shipPosition  the coordinates of the different cells the ship is occupying
     * @return  true if the whole ship is placed inside the board, false if not
     */
    public Boolean isInsideBoard(ArrayList<List<Integer>> shipPosition){
        boolean bool = true;
        for(List<Integer> index: shipPosition){
            int x= index.get(0);
            int y = index.get(1);
            if(!(( 0 <= x && x < 10) && (0 <= y && y < 10))) {
                bool =  false;
            }
        }
        return bool;
    }

    /**
     * shoots on the board at the given coordinates and updates the value on the board if the shot vas valid
     * @param x the x-position for the shot
     * @param y the y-position for the shot
     * @return  false if the the shot vas not valid or a hit, true if both valid and a miss
     *          (returns true if it should be a change of turn after this shot)
     */
    public boolean shoot(Board board, int x, int y){
        ShipController controller = new ShipController();
        if (isValidMove(board, x, y)){
            int value = board.getBoard().get(y).get(x);
            if (board.getCell().isHit(value)){
                for (Ship ship :board.getShips()){
                    controller.boardChange(ship, x, y);
                }
                updateBoard(board, x, y,board.getCell().setCell(value));
                Battleships.firebaseConnector.sendShot(x,y,board.getCell().setCell(value));
                return false;
            }
            else {
                updateBoard(board, x, y, board.getCell().setCell(value));
                Battleships.firebaseConnector.sendShot(x,y,board.getCell().setCell(value));
                return true;
            }
        }
        else{
            return false;
        }
    }

    public boolean singleShoot(Board board, int x, int y){
        ShipController controller = new ShipController();
        if (isValidMove(board, x, y)){
            int value = board.getBoard().get(y).get(x);
            if (board.getCell().isHit(value)){
                for (Ship ship :board.getShips()){
                    controller.boardChange(ship, x, y);
                }
                updateBoard(board, x, y,board.getCell().setCell(value));
                return false;
            }
            else {
                updateBoard(board, x, y, board.getCell().setCell(value));
                return true;
            }
        }
        else{
            return false;
        }
    }

    /**
     * updates the cell on the board with the given value
     * @param x x-position for the cell
     * @param y y-position for the cell
     * @param value the value the cell should be updates with
     */

    public void updateBoard(Board board, int x, int y, int value){
        List<Integer> tmp = board.getBoard().get(y);
        tmp.set(x, value);
        board.getBoard().set(y,tmp);
    }


    public void updateInitalizeOpponentBoard(Board board, int x, int y, int value){
        List<Integer> tmp = board.getInitializeOpponentBoard().get(y);
        tmp.set(x, value);
        board.getInitializeOpponentBoard().set(y,tmp);
    }


    /**
     * checks if a ship can be placed on a given location, or if there already is a ship placed on one of the cells
     * @param location the location for every cell a ship should occupy
     * @return  true if every cell is available, false if there already is a ship on one of the cells
     */
    public boolean isValidLocation(Board board, ArrayList<List<Integer>> location){
        for (List<Integer> coordinates : location){
            if (board.getBoard().get(coordinates.get(1)).get(coordinates.get(0)) == board.getCell().SHIP){
                return false;
            }
        }
        return true;
    }



    /**
     * changes the value on every cell a ship has earlier occupied to be empty
     * @param location  the location for every cell that should change value
     */
    public void removeShipPosition(Board board, ArrayList<List<Integer>> location){
        for (List<Integer> coordinate : location) {
            int x = coordinate.get(0);
            int y = coordinate.get(1);
            updateBoard(board, x, y, board.getCell().EMPTY);
        }
    }

    /**
     * adds the value of a ship to the cells a ship is occupying
     * @param location  the location for every cell that should change value
     */
    public void addShipPosition(Board board, ArrayList<List<Integer>> location) {
        for (List<Integer> coordinate : location) {
            int x = coordinate.get(0);
            int y = coordinate.get(1);
            updateBoard(board, x, y, board.getCell().SHIP);
        }
    }


    /**
     * checks if every ship on the board is sunk (the game is over)
     * @return true if every ship is sunk, false otherwise
     */
    public boolean isFinished(Board board){
        for(Ship ship: board.getShips()){
            if (!ship.getSunk()){
                return false;
            }
        }
        return true;
    }

}
