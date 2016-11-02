package com.clamparion.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.clamparion.engine.assets.Cursors;
import com.clamparion.engine.physicsTools.WorldUtils.FixtureType;
import com.clamparion.engine.screens.EditorScreen;
import com.clamparion.engine.tools.Debug;

public class Starter extends Game {
	SpriteBatch batch;
	Texture img;
	EditorScreen eds;
	UiHandler launcher;
	
	public Starter(UiHandler launcher){
		this.launcher = launcher;
	}

	@Override
	public void create () {
		Cursors.init();
		
		Debug.init();
		Scales.init(true);
		eds = new EditorScreen(launcher);
		setScreen(eds);
		
		
	}

	 
	
	@Override
	public void render () {
		super.render();
		Debug.render();
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Debug.resize(width, height);
	}


	public void playPhysics(){
		eds.unPausePhysics();
	}
	public void pausePhysics(){
		eds.pausePhysics = true;
	}
	public void reset(){
		eds.resetSimulation();
	}
	public void uiValuesChanged(Body body){
		eds.wUtils.addBodyToHashmap(body);
	}
	
	public void createBody(float x, float y, BodyType bodyType, FixtureType fixType, float sizeradius){
		eds.wUtils.createBody(x,y,bodyType, fixType, sizeradius);	
	}
	
	public Array<Vector2> modifyFixture( Fixture selecFixture){
		return eds.modifyFixture( selecFixture);
	}
	public void addVertexToFixture(){
		eds.addVertexToFixture();
	}
	
	public void addModifyedFixtureToBody(){
		eds.addModifyedFixtureToBody();
	}
	public void addNewJoint(String jointName){
		eds.addNewJoint(jointName);
	}
	public void toggleJointMode(boolean toggle){
		eds.jointModeEnabled = toggle;
		eds.resetSimulation();
		eds.deselectAll();
	}
	
}
