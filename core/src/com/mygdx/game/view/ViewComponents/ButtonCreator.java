package com.mygdx.game.view.ViewComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class ButtonCreator {

    private Texture texture;
    public int Buttonx;
    public int Buttony;
    public int Width;
    public int Height;
    private Rectangle rectangle;

    /**
     * Button class for meaking buttons to the game.
     * ButtonView takes in a Texture, coordinates for placement of the texture and size of the texture.
     * This class makes it possible to initiate an action by placing a rectangle around the texture.
     * The class makes it easy to make a button, and will go under the QUALITY ATTRIBUTE: modifiability.
     */
    public ButtonCreator(Texture tex, int buttonx, int buttony, int width, int height) {
        texture = tex;
        Buttonx= buttonx;
        Buttony = buttony;
        Width = width;
        Height = height;
        rectangle = new Rectangle(Buttonx,Buttony,Width,Height);
    }

    public Rectangle getRectangle() {
        return this.rectangle;

    }
    public Texture getTexture(){
        return texture;
    }
}
