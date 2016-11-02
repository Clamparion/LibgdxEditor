package com.clamparion.engine.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cursors {
	public static Texture handtool;
	public static Texture move;
	public static Texture rotate;
	public static Texture resize;
	public static Texture addPoint;
	private static boolean initialized = false;
	public static void init(){
		if(!initialized){
			handtool = new Texture(Gdx.files.internal("cursors/hand-tool.png"));
			move = new Texture(Gdx.files.internal("cursors/move-arrows.png"));
			rotate = new Texture(Gdx.files.internal("cursors/rotate-option.png"));
			resize = new Texture(Gdx.files.internal("cursors/diagonal-resize.png"));
			addPoint = new Texture(Gdx.files.internal("cursors/addPoint.png"));
			initialized = true;
		}
	}

}
