package com.weather.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOver implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Texture backGround2;
    private TextureRegion backGround;
    private HUD hud;
    private Sound death;

    private WeatherGame game;

    public GameOver(WeatherGame game, HUD hud){
        this.game = game;
        viewport = new FitViewport(800, 480, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        backGround2 = new Texture(Gdx.files.internal("overback.jpg"));
        backGround = new TextureRegion(backGround2, 0, 0, 1600, 960);

        death = Gdx.audio.newSound(Gdx.files.internal("angry.mp3"));
        death.play();

        this.hud = hud;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BebasNeue-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Oh no!", new Label.LabelStyle(font, Color.BLACK));
        Label playAgainLabel = new Label("The cat got caught!", new Label.LabelStyle(font, Color.BLACK));

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(backGround, 0, 0, 800, 480);
        game.batch.end();
        stage.draw();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        backGround2.dispose();
        hud.dispose();
    }
}
