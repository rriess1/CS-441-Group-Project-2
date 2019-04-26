package com.weather.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.io.IOException;
import java.util.Iterator;

public class GameScreen implements Screen {
    private WeatherGame game;
    private Texture pikaImage;
    private Texture pokeImage;
    private TextureRegion backGround;
    private Texture backGround2;
    private OrthographicCamera cam;
    private Rectangle pikachu;
    private Array<Rectangle> pokeballs;
    private long lastBallDrop;
    private Viewport port;
    private HUD hud;
    private int speed;
    private int interval;
    private Sound yahoo;
    private int set = 0;

    public GameScreen (WeatherGame game) {
        this.game = game;
        //weatherService();
        pikaImage = new Texture(Gdx.files.internal("pizguy.png"));
        pokeImage = new Texture(Gdx.files.internal("pizza.png"));
        backGround2 = new Texture(Gdx.files.internal("background.png"));
        backGround = new TextureRegion(backGround2, 0, 0, 800, 480);

        yahoo = Gdx.audio.newSound(Gdx.files.internal("yahoo.mp3"));

        hud = new HUD(game.batch);

        cam = new OrthographicCamera();
        port = new FitViewport(800, 480, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        pikachu = new Rectangle();
        pikachu.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        pikachu.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        pikachu.width = 40;
        pikachu.height = 40;

        speed = 100;
        interval = 1500000000;

        pokeballs = new Array<Rectangle>();
        spawnPokeball();

    }

    private void spawnPokeball(){
        Rectangle pokeball = new Rectangle();
        pokeball.x = 800;
        pokeball.y = MathUtils.random(0, 480-64)+80;
        pokeball.width =  40;
        pokeball.height = 40;
        pokeballs.add(pokeball);
        lastBallDrop = TimeUtils.nanoTime();
    }

    public void gameLogic(){
        if(Gdx.input.isTouched()) {
            pikachu.y = pikachu.y + 12;
        }
        if(TimeUtils.nanoTime() - lastBallDrop >interval) spawnPokeball();
        if(pikachu.y > 475){
            yahoo.play();
            pikachu.x = 800 / 2 - 64 / 2;
            pikachu.y = 20;
            hud.incrementHit();
            speed = speed + 10;
            interval = interval - 200000000;
        }
        for (Iterator<Rectangle> iter = pokeballs.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.x -= speed * Gdx.graphics.getDeltaTime();
            if(raindrop.x < 0 || raindrop.y > 475){
                iter.remove();
            }
            if(raindrop.overlaps(pikachu)) {
                game.setScreen(new GameOver(game, hud));
            }
        }
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(backGround, 0, 0, 800, 480);
        game.batch.draw(pikaImage, pikachu.x, pikachu.y);
        for(Rectangle pokeball: pokeballs){
            game.batch.draw(pokeImage, pokeball.x, pokeball.y);
        }
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        gameLogic();
    }

    public void resize(int width, int height) {
        port.update(width, height);
    }

    /*public void weatherService(){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Binghamton&APPID=28011474bd62663987d97ebe379f687e";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    change_weather(object.getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }

    public void change_weather(String des){
        if(des.contains("rain")){
            if(set != 1) {
                set = 1;
            }
        }
        else if(des.contains("cloudy")){
            if(set != 2) {
                set = 2;
            }
        }
        else if(des.contains("snow")){
            if(set != 3) {
                set = 3;
            }
        }
        else if(des.contains("clear")){
            if(set != 0){
            set = 0;
        }
    }
}*/


    @Override
    public void show(){

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
        pikaImage.dispose();
        pokeImage.dispose();
        backGround2.dispose();
    }
}
