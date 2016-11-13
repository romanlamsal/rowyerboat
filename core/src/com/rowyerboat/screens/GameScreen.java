package com.rowyerboat.screens;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.rowyerboat.gameworld.GameMap;
import com.rowyerboat.gameworld.GameWorld;
import com.rowyerboat.helper.InputReader;
import com.rowyerboat.helper.Settings;
import com.rowyerboat.rendering.GameRenderer;

public class GameScreen implements Screen {
	
	private Game game;

	private GameWorld world;
	private GameRenderer renderer;
	
	private boolean doUpdate = false;

	public GameScreen(Game game) {
		InputReader input = new InputReader(game);
		InputMultiplexer multi = new InputMultiplexer();
		multi.addProcessor(input.ges);
		multi.addProcessor(input);
		Gdx.input.setInputProcessor(multi);
		
		this.game = game;
		this.world = new GameWorld(this);
		this.renderer = new GameRenderer(world);
		
		Settings.world = world;
		Settings.renderer = renderer;
		
		input.init(world, renderer);
		
		doUpdate = true;
	}

	@Override
	public void render(float delta) {
		if (doUpdate) {
			world.update(delta);
			renderer.render(delta);
		}
	}
	
	public void end(boolean isWin) {
		doUpdate = false;
		game.setScreen(new WorldMapScreen(new MainScreen(game), isWin));
	}
	
	@Override
	public void show() {
		Gdx.app.log("GameScreen", "Show");
		doUpdate = true;
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "Hide");
		doUpdate = false;
	}

	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
	}

}
