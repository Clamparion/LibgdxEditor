package com.clamparion.engine.screens;

import net.dermetfan.gdx.math.GeometryUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.clamparion.engine.UiHandler;
import com.clamparion.engine.assets.Cursors;
import com.clamparion.engine.physicsTools.WorldUtils;
import com.clamparion.engine.physicsTools.WorldUtils.FixtureType;
import com.clamparion.engine.renderTools.GridRenderer;
import com.clamparion.engine.tools.ColorManager;
import com.clamparion.engine.undoRedo.UndoRedoStack;

public class EditorScreen implements Screen, GestureListener, InputProcessor {

	OrthographicCamera cam;
	SpriteBatch batch;
	OrthographicCamera box2dCam;
	GridRenderer gridRenderer;
	World world;
	public WorldUtils wUtils;
	private MouseJointDef mouseJointDef;
	private MouseJoint mouseJoint;
	Texture cursorTex;
	UiHandler swingUi;
	UndoRedoStack urs;

	public static final int POSITER = 3;
	public static final int VELITER = 7;

	public static float DELTATIME = 0;

	private  float accumulator;
	private  float step = 1.0f / 60.0f;

	public boolean jointModeEnabled = false;

	public boolean pausePhysics = true;

	//TODO: will not be saved later;
	public Body mouseJointBody;
	Body selectedBody;

	public Array<Vector2> selectedVertexArray = null;
	public boolean addVertexActivated;

	public EditorScreen(UiHandler swingUi) {
		this.swingUi = swingUi;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gridRenderer = new GridRenderer(cam);

		GestureDetector gd = new GestureDetector(this);
		gd.setLongPressSeconds(0.5f);

		InputMultiplexer multiplexer= new InputMultiplexer(this,gd);
		Gdx.input.setInputProcessor(multiplexer);
		wUtils = new WorldUtils(swingUi);
		world = wUtils.createWorld();

		wUtils.generateBox(0, 0, 5, 0.5f, BodyType.StaticBody);
		wUtils.generateBox(0, -1, 5, 0.5f, BodyType.StaticBody);

		wUtils.generateCircle(0, 1, 0.5f, BodyType.DynamicBody);
		wUtils.generateCircle(0, 2, 0.5f, BodyType.DynamicBody);
		wUtils.generateCircle(0, 3, 0.5f, BodyType.DynamicBody);

		box2dCam = new OrthographicCamera();
		box2dCam.setToOrtho(false, Gdx.graphics.getWidth()/gridRenderer.PPMscale, Gdx.graphics.getHeight()/gridRenderer.PPMscale);
		box2dCam.update();

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);

		mouseJointBody = wUtils.createBody(1000, 1000, BodyType.StaticBody, FixtureType.BOX, 1);

		mouseJointDef = new MouseJointDef();
		mouseJointDef.bodyA = mouseJointBody;
		mouseJointDef.collideConnected = true;
		mouseJointDef.maxForce = 500;

		setCursor();

		urs = new UndoRedoStack(world, mouseJointBody);
	}

	Vector3 mousePos = new Vector3(0,0, 0);

	public void unPausePhysics(){
		pausePhysics = false;
		deselectAll();
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body bod: bodies){
			bod.setActive(true);
			bod.setAwake(true);
		}
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(ColorManager.backgroundColor.r, ColorManager.backgroundColor.g, ColorManager.backgroundColor.b, 1);
		Gdx.gl.glBlendFunc(GL20.GL_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_LINEAR);



		DELTATIME = delta;
		if(!pausePhysics){

			float deltaTime = Math.min(delta, 0.25f);
			accumulator += deltaTime;
			while (accumulator >= step) {
				world.step(step, VELITER, POSITER);
				accumulator -= step;
			}
			wUtils.updateUiPosAndAngle();
		} else {
			DELTATIME = 0;
		}

		gridRenderer.render();
		wUtils.render(box2dCam, selectedVertexArray);


		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mousePos);


		batch.begin();
		float size = 14 * cam.zoom;
		batch.draw(cursorTex, mousePos.x+size/2f, mousePos.y-size/2f, size,size);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width,  height);
		cam.zoom = 1;
		cam.position.x = width/2f;
		cam.position.y = height/2f;
		cam.update();
		gridRenderer.resize(cam);

		box2dCam.setToOrtho(false, width/gridRenderer.PPMscale, height/gridRenderer.PPMscale);
		box2dCam.update();
		updateBox2dCam();
	}

	public void updateBox2dCam(){
		box2dCam.zoom = cam.zoom;
		box2dCam.position.x = (cam.position.x / gridRenderer.PPMscale) - box2dCam.viewportWidth/2f;
		box2dCam.position.y = (cam.position.y/ gridRenderer.PPMscale)- box2dCam.viewportHeight/2f;
		box2dCam.update();
		batch.setProjectionMatrix(cam.combined);
	}

	public void resetSimulation(){
		pausePhysics = true;
		wUtils.resetPositionAndAngle();
		wUtils.updateUiPosAndAngle();
		deselectAll();
	}
	public void deselectAll(){
		wUtils.selectedBodies.clear();
		wUtils.selectedJoint = null;
	}

	public void addNewJoint(String jointName){
		wUtils.addNewJoint(jointName);
	}


	public Array<Vector2> modifyFixture( Fixture selectedFixture){
		resetSimulation();
		selectedBody = selectedFixture.getBody();	
		if(selectedFixture.getShape() instanceof PolygonShape){
			PolygonShape shape = (PolygonShape)selectedFixture.getShape();
			selectedVertexArray = new Array<Vector2>();
			for (int i = 0; i < shape.getVertexCount(); i++) {
				Vector2 v = new Vector2();
				shape.getVertex(i, v);
				selectedVertexArray.add(selectedBody.getPosition().cpy().add(v));
			}
			selectedBody.destroyFixture(selectedFixture);
		}


		//setTransformmode
		translateModeEnabled = true;
		rotateModeEnabled = false;

		return selectedVertexArray;
	}
	public void addModifyedFixtureToBody(){

		if(selectedBody != null && selectedVertexArray.size >= 3){
			for (int i = 0; i < selectedVertexArray.size; i++) {
				selectedVertexArray.get(i).sub(selectedBody.getPosition());
			}
			float[] vertices = convertVectorArrayToFloatArray(selectedVertexArray);
			if(GeometryUtils.isConvex(selectedVertexArray)){

				if(selectedVertexArray.size <= 8){
					wUtils.generatePolygon(selectedBody, vertices);
				}else {
					int splits = MathUtils.ceil(selectedVertexArray.size /8f);
					int currentSplit = 1;
					int abstand = selectedVertexArray.size / splits;
					Array<Array<Vector2>> polygons = new Array<Array<Vector2>>(); 
					for (int i = 0; i < splits; i++) {
						polygons.add(new Array<Vector2>());
					}
					for (int i = 0; i < selectedVertexArray.size; i++) {
						if(i < abstand * currentSplit){
							polygons.get(currentSplit-1).add(selectedVertexArray.get(i));
						}else {
							polygons.get(currentSplit-1).add(selectedVertexArray.get(i));
							if(currentSplit < splits){
								currentSplit++;
								polygons.get(currentSplit-1).add(selectedVertexArray.get(i));
							}
						}
					}
					for (int i = 0; i < splits; i++) {
						if(i > 0)
							polygons.get(i).add(selectedVertexArray.get(0));
						float[] vertices1 = convertVectorArrayToFloatArray(polygons.get(i));
						GeometryUtils.arrangeConvexPolygon(vertices1, false);
						wUtils.generatePolygon(selectedBody, vertices1);
					}

				}

			}else {
				float[][] polygons = GeometryUtils.decompose(vertices);
				for (int i = 0; i < polygons.length; i++) {
					wUtils.generatePolygon(selectedBody, polygons[i]);
				}
			}
		}

		selectedVertexArray = null;
		addVertexActivated = false;

	}

	public float[] convertVectorArrayToFloatArray(Array<Vector2> arr){
		float[] vertices = new float[arr.size*2];
		int count = 0;
		for (int i = 0; i < arr.size; i++) {
			vertices[count] = arr.get(i).x;
			count++;
			vertices[count] = arr.get(i).y;
			count++;
		}
		return vertices;
	}

	public void addVertexToFixture(){
		addVertexActivated = true;
		setCursor();
	}
	public void addVertexToFixtureAtPoint(float x, float y){
		Vector2 lv = new Vector2();
		lv.set(selectedVertexArray.get(0));

		int index1 = 0;
		int index2 = 1;

		float min = Float.MAX_VALUE;
		for (int i = 1; i < selectedVertexArray.size+1; i++) {
			Vector2 v = null;
			if(i >= selectedVertexArray.size){
				v = selectedVertexArray.get(0);
			} else {
				v =  selectedVertexArray.get(i);
			}
			float dst = Intersector.distanceSegmentPoint(lv.x, lv.y, v.x, v.y, x, y);
			if(dst < min){
				min = dst;
				index1 = i-1;
				index2 = i;
				if(i >= selectedVertexArray.size){
					index2 = 0;
				}
			}	
			lv.set(v);
		}
		//sort
		int smaller = index1 < index2 ? index1 : index2;
		int bigger = index1 > index2 ? index1 : index2;
		if(smaller+1 == bigger) {
			selectedVertexArray.insert(bigger, new Vector2(x, y));
		} else {
			selectedVertexArray.add(new Vector2(x, y));
		}
		//setTransformmode
		addVertexActivated = false;
		translateModeEnabled = true;
		rotateModeEnabled = false;		
		setCursor();
		swingUi.updateVertexCount();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		/*
		 */
	}

	public void setCursor(){

		if(addVertexActivated){
			cursorTex = Cursors.addPoint;
		}else if(translateModeEnabled){
			cursorTex = Cursors.move;
		}else if (rotateModeEnabled) {
			cursorTex = Cursors.rotate;
		} 


	}








	boolean translateModeEnabled = true;
	boolean rotateModeEnabled = false;

	@Override
	public boolean keyDown(int keycode) {
		if(selectedVertexArray == null){
			if(keycode ==Keys.T){
				//toggleTransformmode
				translateModeEnabled = !translateModeEnabled;
				rotateModeEnabled = false;
			}
			if(keycode ==Keys.R){
				//toggleRotateMode
				translateModeEnabled = false;
				rotateModeEnabled = !rotateModeEnabled;
			}



			if(keycode ==Keys.A){
				//(de)select all

				if(wUtils.selectedBodies.size > 0){
					wUtils.selectedBodies.clear();
				}else {
					Array<Body> bodies = new Array<Body>();
					world.getBodies(bodies);
					for(Body bod: bodies){
						if(!wUtils.selectedBodies.contains(bod, true)){
							wUtils.selectedBodies.add(bod);
						}
					}

				}
			}
		}
		setCursor();
		
		if(keycode == Keys.LEFT || keycode == Keys.RIGHT || keycode == Keys.UP || keycode == Keys.DOWN){
			float deltaX = 0;
			float deltaY = 0;
			
			switch (keycode) {
			case Keys.LEFT:
				deltaX = -1;
				break;
			case Keys.RIGHT:
				deltaX = 1;
				break;
			case Keys.UP:
				deltaY = 1;
				break;
			case Keys.DOWN:
				deltaY = -1;
				break;
			default:
				break;
			}
			cam.translate(deltaX * cam.zoom * 4, deltaY * cam.zoom * 4);
			cam.update();
			gridRenderer.updateCam(cam);
			updateBox2dCam();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}



	private Vector3 tmp = new Vector3();
	private Vector2 tmp2 = new Vector2();

	private QueryCallback queryCallback = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			if(!fixture.testPoint(tmp.x, tmp.y))
				return true;

			mouseJointDef.bodyB = fixture.getBody();
			mouseJointDef.target.set(tmp.x, tmp.y);
			mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
			return false;
		}

	};




	int lastPressedMouseButton = 0;

	int selectedVertexIndex = -1;

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(jointModeEnabled){
			if(pausePhysics){
				if(button ==1){
					box2dCam.unproject(tmp.set(screenX, screenY, 0));
					wUtils.selectJoint(tmp.x, tmp.y);
				}
			}
		}else {
			if(selectedVertexArray == null){

				if(pausePhysics){
					if(button ==1){
						if(wUtils.selectedBodies.size > 0 && !Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
							wUtils.selectedBodies.clear();
						}
						Vector3 position = new Vector3(screenX, screenY, 0);
						box2dCam.unproject(position);
						wUtils.selectBody(position.x, position.y);	
					}
				} else {

					box2dCam.unproject(tmp.set(screenX, screenY, 0));
					world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);

				}

			} else {
				box2dCam.unproject(tmp.set(screenX, screenY, 0));
				selectedVertexIndex = -1;
				for (int i = 0; i < selectedVertexArray.size; i++) {
					float dist = selectedVertexArray.get(i).dst2(tmp.x, tmp.y);
					if(dist < 0.02f){
						selectedVertexIndex = i;
					}

				}
			}
		}
		lastPressedMouseButton = button;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		setCursor();

		if(addVertexActivated){
			box2dCam.unproject(tmp.set(screenX, screenY, 0));
			addVertexToFixtureAtPoint(tmp.x, tmp.y);	
			selectedVertexIndex = -1;
		} else {
			if(!pausePhysics){
				if(mouseJoint == null)
					return false;

				world.destroyJoint(mouseJoint);
				mouseJoint = null;
				return false;
			}
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {


		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(amount == 1){
			if(cam.zoom < 1.5f)
				cam.zoom += 0.05f;
		}
		else if(amount == -1){
			if(cam.zoom-0.05f > 0f)
				cam.zoom -= 0.05f;
		}
		cam.update();
		gridRenderer.updateCam(cam);
		updateBox2dCam();
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(lastPressedMouseButton == 2){
			cursorTex = Cursors.handtool;
			cam.translate(-deltaX * cam.zoom, deltaY * cam.zoom);
			cam.update();
			gridRenderer.updateCam(cam);
			updateBox2dCam();
		}
		if(selectedVertexArray == null){
			if(!pausePhysics){
				if(mouseJoint == null)
					return false;

				box2dCam.unproject(tmp.set(x, y, 0));
				mouseJoint.setTarget(tmp2.set(tmp.x, tmp.y));
			}else {
				if(lastPressedMouseButton != 2) {
					if(translateModeEnabled && pausePhysics){
						Vector3 translate = new Vector3(deltaX * cam.zoom , -deltaY * cam.zoom, 0);
						translate.scl(1f / gridRenderer.PPMscale);
						Vector3 position = new Vector3(x, y, 0);
						box2dCam.unproject(position);

						wUtils.translateSelectedBodiesBy(translate.x, translate.y, position);
					}else if(rotateModeEnabled && pausePhysics){
						Vector3 position = new Vector3(x, y, 0);
						box2dCam.unproject(position);
						wUtils.rotateSelectedBodiesBy(position);
					}
				}
			}
		} else {
			if(selectedVertexIndex != -1){
				box2dCam.unproject(tmp.set(x, y, 0));
				selectedVertexArray.get(selectedVertexIndex).set(tmp.x, tmp.y);
				swingUi.updateVertexInputs();

			}
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub

	}




}
