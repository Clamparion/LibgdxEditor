package com.clamparion.engine.undoRedo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.clamparion.engine.tools.Utils;

public class SaveJoint {
	
	

	public static String getJSON(Joint joint){
		
		String jsn = "";
		
		
		JointType type = joint.getType();
		addObject(jsn,joint.getType().toString(), "type", false);

		if(joint.getUserData() instanceof JointDef){
			if(type == JointType.PrismaticJoint){
				PrismaticJointDef def = (PrismaticJointDef)joint.getUserData();
				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");

				addObject(jsn,def.localAxisA, "localAxisA");
				addObject(jsn,def.referenceAngle, "refAngle");
				addObject(jsn,def.enableLimit, "enableLimit");
				addObject(jsn,def.lowerTranslation, "lowerTrans");
				addObject(jsn,def.upperTranslation, "upperTrans");
				addObject(jsn,def.enableMotor, "enableMotor");
				addObject(jsn,def.motorSpeed, "motorSpeed");
				addObject(jsn,def.maxMotorForce, "maxMotorForce");

			}else if(type == JointType.DistanceJoint){
				DistanceJointDef def = (DistanceJointDef)joint.getUserData();
				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");
				addObject(jsn,def.dampingRatio, "dampingRatio");
				addObject(jsn,def.frequencyHz, "frequencyHz");
				addObject(jsn,def.length, "length");		
			} else if(type == JointType.RevoluteJoint){
				RevoluteJointDef def = (RevoluteJointDef)joint.getUserData();
				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");

				addObject(jsn,def.enableLimit, "enableLimit");
				addObject(jsn,def.referenceAngle, "refAngle");
				addObject(jsn,def.lowerAngle, "lowerAngle");
				addObject(jsn,def.upperAngle, "upperAngle");

				addObject(jsn,def.enableMotor, "enableMotor");
				addObject(jsn,def.maxMotorTorque, "maxMotTorq");
				addObject(jsn,def.motorSpeed, "motorSpeed");
			}else if(type == JointType.RopeJoint){
				RopeJointDef def = (RopeJointDef)joint.getUserData();
				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");
				addObject(jsn,def.maxLength, "maxLength");
			}  else if(type == JointType.PulleyJoint){
				PulleyJointDef def = (PulleyJointDef)joint.getUserData();
				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");
				addObject(jsn,def.groundAnchorA, "groundAnchorA");
				addObject(jsn,def.groundAnchorB, "groundAnchorB");
				addObject(jsn,def.lengthA, "lengthA");
				addObject(jsn,def.lengthB, "lengthB");
				addObject(jsn,def.ratio, "ratio");
			} else if(type == JointType.WeldJoint){
				WeldJointDef def = (WeldJointDef)joint.getUserData();
				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");

				addObject(jsn,def.localAnchorA, "localAnchorA");
				addObject(jsn,def.localAnchorB, "localAnchorB");
				addObject(jsn,def.collideConnected, "collideConn");
				addObject(jsn,def.dampingRatio, "dampingRatio");
				addObject(jsn,def.frequencyHz, "frequencyHz");
				addObject(jsn,def.referenceAngle, "refAngle");		

			}
		}
		jsn= Utils.removeLastChar(jsn);
		jsn = "{" + jsn + "}";
		return jsn;
	}
	
	
	
	//add Vector
	private static void addObject(String jsn, Vector2 vec, String name){
		 jsn += "\""+ name +"\" : {\"x\":" + vec.x + ", \"y\":" + vec.y + "},"; 
	}
	private static void addObject(String jsn, boolean b, String name){
		jsn += "\""+ name +"\": " + b +",";
	}
	private static void addObject(String jsn, float f, String name){
		addObject( jsn, f + "",name, true);
	}
	private static void addObject(String jsn, String s, String name, boolean enabled){
		jsn += "\""+ name +"\": " + s +",";
	}
}
