package com.weather.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class WeatherGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartMenu(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
