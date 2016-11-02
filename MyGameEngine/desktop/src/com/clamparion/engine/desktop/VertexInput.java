package com.clamparion.engine.desktop;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.badlogic.gdx.math.Vector2;

public class VertexInput {
	public JLabel label;
	public JPanel panel;
	public JTextField xPos;
	public JTextField yPos;
	public Vector2 vec;
	public VertexInput(JLabel label, JPanel panel, JTextField xPos,
			JTextField yPos) {
		super();
		vec = new Vector2();
		this.label = label;
		this.panel = panel;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	
	public void setXandY(float x, float y){
		vec.set(x, y);
		xPos.setText(x + "");
		yPos.setText(y + "");
	}
	
	public Vector2 getXandY(){
		try {
			float x = Float.parseFloat(xPos.getText());
			float y = Float.parseFloat(yPos.getText());
			vec.set(x, y);
		} catch (Exception e) {
		}
		return vec;
	}
	
	public void remove(ModifyVertexPanel panel){
		panel.remove(label);
		this.panel.remove(xPos);
		this.panel.remove(yPos);
		panel.remove(this.panel);
	}
	
	
}
