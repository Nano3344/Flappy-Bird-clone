package com.example.thepcdude.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture background;
	Texture[] birds;
	Texture toptube;
	Texture bottomtube;
	Random random;
	Circle birdCircle = new Circle();
	Rectangle[] topRect;
	Rectangle[] bottomRect;
	int flying = 0;
	int gamestart = 0;
	int tubegap = 400;
	int score = 0;
	float birdX;
	float birdY;
	float velocity;
	float gravity = 4;
	float tubeSpeed = 10;
	int tubeNumber = 4;
	float[] tubesX = new float[tubeNumber];
	float[] tubeOffset = new float[tubeNumber];
	float tubeDistance;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdX = Gdx.graphics.getWidth() / 2 - birds[flying].getWidth() / 2;
		birdY = Gdx.graphics.getHeight() / 2 - birds[flying].getHeight() / 2;
		toptube = new Texture("toptube.png");
		bottomtube = new Texture("bottomtube.png");
		tubeDistance = Gdx.graphics.getWidth() /2;
		random = new Random();
		shapeRenderer = new ShapeRenderer();
		topRect = new Rectangle[tubeNumber];
		bottomRect = new Rectangle[tubeNumber];

		for(int i = 0; i < tubeNumber; i++) {
		    tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - tubegap - 200);
            tubesX[i] = Gdx.graphics.getWidth() / 2 - toptube.getWidth() + i * tubeDistance;
            topRect[i] = new Rectangle();
            bottomRect[i] = new Rectangle();
        }
	}

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gamestart != 0) {

			if(Gdx.input.justTouched()) {
				velocity = -20;
			}

            for(int i = 0; i < tubeNumber; i++) {

			    if(tubesX[i] < -toptube.getWidth()) {
			        tubesX[i] += tubeNumber * tubeDistance;
                } else {

                    tubesX[i] -= tubeSpeed;

                    if(tubesX[i] < Gdx.graphics.getWidth() / 2) {
                    	score++;
					}

                }

                batch.draw(toptube, tubesX[i], Gdx.graphics.getHeight() / 2 + tubegap / 2 + tubeOffset[i]);
                batch.draw(bottomtube, tubesX[i], Gdx.graphics.getHeight() / 2 - tubegap / 2 - bottomtube.getHeight() + tubeOffset[i]);

                topRect[i] = new Rectangle(tubesX[i], Gdx.graphics.getHeight() / 2 + tubegap / 2 + tubeOffset[i], toptube.getWidth(), toptube.getHeight());
                bottomRect[i] = new Rectangle(tubesX[i], Gdx.graphics.getHeight() / 2 - tubegap - bottomtube.getHeight() + tubeOffset[i], toptube.getWidth(), toptube.getHeight());

			}

			if(birdY > 0 || velocity < 0) {
				velocity += gravity;
				birdY -= velocity;
			}

		} else {

			if(Gdx.input.justTouched()){
				gamestart = 1;
			}

		}

		if (flying == 0) {
			flying = 1;
		} else {
			flying = 0;
		}



		    batch.draw(birds[flying], birdX, birdY);
		    batch.end();

		    // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		    // shapeRenderer.setColor(Color.BLACK);

		    birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flying].getHeight() / 2, birds[flying].getWidth() / 2);
            // shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
            for(int i = 0; i < tubeNumber; i++) {

				// shapeRenderer.rect(tubesX[i], Gdx.graphics.getHeight() / 2 + tubegap / 2 + tubeOffset[i], toptube.getWidth(), toptube.getHeight());
				// shapeRenderer.rect(tubesX[i], Gdx.graphics.getHeight() / 2 - tubegap - bottomtube.getHeight() + tubeOffset[i], toptube.getWidth(), toptube.getHeight());

            if(Intersector.overlaps(birdCircle, topRect[i]) || Intersector.overlaps(birdCircle, bottomRect[i]));

            }
            shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
