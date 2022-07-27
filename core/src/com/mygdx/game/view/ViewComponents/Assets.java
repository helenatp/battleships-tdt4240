package com.mygdx.game.view.ViewComponents;

import com.badlogic.gdx.graphics.Texture;
public class Assets {

    /**
     * QUALITY ATTRIBUTE: Performance
     *          Tactic: Increase efficiency
     */

    // Backgrounds
    public static Texture mainBackground;
    public static Texture playBackground;
    public static Texture tutorialBackground;

    // Buttons
    public static Texture playGameButton;
    public static Texture tutorialButton;
    public static Texture backButton;
    public static Texture multiplayerButton;
    public static Texture singleplayerButton;
    public static Texture doneButton;
    public static Texture newGame;

    // Logos
    public static Texture coverLogo;
    public static Texture smallLogo;

    // Tutorial
   public static Texture tutorial;
   public static Texture infotext;

    // Loading
    public static Texture load0;
    public static Texture load1;
    public static Texture soundOn;
    public static Texture soundOff;

    // MakeBoard
    public static Texture notPossible;
    public static Texture setUpBoard;

    // PlayView
    public static Texture missed;

    // GameFinished


    public static void load(){

        // Backgrounds
        mainBackground = new Texture("background.jpg");
        playBackground = new Texture("background2.jpeg");
        tutorialBackground = new Texture("background3.jpeg");

        // Buttons
        playGameButton = new Texture("playbutton.png");
        tutorialButton = new Texture("tutorialbutton1.png");
        backButton = new Texture("left-arrow.png");
        multiplayerButton = new Texture("multiplayer.png");
        singleplayerButton = new Texture("singleplayer1.png");
        doneButton = new Texture("done.png");
        newGame = new Texture("newGame.png");

        // Logos
        coverLogo = new Texture("cover.png");
        smallLogo = new Texture("logo.png");

        // Tutorial
        tutorial = new Texture("tutorial.png");
        infotext = new Texture("infotext.png");

        // Loading
        load0 = new Texture("load0.png");
        load1 = new Texture("load1.png");
        soundOn = new Texture("volume.png");
        soundOff = new Texture("mute.png");

        // MakeBoard
        notPossible = new Texture("notpossible.png");
        setUpBoard = new Texture("boardSetup0.png");

        // PlayView
        missed = new Texture("missed.png");
    }

    public void update(){

    }

}
