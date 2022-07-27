package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.GameCodeHolder;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.ViewComponents.ButtonCreator;
import com.mygdx.game.view.ViewComponents.Assets;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameFinishedView extends State {

    private Texture logo;
    private Texture background;
    private BitmapFont font;
    private ButtonCreator newGame;
    private HashMap<String, Integer> temp;
    private GameCodeHolder gch;

    protected GameFinishedView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        logo = Assets.coverLogo;
        background = Assets.mainBackground;
        font = new BitmapFont();
        newGame = new ButtonCreator(Assets.newGame, Battleships.WIDTH/2-150, 90, 300, 110);
        temp = gsc.getScoreBoardController().getScoreboard();
        gch = GameCodeHolder.getInstance(Battleships.firebaseConnector);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
            if(newGame.getRectangle().contains(touch.x,touch.y)){
               gsm.push(new MenuView(gsm));
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
        if(gsc.getSinglePlayer()){
            sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
            sb.draw(logo, Battleships.WIDTH/2-350, Battleships.HEIGHT-200, 600, 250);
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(4,4);
            font.setColor(Color.BLACK);
            font.draw(sb,"Your score: " + gsc.getScoreBoard().getScore(), 200,Battleships.HEIGHT-400);
            sb.draw(newGame.getTexture(), newGame.Buttonx, newGame.Buttony, newGame.Width, newGame.Height);
        }else{
            sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
            sb.draw(logo, Battleships.WIDTH/2-300, Battleships.HEIGHT-200, 600, 250);
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(4,4);
            if(gch.getWonGame()){
                font.draw(sb,"You Won!", 200,Battleships.HEIGHT-200);
            }else{
                font.draw(sb,"You Lose!", 200,Battleships.HEIGHT-200);

            }
            font.draw(sb,"Your score: " + gsc.getScoreBoard().getScore(), 200,Battleships.HEIGHT-400);
            font.draw(sb,"", Battleships.WIDTH-700,Battleships.HEIGHT-200);
            font.draw(sb,"Opponent score: " + gsc.getScoreBoard().getOpponentScore(), Battleships.WIDTH-700,Battleships.HEIGHT-400);
        }
            font.draw(sb,"Scoreboard: ", Battleships.WIDTH/2-200,900);
            Iterator iterator = temp.entrySet().iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) iterator.next();
                //for (int i = 0; i < 10; i++) {
                int score = ((int)mapElement.getValue());
                font.setColor(Color.BLACK);
                font.draw(sb, mapElement.getKey().toString(), Battleships.WIDTH/2-300, 800-i*70);
                font.draw(sb, " : ", Battleships.WIDTH/2, 800-i*70);
                font.draw(sb, String.valueOf(score), Battleships.WIDTH/2+100, 800-i*70);
                //}
                i++;
            }
            sb.draw(newGame.getTexture(), newGame.Buttonx, newGame.Buttony, newGame.Width, newGame.Height);
            sb.end();
    }

    @Override
    public void dispose() {

    }
}
