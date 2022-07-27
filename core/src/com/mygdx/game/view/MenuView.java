package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.ViewComponents.ButtonCreator;
import com.mygdx.game.view.ViewComponents.Assets;


public class MenuView extends State {

    private Texture logo;
    private Texture background;
    private ButtonCreator playbutton;
    private ButtonCreator initButton;
    private TutorialView TutorialView;

    /**
     *  QUALITY ATTRIBUTE: Usability
     *          Tactics: Tutorial
     *
     * This is the first view and state that is added to the stack in the gsm.
     * It is the front page of the application.
     * There are two buttons witch sends the user to the game or to a tutorial of the game.
     */
    public MenuView(GameStateManager gsm) {
        super(gsm, new GameStateController());
        logo = Assets.coverLogo;
        background = Assets.mainBackground;
        playbutton = new ButtonCreator(Assets.playGameButton, Battleships.WIDTH/2-200, Battleships.HEIGHT/2,400,125);
        initButton = new ButtonCreator(Assets.tutorialButton, Battleships.WIDTH/2-150, 300,300,120);

    }

    /**
     * By pressing the playbutton the game is sent to the play site to chose between muliplayer and singleplayer
     * By pressing the init button the user is sent to the tutorial
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
            if(playbutton.getRectangle().contains(touch.x,touch.y)){
                gsm.push(new InitializeGameView(gsm, new GameStateController()));
            }
            else if(initButton.getRectangle().contains(touch.x,touch.y)) {
                TutorialView = new TutorialView(gsm, gsc);
                gsm.push(TutorialView);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * renders the MenuView
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2-750, Battleships.HEIGHT-500, 1500, 600);
        sb.draw(playbutton.getTexture(),playbutton.Buttonx,playbutton.Buttony,playbutton.Width ,playbutton.Height);
        sb.draw(initButton.getTexture(),initButton.Buttonx,initButton.Buttony,initButton.Width,initButton.Height);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
