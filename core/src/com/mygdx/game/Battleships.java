package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.ViewComponents.Assets;
import com.mygdx.game.view.MenuView;
import com.mygdx.game.view.GameStateManager;

public class Battleships extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;
	public static int WIDTH;
	public static int HEIGHT;
	FirebaseServices firebaseServices;
	public static GameCodeHolder firebaseConnector;

	public Battleships(FirebaseServices firebaseServices) {
		this.firebaseServices = firebaseServices;
	}

	@Override
	public void create () {
		Assets.load();
		WIDTH = Gdx.app.getGraphics().getWidth();
		HEIGHT = Gdx.app.getGraphics().getHeight();
		this.firebaseConnector = GameCodeHolder.getInstance(firebaseServices);
		batch = new SpriteBatch();
		gsm = GameStateManager.getInstance();
		gsm.push(new MenuView(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
