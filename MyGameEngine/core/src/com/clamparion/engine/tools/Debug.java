package com.clamparion.engine.tools;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {
	private static BitmapFont font;
	private static SpriteBatch batch;
	private static HashMap<String, String> logs = new HashMap<String, String>();

	public static void init() {
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
	}

	public static void dispose() {
		font.dispose();
		batch.dispose();
	}

	public static void print(String flag, String message) {
		logs.put(flag, message);
	}
	
	public static void resize(int width, int height){
		OrthographicCamera cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		batch.setProjectionMatrix(cam.combined);
	}
	public static void render() {

		batch.begin();
		font.draw(batch, Gdx.graphics.getFramesPerSecond() + " fps", 5, 20);

		printMap(logs);
		batch.end();

	}

	private static void printMap(HashMap<String, String> map) {
		int count = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			font.draw(batch, entry.getKey() + "" + ": " + entry.getValue(), 5, 100 + (20 * count));
			count++;
		}
	}


}
