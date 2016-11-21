package com.clamparion.engine.undoRedo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.clamparion.engine.tools.Utils;

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
		fixtures = new Array<SaveFixture>();
		for (int i = 0; i < bod.getFixtureList().size; i++) {
			fixtures.add(new SaveFixture(bod.getFixtureList().get(i)));
		}
	}

	private SaveBody(Vector2 position, float angle, Object userdata,
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


	public String toJson(){
		String jsn = "";

		jsn += "{";
		jsn += "\"position\" : {\"x\":" + position.x + ", \"y\":" +position.y + "},";
		jsn += "\"angle\": "+ angle + ",";
		jsn += "\"linearVelocity\": {\"x\":"+ linearVelocity.x + ", \"y\":" + linearVelocity.y + "},";
		jsn += "\"angularVelocity\": " + angularVelocity +",";
		jsn += "\"linearDamping\": " + linearDamping +",";
		jsn += "\"angularDamping\": " + angularDamping + ",";
		jsn += "\"fixtures\": [";
		String fixtrs = "";
		for (int i = 0; i < fixtures.size; i++) {
			fixtrs += fixtures.get(i).toJson() + ",";
		}
		fixtrs = Utils.removeLastChar(fixtrs);
		jsn += fixtrs;
		jsn += "]";
		jsn += "}";
		return jsn;
	}



}
