package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * The main game to render the map stage and UI stage.
 */
public final class CrowChart extends Game {
	/**
	 * The map stage, shows the map.
	 */
	private Stage myStage;
	/**
	 * The UI stage, shows the UI.
	 */
	private Stage myUIStage;
	/**
	 * UIData, stores everything.
	 */
	private UIData myData;

	/**
	 * Generates an instance of our map program.
	 */
	@Override
	public void create() {
		myData = new UIData();
		myStage = myData.getCurrentStage();
		myUIStage = myData.buildUI();
		Gdx.input.setInputProcessor(myUIStage);
	}

	/**
	 * Renders the map program.
	 */
	@Override
	public void render() {
		//Maps change automatically to the current one.
		myStage = myData.getCurrentStage();
		//Stages act at 24 FPS
		myStage.act(1 / 24f);
		myUIStage.act(1 / 24f);
		//clear screen to chart colors
		Gdx.gl.glClearColor(215 / 255f, 211 / 255f, 201 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Draw Graphics.
		myStage.draw();
		myUIStage.draw();
	}
}
