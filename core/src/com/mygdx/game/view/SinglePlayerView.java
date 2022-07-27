package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.ViewComponents.Assets;
import com.mygdx.game.view.ViewComponents.ButtonCreator;
import com.mygdx.game.view.ViewComponents.DrawGameBoard;
import com.mygdx.game.view.ViewComponents.FeedbackDelay;

public class SinglePlayerView extends State implements FeedbackDelay {
    private Texture background;
    private Texture logo;
    private float x_position;
    private float y_position;
    private BitmapFont font = new BitmapFont();
    private BitmapFont turn = new BitmapFont();
    private DrawGameBoard drawGameBoard;
    private ButtonCreator tutorialButton;
    private TutorialView TutorialView;
    private int i=0;
    private boolean feedback = false;
    private static Sound hitSound;
    private static Sound missSound;



    public SinglePlayerView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        background = Assets.playBackground;
        logo = Assets.coverLogo;
        this.drawGameBoard = new DrawGameBoard();
        tutorialButton = new ButtonCreator(Assets.tutorialButton, Battleships.WIDTH/2+380, 135,250,100);
        gsc.setSinglePlayer(true);
        gsc.setScoreBoard(gsc.getScoreBoardController().createNewSingleScoreBoard(gsc.getPlayer(), gsc.getSinglePlayer()));
        GameStateController.addFeedbackDelayListener(this);
        hitSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/hitshoot.wav"));
        missSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/miss.mp3"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);
            if(tutorialButton.getRectangle().contains(touch.x, touch.y)) {
                TutorialView = new TutorialView(gsm, gsc);
                gsm.push(TutorialView);
            }
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            gsc.shootSingle(gsc.getIndex(x_position, y_position));
            gsc.getScoreBoard().setBoardList(gsc.getBoard().getBoard());
            gsc.getScoreBoardController().updateScore(gsc.getScoreBoard());
        }
        if (gsc.getBoardController().isFinished(gsc.getBoard())){
            gsc.getScoreBoard().setBoardList(gsc.getBoard().getBoard());
            gsc.getScoreBoardController().updateSingleScoreboard(gsc.getScoreBoard());
            gsm.set(new GameFinishedView(gsm, gsc));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2+100, Battleships.HEIGHT-250, 800, 300);
        font.getData().setScale(3, 3);
        turn.getData().setScale(5, 5);
        turn.setColor(Color.BLACK);
        font.setColor(Color.BLACK);
        turn.draw(sb, "Your score: " + gsc.getScoreBoard().getScore() , Battleships.WIDTH / 2 + 300, 750);
        font.draw(sb, "/ - Represents MISS", Battleships.WIDTH / 2 + 320, 520);
        font.draw(sb, "X - Represents HIT", Battleships.WIDTH / 2 + 320, 450);
        sb.draw(tutorialButton.getTexture(),tutorialButton.Buttonx,tutorialButton.Buttony,tutorialButton.Width,tutorialButton.Height);
        sb.end();
        drawGameBoard.drawBoardView(gsc.getSingleTurn(), gsc.getSingleBoard());
    }

    @Override
    public void dispose() {
        hitSound.dispose();
    }
    public void playSoundHit(){
        if(!feedback && i>0){
            long id = hitSound.play(4f);
            hitSound.setPitch(id,0.6f);
            i=0;
        }
    }
    public void playSoundMiss(){
        long id = missSound.play(4f);
        hitSound.setPitch(id,0.6f);

    }

    public void setFeedback(boolean feedback){
        if(!feedback){
            this.i+=1;
            playSoundHit();
        }
        else if(feedback){
            playSoundMiss();
        }
        this.feedback = feedback;
    }


    @Override
    public void fireActionDelay(boolean feedbackDelay) {
        setFeedback(feedbackDelay);
    }

}
