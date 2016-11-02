package com.clamparion.engine.undoRedo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class SaveBody {
	public Vector2 position;
	public float angle;
	public Object userdata;
	public Vector2 linearVelocity;
	public float angularVelocity;
	public float linearDamping;
	public float angularDamping;
	public Array<SaveFixture> fixtures;
	
	public SaveBody(Body bod){
		this(bod.getPosition().cpy(), bod.getAngle(), bod.getUserData(),
				bod.getLinearVelocity().cpy(), bod.getAngularVelocity(), bod.getLinearDamping(), bod.getAngularDamping());
	}
	
	public SaveBody(Vector2 position, float angle, Object userdata,
			Vector2 linearVelocity, float angularVelocity, float linearDamping,
			float angularDamping) {
		super();
		this.position = position;
		this.angle = angle;
		this.userdata = userdata;
		this.linearVelocity = linearVelocity;
		this.angularVelocity = angularVelocity;
		this.linearDamping = linearDamping;
		this.angularDamping = angularDamping;
	}


	

	
	

}
