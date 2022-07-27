package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.ViewComponents.ButtonCreator;
import com.mygdx.game.view.ViewComponents.Assets;

public class TutorialView extends State{

    private Texture logo;
    private Texture background;
    private BitmapFont tutorial;
    private ButtonCreator backButton;
    private Texture gamerules;


    protected TutorialView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        logo = Assets.coverLogo;
        background = Assets.tutorialBackground;
        tutorial = new BitmapFont();
        gamerules = Assets.tutorial;
        backButton = new ButtonCreator(Assets.backButton, 50, Battleships.HEIGHT-120, 100, 100);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);
            if (backButton.getRectangle().contains(touch.x, touch.y)) {
                gsm.pop();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo,0,-35,500,200);
        tutorial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tutorial.getData().setScale(4,4);
        tutorial.draw(sb,"How to play battleships", Battleships.WIDTH/2-300,Battleships.HEIGHT-70);
        sb.draw(backButton.getTexture(),backButton.Buttonx,backButton.Buttony,backButton.Width,backButton.Height);
        sb.draw(gamerules, Battleships.WIDTH/2-750,80,1700,1000);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
