package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.ViewComponents.ButtonCreator;
import com.mygdx.game.view.ViewComponents.DrawGameBoard;
import com.mygdx.game.view.ViewComponents.Feedback;
import com.mygdx.game.view.ViewComponents.Assets;

import java.util.ArrayList;
import java.util.List;


public class MakeBoardView extends State implements Feedback {
    private Texture background;
    private int x_position;
    private int y_position;
    private Boolean nextTouch = false;
    private ArrayList<List<Integer>> location;
    private ButtonCreator next;
    private Texture logo;
    private Texture setUpTutorial;
    private BitmapFont setUp;
    private boolean bool = true;
    private Texture wrong;
    private DrawGameBoard drawGameBoard = new DrawGameBoard();


    /**
     * the constructor, sets the background, MakeBoardController, board and "next-button"
     **/

    /**
     * QUALITY ATTRIBUTE: USABILITY
     *          The user can initialize the board by placing the ships where the user wants and recieve feedback.
     *          The user can also read a tutorial.
     *          Tactics: Tutorial, support user initative
     *
     *
     * This view presents the board for the player
     * The player can place the ships at preferred position.
     * The class has its own controller that handles the input actions.
     * The class implements the Feedback interface that talks with the MakeBoardController
**/
    protected MakeBoardView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        background = Assets.playBackground;
        next = new ButtonCreator(Assets.doneButton, Battleships.WIDTH/2+650, 90, 250, 95);
        logo = Assets.smallLogo;
        setUpTutorial = Assets.setUpBoard;
        wrong = Assets.notPossible;
        GameStateController.addFeedbackListener(this);
    }

    public void setNextTouch(boolean bool){
        this.nextTouch = bool;
    }

    public void setmarkedShip(ArrayList<List<Integer>> location){
        this.location = location;
    }

    public ArrayList<List<Integer>> getShipLocation(){
        return this.location;
    }


    public boolean getNextTouch(){
        return this.nextTouch;
    }

    /**
     * Handle input is handling the input coordinates that is pressed.
     * The findShip function in the controller detects witch ship is pressed.
     * NextTouch is true if the player has pressed a ship and a ship is "marked".
     *                  The next thouch is then regesterd in the moveShip function.
     */

    /**
     * moves ship to where the player places it on the board
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
            gsc.getShipController().findShip(gsc.getBoard(),gsc.getIndex(x_position,y_position));
            if(next.getRectangle().contains(touch.x,touch.y)){
                gsc.sendBoard();
                gsm.set(new LoadingView(gsm, gsc));
            }
            if(getNextTouch()){
                gsc.moveShip(x_position,y_position,getShipLocation());
                setNextTouch(false);
            }
            if(gsc.getShipController().getMarkedShip() != null){
                setmarkedShip(gsc.getShipController().getMarkedShip().getLocation());
                setNextTouch(true);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    /**
     * @param sb is what is drawn on.
     *
     *  is used to give feedback to the user if they have a valid move.
     *
     */

    /**
     * renders the MakeBoardView
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2+260, Battleships.HEIGHT-260, 400, 400);
        sb.draw(setUpTutorial, Battleships.WIDTH/2+100, 230, 850, 780);
        sb.draw(next.getTexture(),next.Buttonx,next.Buttony,next.Width, next.Height);
        if(!bool){
            sb.draw(wrong, Battleships.WIDTH/2+40, 40,280,200);
        }
        sb.end();
        drawBoardView();
        drawMarkedShip();

    }

    /**
     * draws the board and the ship.
     */

    public void drawBoardView(){
        drawGameBoard.drawBoardandShips(gsc.getBoard());
    }

    /**
     * if a ship is pressed, the ship is then marked my coloring the squares.
     */
    public void drawMarkedShip() {
        if(gsc.getShipController().getMarkedShip() != null){
            drawGameBoard.drawMarkedShip(gsc.getShipController().getMarkedShip(), gsc.getBoard());
        }
    }

    /**
     * OBSERVER PATTERN and QUALITY ATTRIBUTE: USEABILITY
     * here the observer pattern is used by detecting if the player is making an valid move and then giving the player a feedback.
     * The @param bool is declaring if the move is valid:true or notvalid:false.
     */

    public void setBoolean(boolean bool){
        this.bool = bool;
    }
    public Boolean getBoolean(){
        return this.bool;
    }

    @Override
    public void fireAction(boolean bool) {
        setBoolean(bool);
    }

    @Override
    public void dispose() {

    }
}
