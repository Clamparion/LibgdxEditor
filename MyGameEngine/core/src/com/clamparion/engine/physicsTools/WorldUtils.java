package com.clamparion.engine.physicsTools;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.clamparion.engine.UiHandler;
import com.clamparion.engine.renderTools.MyBox2DDebugRenderer;
import com.clamparion.engine.undoRedo.SaveBody;

public class WorldUtils {

	public HashMap<Body, SaveBody> initialPositions = new HashMap<Body, SaveBody>();
	public Array<Joint> joints = new Array<Joint>();
	public Array<Body> selectedBodies = new Array<Body>();
	public Joint selectedJoint = null;

	private World world;
	MyBox2DDebugRenderer b2dRenderer;
	UiHandler swingUi;
	public static enum FixtureType
	{
		CIRCLE, BOX, TRIANGLE, PENTAGON, HEXAGON
	}

	public WorldUtils(UiHandler swingUi){
		this.swingUi = swingUi;
		b2dRenderer = new MyBox2DDebugRenderer(this);
		b2dRenderer.setDrawContacts(false);
	}

	public World createWorld() {
		world = new World(new Vector2(0, -9.81f), true);
		return world;
	}

	public void render(OrthographicCamera box2dCam,Array<Vector2> selectedVertices){
		b2dRenderer.render(world, box2dCam.combined, selectedVertices);
	}

	public void updateUiPosAndAngle(){
		if(selectedBodies.size == 1){
			Body b = selectedBodies.first();
			swingUi.setPositionAndAngle(b);
		}
	}
	public void updateUiFixtureList(){
		if(selectedBodies.size == 1){
			Body b = selectedBodies.first();
			swingUi.setSelectedBody(b);
		}
	}

	public void translateSelectedBodiesBy(float deltaX, float deltaY, Vector3 pos){
		if(selectedBodies.size > 1){
			for (Body body : selectedBodies) {
				body.setTransform(body.getPosition().x + deltaX, body.getPosition().y + deltaY, body.getAngle());
				addBodyToHashmap(body);
			}
		}else{
			for (Body body : selectedBodies) {
				body.setTransform(pos.x, pos.y, body.getAngle());
				addBodyToHashmap(body);
			}
		}
		updateUiPosAndAngle();
	}
	private float previousAngle = 0;
	public void rotateSelectedBodiesBy(Vector3 pos){
		if(selectedBodies.size > 1){
			//calculate middle point
			Vector2 middle = new Vector2();
			int count = 0;
			for (Body body : selectedBodies) {
				Vector2 v = body.getPosition().cpy();
				middle.add(v);
				count++;
			}
			middle.scl(1f / count);

			float angle = new Vector2(pos.x, pos.y).sub(middle).angle();

			//rotatearound middle
			for (Body body : selectedBodies) {
				Vector2 v = body.getPosition().cpy().sub(middle).rotate( angle - previousAngle);
				v.add(middle);

				body.setTransform(v.x, v.y, angle * MathUtils.degreesToRadians);
				addBodyToHashmap(body);
			}	

			previousAngle = angle;
		}else{
			for (Body body : selectedBodies) {
				Vector2 v = body.getPosition().cpy();
				float angle = MathUtils.degreesToRadians *  v.sub(pos.x, pos.y).angle();

				body.setTransform(body.getPosition().x, body.getPosition().y, angle);
				addBodyToHashmap(body);
			}
		}
		updateUiPosAndAngle();
	}







	private Body addBodyToWorld(BodyDef bodyDef){
		Body body = world.createBody(bodyDef);
		addBodyToHashmap(body);
		return body;
	}

	boolean bodyWasHit = false;
	Vector2 testpoint = new Vector2();
	public void selectBody(float x, float y){
		testpoint.x = x;
		testpoint.y = y;
		bodyWasHit = false;
		world.QueryAABB(callback, x - 0.001f, y - 0.001f, x + 0.001f, y + 0.001f);
		if(!bodyWasHit){
			selectedBodies.clear();
		}
		if(selectedBodies.size != 1){
			swingUi.noBodySelected();
		}
	}

	QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture (Fixture fixture) {
			if(fixture.testPoint(testpoint.x, testpoint.y)){
				bodyWasHit = true;
				Body body = fixture.getBody();
				if(!selectedBodies.contains(body, true)){
					selectedBodies.add(body);
					updateUiPosAndAngle();
					updateUiFixtureList();
				}
				return false;
			}else {
				return true;
			}


		}
	};

	boolean jointwashit = false;
	public void selectJoint(float x, float y){
		jointwashit = false;
		for (int i = 0; i < joints.size; i++) {
			Joint joint = joints.get(i);
			Body bodyA = joint.getBodyA();
			Body bodyB = joint.getBodyB();
			Transform xf1 = bodyA.getTransform();
			Transform xf2 = bodyB.getTransform();

			Vector2 x1 = xf1.getPosition();
			Vector2 x2 = xf2.getPosition();
			Vector2 p1 = joint.getAnchorA();
			Vector2 p2 = joint.getAnchorB();
			

			if (joint.getType() == JointType.DistanceJoint) {
				checkJoint(p1, p2, x, y, joint);
			} else if (joint.getType() == JointType.PulleyJoint) {
				PulleyJoint pulley = (PulleyJoint)joint;
				Vector2 s1 = pulley.getGroundAnchorA();
				Vector2 s2 = pulley.getGroundAnchorB();
				checkJoint(s1, p1, x, y, joint);
				checkJoint(s2, p2, x, y, joint);
				checkJoint(s1, s2, x, y, joint);
			}  else {
				checkJoint(x1, p1,x, y, joint);
				checkJoint(p1, p2,x, y, joint);
				checkJoint(x2, p2, x, y, joint);
			}
			
			
		}
		if(!jointwashit){
			selectedJoint = null;
		}
		swingUi.setSelectedJoint(selectedJoint);
		
		
	}

	private void checkJoint(Vector2 a, Vector2 b, float x, float y, Joint j){
		float dist = Intersector.distanceSegmentPoint(a.x, a.y, b.x, b.y, x, y);
		if(dist < 0.2f){
			jointwashit = true;
			selectedJoint = j;
		}
	}

	public void deleteJoint(Joint joint){
		joints.removeValue(joint, true);
		world.destroyJoint(joint);
		swingUi.setSelectedJoint(null);
		selectedJoint = null;
		
	}
	public Joint changeJoint(JointDef def, Joint joint){
		world.destroyJoint(joint);
		Joint jointnew = world.createJoint(def);
		jointnew.setUserData(def);
		selectedJoint = jointnew;
		return jointnew;
		
	}

	public void addBodyToHashmap(Body body){
		initialPositions.put(body, new SaveBody(body));
	}

	public void resetPositionAndAngle(){
		for (Entry<Body, SaveBody> entry : initialPositions.entrySet()) {
			Body body = entry.getKey();
			SaveBody value = entry.getValue();

			body.setTransform(value.position.x, value.position.y, value.angle);
			body.setLinearVelocity(value.linearVelocity.x,value.linearVelocity.y);
			body.setAngularVelocity(value.angularVelocity);
		}
	}


	public void addNewJoint(String jointName){
		if(selectedBodies.size == 2){
			if(jointName.equals("DistanceJoint")){
				DistanceJointDef def = new DistanceJointDef();
				def.bodyA = selectedBodies.get(0);
				def.bodyB = selectedBodies.get(1);
				def.length = selectedBodies.get(0).getPosition().dst( selectedBodies.get(1).getPosition());
				def.localAnchorA.set(0, 0);
				def.localAnchorB.set(0, 0);
				def.collideConnected = true;
				def.frequencyHz = 5f;
				Joint j =  world.createJoint(def);
				j.setUserData(def);
				joints.add(j);

			} else if(jointName.equals("PrismaticJoint")){
				PrismaticJointDef def = new PrismaticJointDef();
				def.bodyA = selectedBodies.get(0);
				def.bodyB = selectedBodies.get(1);
				Vector2 middle = (selectedBodies.get(0).getPosition().cpy().add( selectedBodies.get(1).getPosition()).scl(0.5f));

				Vector2 v1 = selectedBodies.get(0).getLocalPoint(middle);
				Vector2 v2 = selectedBodies.get(1).getLocalPoint(middle);
				def.localAnchorA.set(0, 0);
				def.localAnchorB.set(0, 0);
				
				def.collideConnected = true;

				def.enableLimit = true;
				float dist = selectedBodies.get(0).getPosition().dst( selectedBodies.get(1).getPosition());
				def.upperTranslation = 1f * dist;
				def.lowerTranslation =  0.8f * dist;



				Joint j =  world.createJoint(def);
				j.setUserData(def);
				joints.add(j);
			}  else if(jointName.equals("PulleyJoint")){
				PulleyJointDef def = new PulleyJointDef();
				//TODO
				
			}  else if(jointName.equals("RevoluteJoint")){
				RevoluteJointDef def = new RevoluteJointDef();
				def.bodyA = selectedBodies.get(0);
				def.bodyB = selectedBodies.get(1);
				Vector2 middle = (selectedBodies.get(0).getPosition().cpy().add( selectedBodies.get(1).getPosition()).scl(0.5f));

				Vector2 v1 = selectedBodies.get(0).getLocalPoint(middle);
				Vector2 v2 = selectedBodies.get(1).getLocalPoint(middle);
				def.localAnchorA.set(v1.x, v1.y);
				def.localAnchorB.set(v2.x, v2.y);
				def.collideConnected = true;


				Joint j =  world.createJoint(def);
				j.setUserData(def);
				joints.add(j);
			} else if(jointName.equals("RopeJoint")){
				RopeJointDef def = new RopeJointDef();
				def.bodyA = selectedBodies.get(0);
				def.bodyB = selectedBodies.get(1);
				float dist = selectedBodies.get(0).getPosition().dst( selectedBodies.get(1).getPosition());
				Vector2 nor = (selectedBodies.get(0).getPosition().cpy().sub( selectedBodies.get(1).getPosition())).nor();

				Vector2 v1 = selectedBodies.get(0).getLocalPoint(selectedBodies.get(0).getPosition().cpy().sub(nor.cpy().scl((1/3f)*dist )));
				Vector2 v2 = selectedBodies.get(1).getLocalPoint(selectedBodies.get(0).getPosition().cpy().sub(nor.cpy().scl((2/3f)*dist )));
				
				def.localAnchorA.set(v1.x, v1.y);
				def.localAnchorB.set(v2.x, v2.y);
				def.collideConnected = true;
				def.maxLength = (1/3f)*dist;

				Joint j =  world.createJoint(def);
				j.setUserData(def);
				joints.add(j);
			}  else if(jointName.equals("WeldJoint")){
				WeldJointDef def = new WeldJointDef();
				def.bodyA = selectedBodies.get(0);
				def.bodyB = selectedBodies.get(1);

				Vector2 middle = (selectedBodies.get(0).getPosition().cpy().add( selectedBodies.get(1).getPosition()).scl(0.5f));

				Vector2 v1 = selectedBodies.get(0).getLocalPoint(middle);
				Vector2 v2 = selectedBodies.get(1).getLocalPoint(middle);
				def.localAnchorA.set(v1.x, v1.y);
				def.localAnchorB.set(v2.x, v2.y);
				def.collideConnected = false;
				Joint j =  world.createJoint(def);
				j.setUserData(def);
				joints.add(j);
			}
		}
	}



	public  Body generateBox(float x, float y, float width, float height, BodyType type) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = type;
		Body body = addBodyToWorld(bdef);
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(width / 2f, height / 2f);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = pshape;

		fdef.density = 1;
		fdef.restitution = 0;
		fdef.friction = 1;

		body.createFixture(fdef);
		pshape.dispose();
		return body;
	}

	public  Body generatePolygon(float x, float y, float[] vertices, BodyType type) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = type;
		Body body = addBodyToWorld(bdef);
		PolygonShape pshape = new PolygonShape();
		pshape.set(vertices);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = pshape;
		fdef.density = 1;
		fdef.restitution = 0;
		fdef.friction = 1;
		body.createFixture(fdef);
		pshape.dispose();
		return body;
	}

	public  Body generatePolygon(Body body, float[] vertices) {
		PolygonShape pshape = new PolygonShape();
		pshape.set(vertices);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = pshape;
		fdef.density = 1;
		fdef.restitution = 0;
		fdef.friction = 1;
		body.createFixture(fdef);
		pshape.dispose();
		return body;
	}


	public  Body generateCircle(float x, float y, float radius, BodyType type) {
		BodyDef bdef = new BodyDef();

		bdef.position.set(x, y);
		bdef.type = type;
		Body body = addBodyToWorld(bdef);
		CircleShape cshape = new CircleShape();
		cshape.setPosition(new Vector2(0, 0));
		cshape.setRadius(radius);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = cshape;
		fdef.density = 1;
		fdef.restitution = 0;
		fdef.friction = 1;
		body.createFixture(fdef);
		cshape.dispose();
		return body;
	}
	public Body generatePolygonWithNEdges(float x, float y, float radius, int numberOfEdges, BodyType bodyType){
		BodyDef bdef = new BodyDef();
		bdef.position.set(x,y);
		bdef.type = bodyType;
		Body body = addBodyToWorld(bdef);
		PolygonShape pshape = new PolygonShape();
		pshape.set(getCircularVertices(numberOfEdges, radius));
		FixtureDef fdef = new FixtureDef();
		fdef = new FixtureDef();
		fdef.shape = pshape;
		fdef.restitution = 0;
		fdef.friction = 1;
		fdef.density = 1;
		body.createFixture(fdef);
		pshape.dispose();
		return body;
	}

	private float[] getCircularVertices(int segments, float radius){

		float segcount = 360f / (float)segments;
		float[] verts = new float[segments * 2];
		int count = 0;
		for(float angle = 0; angle < 359; angle+= segcount){

			verts[count] = (float)(radius * Math.cos(angle * Math.PI / 180F));
			count++;
			verts[count] = (float)(radius * Math.sin(angle * Math.PI / 180F));
			count++;
		}
		return verts;
	}


	public Body createBody(float x, float y,BodyType bodyType, FixtureType fixType, float sizeradius){

		switch (fixType) {
		case CIRCLE:
			return generateCircle(x, y, sizeradius, bodyType);
		case BOX:
			return generateBox(x, y, 2*sizeradius, 2*sizeradius, bodyType);
		case TRIANGLE:
			return generatePolygonWithNEdges(x, y, sizeradius, 3, bodyType);
		case PENTAGON:
			return generatePolygonWithNEdges(x, y, sizeradius, 5, bodyType);
		case HEXAGON:
			return generatePolygonWithNEdges(x, y, sizeradius, 6, bodyType);
		default:
			break;
		}
		return null;

	}

}