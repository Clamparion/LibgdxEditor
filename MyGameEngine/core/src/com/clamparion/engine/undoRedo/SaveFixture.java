package com.clamparion.engine.undoRedo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.clamparion.engine.tools.Utils;

public class SaveFixture {
	float density;
	float friction;
	boolean isSensor;
	float restitution;
	float radius;
	boolean isPolygon;

	short groupindex;
	short maskbits;
	short categorybits;
	Vector2[] vertices =  null;

	public SaveFixture(Fixture fix){
		density = fix.getDensity();
		friction = fix.getFriction();
		restitution = fix.getRestitution();
		isSensor = fix.isSensor();
		radius = fix.getShape().getRadius();
		isPolygon = (fix.getShape() instanceof PolygonShape);
		groupindex = fix.getFilterData().groupIndex;
		maskbits = fix.getFilterData().maskBits;
		categorybits = fix.getFilterData().categoryBits;

		if(isPolygon){
			PolygonShape shape = (PolygonShape)fix.getShape();
			int vcount = shape.getVertexCount();
			vertices = new Vector2[vcount];
			for (int i = 0; i < vcount; i++) {
				Vector2 v = new Vector2();
				shape.getVertex(i, v);
				vertices[i] = v;
			}
		}

	}

	public String toJson(){
		String jsn = "";

		jsn += "{";
		jsn += "\"density\": " + density + ",";
		jsn += "\"friction\": " + friction +",";
		jsn += "\"isSensor\": " + isSensor +",";
		jsn += "\"restitution\": "+ restitution +",";
		jsn += "\"radius\": "+ radius +",";
		jsn += "\"isPolygon\": "+ isPolygon +",";
		jsn += "\"groupindex\": " + groupindex +",";
		jsn += "\"maskbits\": " + maskbits +",";
		jsn += "\"categorybits\": " + categorybits + ",";
		if(isPolygon){
			String verts = "";
			for (int i = 0; i < vertices.length; i++) {
				verts += "{\"x\":" + vertices[i].x + ", \"y\":" + vertices[i].y + "},";
			}
			verts = Utils.removeLastChar(verts);
			jsn += "\"vertices\": [" + verts +"]";
		}else {
			jsn += "\"vertices\": null";
		}
		jsn += "}";
		
		return jsn;
	}


	

}
