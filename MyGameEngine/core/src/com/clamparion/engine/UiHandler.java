package com.clamparion.engine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;


public interface UiHandler {

	 void setPositionAndAngle(Body body);
	 
	 void setSelectedBody(Body body);
	 void noBodySelected();
	 void updateVertexInputs();
	 void updateVertexCount();
	 
	 void setSelectedJoint(Joint joint);
}
