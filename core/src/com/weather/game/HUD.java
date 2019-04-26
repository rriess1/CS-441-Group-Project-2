package com.weather.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD implements Disposable {
    public Stage stage;

    private Integer timeCount;

    private Viewport port;

    private int score;

    Label hitLabel;

    public HUD(SpriteBatch sb){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BebasNeue-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        timeCount = 0;

        port = new FitViewport(800, 480, new OrthographicCamera());
        stage = new Stage(port, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hitLabel = new Label(String.format("Score: %d", score), new Label.LabelStyle(font, Color.BLACK));

        table.add(hitLabel).expandX().top().left();
        table.row();

        stage.addActor(table);
    }

    public void incrementHit(){
        score = score + 100;
        hitLabel.setText(String.format("Score: %d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
