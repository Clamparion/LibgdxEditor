package com.clamparion.engine.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

public class JointPanel extends JPanel {


	/**
	 * Create the panel.
	 */
	DektopEditor mainFrame;

	int gridRow = 0;
	private HashMap<String, JTextField> textfields = new HashMap<String, JTextField>();
	private HashMap<String, JCheckBox> checkboxes = new HashMap<String, JCheckBox>();



	public JointPanel(DektopEditor mainFrame) {
		this.mainFrame = mainFrame;
		reset();

	}

	public void reset(){
		this.removeAll();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	public void setJoint(Joint joint){
		JointType type = joint.getType();

		addObject(joint.getType().toString(), "type", false);

		if(joint.getUserData() instanceof JointDef){
			if(type == JointType.PrismaticJoint){
				PrismaticJointDef def = (PrismaticJointDef)joint.getUserData();
				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");
				
				addObject(def.localAxisA, "localAxisA");
				addObject(def.referenceAngle, "refAngle");
				addObject(def.enableLimit, "enableLimit");
				addObject(def.lowerTranslation, "lowerTrans");
				addObject(def.upperTranslation, "upperTrans");
				addObject(def.enableMotor, "enableMotor");
				addObject(def.motorSpeed, "motorSpeed");
				addObject(def.maxMotorForce, "maxMotorForce");
				
			}else if(type == JointType.DistanceJoint){
				DistanceJointDef def = (DistanceJointDef)joint.getUserData();
				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");
				addObject(def.dampingRatio, "dampingRatio");
				addObject(def.frequencyHz, "frequencyHz");
				addObject(def.length, "length");		
			} else if(type == JointType.RevoluteJoint){
				RevoluteJointDef def = (RevoluteJointDef)joint.getUserData();
				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");

				addObject(def.enableLimit, "enableLimit");
				addObject(def.referenceAngle, "refAngle");
				addObject(def.lowerAngle, "lowerAngle");
				addObject(def.upperAngle, "upperAngle");

				addObject(def.enableMotor, "enableMotor");
				addObject(def.maxMotorTorque, "maxMotTorq");
				addObject(def.motorSpeed, "motorSpeed");
			}else if(type == JointType.RopeJoint){
				RopeJointDef def = (RopeJointDef)joint.getUserData();
				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");
				addObject(def.maxLength, "maxLength");
			}  else if(type == JointType.PulleyJoint){
				PulleyJointDef def = (PulleyJointDef)joint.getUserData();
				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");
				addObject(def.groundAnchorA, "groundAnchorA");
				addObject(def.groundAnchorB, "groundAnchorB");
				addObject(def.lengthA, "lengthA");
				addObject(def.lengthB, "lengthB");
				addObject(def.ratio, "ratio");
			} else if(type == JointType.WeldJoint){
				WeldJointDef def = (WeldJointDef)joint.getUserData();
				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");

				addObject(def.localAnchorA, "localAnchorA");
				addObject(def.localAnchorB, "localAnchorB");
				addObject(def.collideConnected, "collideConn");
				addObject(def.dampingRatio, "dampingRatio");
				addObject(def.frequencyHz, "frequencyHz");
				addObject(def.referenceAngle, "refAngle");		
					
			}
		}
		
		this.revalidate();
		this.repaint();
	}



	//add Vector
	private void addObject(Vector2 vec, String name){
		if(textfields.get(name + "X") != null && textfields.get(name + "Y") != null){
			textfields.get(name + "X").setText(vec.x + "");
			textfields.get(name + "Y").setText(vec.y + "");
		}else{
			JLabel lblLocalanchora = new JLabel(name+":");
			GridBagConstraints gbc_lblLocalanchora = new GridBagConstraints();
			gbc_lblLocalanchora.insets = new Insets(0, 0, 5, 5);
			gbc_lblLocalanchora.anchor = GridBagConstraints.EAST;
			gbc_lblLocalanchora.gridx = 0;
			gbc_lblLocalanchora.gridy = gridRow;
			add(lblLocalanchora, gbc_lblLocalanchora);

			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = gridRow;
			add(panel, gbc_panel);

			JTextField textField = new JTextField();
			textField.setText(vec.x + "");
			panel.add(textField);
			textField.setColumns(6);

			JTextField textField_1 = new JTextField();
			textField_1.setText(vec.y + "");
			panel.add(textField_1);

			textfields.put(name+ "X", textField);
			textfields.put(name+ "Y", textField_1);

			textField_1.setColumns(6);
			gridRow++;
		}
	}
	private void addObject(boolean b, String name){
		if(checkboxes.get(name) != null){
			checkboxes.get(name).setSelected(b);
		}else {
			JLabel lblNewLabel_5 = new JLabel(name+":");
			GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
			gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_5.gridx = 0;
			gbc_lblNewLabel_5.gridy = gridRow;
			add(lblNewLabel_5, gbc_lblNewLabel_5);

			JCheckBox chckbxMotor = new JCheckBox("");
			chckbxMotor.setSelected(b);
			GridBagConstraints gbc_chckbxMotor = new GridBagConstraints();
			gbc_chckbxMotor.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxMotor.gridx = 1;
			gbc_chckbxMotor.gridy = gridRow;
			add(chckbxMotor, gbc_chckbxMotor);
			
			checkboxes.put(name, chckbxMotor);
			
			gridRow++;
		}
	}
	private void addObject(float f, String name){
		addObject(f + "",name, true);
	}
	private void addObject(String s, String name, boolean enabled){
		if(textfields.get(name) != null){
			textfields.get(name).getText();
		}else{
			JLabel lblNewLabel_8 = new JLabel(name +":");
			GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
			gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_8.gridx = 0;
			gbc_lblNewLabel_8.gridy = gridRow;
			add(lblNewLabel_8, gbc_lblNewLabel_8);

			JTextField textField_12 = new JTextField();
			textField_12.setText(s);
			textField_12.setEditable(enabled);
			GridBagConstraints gbc_textField_12 = new GridBagConstraints();
			gbc_textField_12.insets = new Insets(0, 0, 5, 0);
			gbc_textField_12.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_12.gridx = 1;
			gbc_textField_12.gridy = gridRow;
			add(textField_12, gbc_textField_12);
			textField_12.setColumns(10);

			gridRow++;
		}
		
	}


}
