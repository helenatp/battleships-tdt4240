package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.ViewComponents.Assets;
import com.mygdx.game.view.ViewComponents.ButtonCreator;

public class InitializeGameView extends State{

    private Texture background;
    private Texture logo;
    private BitmapFont font;
    public String name1;
    public ButtonCreator nextButton;
    public ButtonCreator multiplayerButton;
    public ButtonCreator singlePlayerButton;
    public Texture infotext;
    public ButtonCreator backButton;

    /**
     * the constructor sets the background, buttons, logo, and font
     */

    protected InitializeGameView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        logo = new Texture("cover.png");
        background = Assets.mainBackground;
        font = new BitmapFont();
        infotext = Assets.infotext;
        multiplayerButton = new ButtonCreator(Assets.multiplayerButton, Battleships.WIDTH/2-200, Battleships.HEIGHT/2,360,125);
        singlePlayerButton = new ButtonCreator(Assets.singleplayerButton, Battleships.WIDTH/2-200, 300,360,125);
        backButton = new ButtonCreator(Assets.backButton, 50, Battleships.HEIGHT-120, 100, 100);
    }

    /**
     * switches to loadingView when touching the "next" button
     * creates a player with username from the input-field when clicking on login
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);
            if (backButton.getRectangle().contains(touch.x, touch.y)) {
                gsm.pop();
            }
            if (multiplayerButton.getRectangle().contains(touch.x, touch.y)) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String name) {
                        if (name.equals("") || name.equals(" ")){
                            return;
                        }else {
                            gsc.setPlayer(gsc.getPlayerController().createPlayer(name));
                            gsc.getPlayerController().addPlayerFirebase(gsc.getPlayer());
                            name1 = name;
                            setName(name1);
                            gsm.set(new LoadingView(gsm, gsc));
                        }
                    }

                    @Override
                    public void canceled() {
                        System.out.println("ups");
                    }
                },"Username","","");


            } if (singlePlayerButton.getRectangle().contains(touch.x, touch.y)) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String name) {
                        gsc.setPlayer(gsc.getPlayerController().createPlayer(name));
                        gsm.set(new SinglePlayerView(gsm, gsc));
                    }

                    @Override
                    public void canceled() {
                        System.out.println("ups");
                    }
                },"Username","","");
            }
        }
    }


    public void setName(String name){
        name1 = name;
    }

    public String getName(){
        return name1;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * renders the InitializeGameView
     * prints the username of the user if it exists
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 255, 1);
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2-750, Battleships.HEIGHT-500, 1500, 600);
        sb.draw(infotext, Battleships.WIDTH/2 + 350, Battleships.HEIGHT-550, 500, 130);
        sb.draw(backButton.getTexture(),backButton.Buttonx,backButton.Buttony,backButton.Width,backButton.Height);
        if(name1==null || name1==""){
            sb.draw(multiplayerButton.getTexture(),multiplayerButton.Buttonx,multiplayerButton.Buttony,multiplayerButton.Width,multiplayerButton.Height);
        }
        sb.draw(singlePlayerButton.getTexture(),singlePlayerButton.Buttonx,singlePlayerButton.Buttony,singlePlayerButton.Width,singlePlayerButton.Height);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
