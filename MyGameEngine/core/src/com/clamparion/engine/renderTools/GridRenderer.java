package com.clamparion.engine.renderTools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.clamparion.engine.Scales;
import com.clamparion.engine.tools.ColorManager;

public class GridRenderer {

	ShapeRenderer shapeRenderer;
	Vector2 rectsize;
	Vector2 rectPosition;
	Vector2 rectCenter;
	public float PPMscale;

	public GridRenderer(OrthographicCamera cam) {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initScreenScale();
	}

	public void initScreenScale(){
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		if(Scales.screenSize.x < width && Scales.screenSize.y < height){
			rectsize = Scales.screenSize.cpy().scl(0.9f);
		}else{
			if(Scales.isPortrait){
				float s = height / Scales.screenSize.y  ;
				rectsize = new Vector2(s * Scales.screenSize.x,  height).scl(0.9f);
				PPMscale = Scales.PPM * s;
			} else {
				float s = width / Scales.screenSize.x  ;
				rectsize = new Vector2(width,s * Scales.screenSize.y).scl(0.9f);
				PPMscale = Scales.PPM * s;
			}

		}
		rectPosition = new Vector2((width-rectsize.x)/2f, (height-rectsize.y)/2f);
		rectCenter = new Vector2(rectPosition.x + rectsize.x/2f, rectPosition.y + rectsize.y/2f);
		PPMscale *= 0.9f;
	}

	public void resize(OrthographicCamera cam){
		shapeRenderer.setProjectionMatrix(cam.combined);
		initScreenScale();
	}
	public void updateCam(OrthographicCamera cam){
		shapeRenderer.setProjectionMatrix(cam.combined);

	}


	public void render(){
		shapeRenderer.begin(ShapeType.Line);

		//draw Axis
		shapeRenderer.setColor(ColorManager.XAxisOutlineColor);
		shapeRenderer.line(-2000, rectCenter.y, 4000, rectCenter.y);
		shapeRenderer.setColor(ColorManager.YAxisOutlineColor);
		shapeRenderer.line(rectCenter.x,-2000, rectCenter.x, 4000);
		//draw Grid
		shapeRenderer.setColor(ColorManager.gridOutlineColor);
		for (int i = 1; i < 20; i++) {
			float abstand = PPMscale *i;
			shapeRenderer.line(-2000, rectCenter.y+abstand, 4000, rectCenter.y+abstand);
			shapeRenderer.line(-2000, rectCenter.y-abstand, 4000, rectCenter.y-abstand);
			shapeRenderer.line(rectCenter.x+abstand,-2000, rectCenter.x+abstand, 4000);
			shapeRenderer.line(rectCenter.x-abstand,-2000, rectCenter.x-abstand, 4000);
		}
		//draw Rectangle
		shapeRenderer.setColor(ColorManager.rectOutlineColor);
		shapeRenderer.rect(rectPosition.x, rectPosition.y, rectsize.x, rectsize.y);

		shapeRenderer.end();
	}

}
