package com.clamparion.engine.undoRedo;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class UndoRedoStack {
	Body mouseJointBody;
	public UndoRedoStack(World world,Body mouseJointBody){
		this.mouseJointBody = mouseJointBody;
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body bod: bodies){
			new SaveBody(bod);
			
			
			
		}

	}
}
